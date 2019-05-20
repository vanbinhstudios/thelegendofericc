package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.ActivatedComponent;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Activity System is responsible for sequencing and scheduling the agencies and actions of actors.
 * Actors accumulate delay, each in their own separate pool. Each action costs
 * a certain amount of time - it is indicated by adding its delay to the actor's pool.
 * <p>
 * Actors are arranged in a priority queue {@link #pending}, sorted by amount of their delay.
 * If several actors have the same amount of delay, the precise order of their actions is determined based
 * on their initiative.
 */

public class ActivitySystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> sapient;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> activated;

    private Comparator<Entity> timeLeftComparator = Comparator.comparingInt(e -> {
        AgencyComponent agency = Mappers.agency.get(e);
        return agency == null ? 0 : agency.delay;
    });
    private PriorityQueue<Entity> pending = new PriorityQueue<>(timeLeftComparator);
    private Stack<Entity> actingInThisMoment = new Stack<>();
    private GameEngine gameEngine;
    private Action action;
    private Entity actor;

    public ActivitySystem(GameEngine gameEngine, int priority) {
        super(priority);
        this.gameEngine = gameEngine;
    }

    @Override
    public void addedToEngine(Engine engine) {
        sapient = engine.getEntitiesFor(Family.all(AgencyComponent.class, StatsComponent.class).get());
        entities = engine.getEntitiesFor(Family.all(AgencyComponent.class).get());
        activated = engine.getEntitiesFor(Family.all(ActivatedComponent.class).get());

        engine.addEntityListener(Family.all(AgencyComponent.class).get(), this);
        for (Entity entity : entities) {
            if (!Mappers.agency.get(entity).passive) {
                pending.add(entity);
            }
        }
    }

    @Override
    public void update(float deltaTime) {

        if (actor == null) {
            if (activated.size() > 0) {
                actor = activated.get(0);
                actor.remove(ActivatedComponent.class);
            } else {
                if (actingInThisMoment.isEmpty()) {
                    findActingInThisMoment();
                    rollInitiative();
                }
                actor = actingInThisMoment.pop();
            }

            if (actor == null) {
                // There are no actors.
                gameEngine.stopSpinning();
                return;
            }
        }

        if (action == null) {
            AgencyComponent agency = Mappers.agency.get(actor);
            PositionComponent pos = Mappers.position.get(actor);
            StatsComponent stats = Mappers.stats.get(actor);

            action = agency.agency.chooseAction(pos, stats);
            if (action == null) {
                // The actor needs more time to think.
                gameEngine.stopSpinning();
                return;
            }
        }

        if (action.needsSync(actor, getEngine())) {
            // The action needs to wait until some animation ends.
            gameEngine.stopSpinning();
            return;
        }

        AgencyComponent agency = Mappers.agency.get(actor);
        StatsComponent stats = Mappers.stats.get(actor);

        action.execute(actor, getEngine());

        if (activated.size() == 0) {
            int delay = action.getDelay();
            if (delay == 0) {
                actingInThisMoment.push(actor);
            } else {
                agency.delay += action.getDelay() * ((stats != null) ? stats.delayMultiplier : 1.0);
                pending.add(actor);
            }
        }

        action = null;
        actor = null;
    }

    private void findActingInThisMoment() {
        Entity first = pending.peek();
        if (first == null) {
            return;
        }

        int dt = Mappers.agency.get(first).delay;
        for (Entity entity : pending) {
            Mappers.agency.get(entity).delay -= dt;
            if (Mappers.stats.has(entity)) {
                Mappers.stats.get(entity).tick(dt);
            }
        }

        while (!pending.isEmpty() && Mappers.agency.get(pending.peek()).delay <= 0) {
            actingInThisMoment.add(pending.poll());
        }
    }

    private void rollInitiative() {
        for (Entity entity : sapient) {
            StatsComponent stats = Mappers.stats.get(entity);
            Mappers.agency.get(entity).initiative = ((stats.agility + stats.intelligence) / 4
                    + ThreadLocalRandom.current().nextInt(1, 20));
        }

        actingInThisMoment.sort(Comparator.comparingInt((Entity e) -> Mappers.agency.get(e).initiative));
    }

    @Override
    public void entityAdded(Entity entity) {
        if (!Mappers.agency.get(entity).passive) {
            pending.add(entity);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        pending.remove(entity);
        actingInThisMoment.remove(entity);
    }
}
