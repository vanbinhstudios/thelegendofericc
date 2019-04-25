package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.FixedInitiativeComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Activity System is based on passing an activity token (ActiveComponent).
 * From the point of view of game systems, there is always exactly one entity being active.
 * However, there exists a notion of "logical time", that is, actions can take shorter or longer amounts of time.
 * Actors accumulate delay, each in their own separate pool. Each action costs
 * a certain amount of time - it is indicated by adding delay to the pool.
 * <p>
 * Actors are arranged in a priority queue {@link #pending}, sorted by amount of their delay.
 * If several actors have the same amount of delay, the precise order of their actions is determined based
 * on their initiative.
 */

public class ActivitySystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> sapient;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> withfixedinitiative;

    private Comparator<Entity> timeLeftComparator = Comparator.comparingInt(e -> {
        AgencyComponent agency = Mappers.agency.get(e);
        return agency == null ? 0 : agency.delay;
    });
    private PriorityQueue<Entity> pending = new PriorityQueue<>(timeLeftComparator);
    private Stack<Entity> actingInThisMoment = new Stack<>();
    private GameEngine gameEngine;

    public ActivitySystem(GameEngine gameEngine, int priority) {
        super(priority);
        this.gameEngine = gameEngine;
    }

    @Override
    public void addedToEngine(Engine engine) {
        withfixedinitiative =
                engine.getEntitiesFor(Family.all(AgencyComponent.class, FixedInitiativeComponent.class).get());
        sapient = engine.getEntitiesFor(Family.all(AgencyComponent.class, StatsComponent.class).get());
        entities = engine.getEntitiesFor(Family.all(AgencyComponent.class).get());

        engine.addEntityListener(Family.all(AgencyComponent.class).get(), this);
        for (Entity entity : entities) {
            pending.add(entity);
        }
    }

    @Override
    public void update(float deltaTime) {
        if (actingInThisMoment.isEmpty()) {
            findActingInThisMoment();
            rollInitiative();
        }

        if (!actingInThisMoment.isEmpty()) {
            Entity entity = actingInThisMoment.pop();

            AgencyComponent agency = Mappers.agency.get(entity);
            PositionComponent pos = Mappers.position.get(entity);
            StatsComponent stats = Mappers.stats.get(entity);

            Action action = agency.agency.chooseAction(pos, stats);

            if (action == null || action.needsSync(entity, getEngine())) {
                gameEngine.stopSpinning();
                actingInThisMoment.push(entity);
            } else {
                action.execute(entity, getEngine());
                agency.delay += action.getDelay() * ((stats != null) ? stats.delayMultiplier : 1.0);
                pending.add(entity);
            }
        }
    }

    private void findActingInThisMoment() {
        Entity first = pending.peek();
        if (first == null) {
            return;
        }

        int dt = Mappers.agency.get(first).delay;
        for (Entity entity : pending) {
            Mappers.agency.get(entity).delay -= dt;
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

        for (Entity entity : withfixedinitiative) {
            FixedInitiativeComponent initiative = Mappers.fixedInitiative.get(entity);
            Mappers.agency.get(entity).initiative = initiative.initiative;
        }

        actingInThisMoment.sort(Comparator.comparingInt((Entity e) -> Mappers.agency.get(e).initiative));
    }

    @Override
    public void entityAdded(Entity entity) {
        pending.add(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
        pending.remove(entity);
        actingInThisMoment.remove(entity);
    }
}
