package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.ActiveComponent;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.entities.Player;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ActivitySystem extends EntitySystem {
    private ImmutableArray<Entity> sapient;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> active;

    private Comparator<Entity> timeLeftComparator = Comparator.comparingInt(e -> Mappers.agency.get(e).timeUnitsLeft);
    private PriorityQueue<Entity> pending = new PriorityQueue<>(timeLeftComparator);
    private Array<Entity> actingInThisMoment = new Array<>(false, 512);
    private GameEngine gameEngine;

    private Player player;

    public ActivitySystem(GameEngine gameEngine, int priority, Player player) {
        super(priority);
        this.gameEngine = gameEngine;
        this.player = player;
    }

    @Override
    public void addedToEngine(Engine engine) {
        sapient = engine.getEntitiesFor(Family.all(AgencyComponent.class, StatsComponent.class).get());
        entities = engine.getEntitiesFor(Family.all(AgencyComponent.class).get());
        active = engine.getEntitiesFor(Family.all(ActiveComponent.class).get());
    }

    // TODO Fix NPE
    @Override
    public void update(float deltaTime) {
        for (Entity entity : active) {
            if (Mappers.active.has(entity)) {
                entity.remove(ActiveComponent.class);
                if (Mappers.agency.get(entity).timeUnitsLeft >= 0) {
                    pending.add(entity);
                }
            }
        }

        // Player has just pressed a key
        if (actingInThisMoment.isEmpty() && pending.isEmpty()) {
            for (Entity e : entities) {
                if (Mappers.agency.get(e).timeUnitsLeft >= 0) {
                    pending.add(e);
                }
            }
        }

        if (actingInThisMoment.isEmpty()) {
            findActingInThisMoment();
            rollInitiative();
        }

        // Skip dying entities
        Entity entity = actingInThisMoment.pop();
        while (entity != null && Mappers.death.has(entity)) {
            if (!actingInThisMoment.isEmpty()) {
                entity = actingInThisMoment.pop();
            } else {
                entity = null;
            }

        }
        if (entity != null) {
            entity.add(ActiveComponent.ACTIVE);
        }

        // Everyone has done their action
        if (actingInThisMoment.isEmpty() && pending.isEmpty()) {
            if (!Mappers.player.get(player).handled) {
                int playerActionCost = Mappers.player.get(player).lastActionTimeCost;
                for (Entity e : entities) {
                    Mappers.agency.get(e).timeUnitsLeft += playerActionCost;
                }
                Mappers.player.get(player).handled = true;
                gameEngine.stopSpinning();
            }
        }
    }

    private void findActingInThisMoment() {
        int previousTimeUnits = 0;
        boolean moreThanOneHandled = false;
        while (pending.peek() != null && (!moreThanOneHandled || Mappers.agency.get(pending.peek()).timeUnitsLeft == previousTimeUnits)) {
            Entity e = pending.poll();
            previousTimeUnits = Mappers.agency.get(e).timeUnitsLeft;
            moreThanOneHandled = true;
            actingInThisMoment.add(e);
        }
    }

    private void rollInitiative() {
        for (Entity entity : sapient) {
            StatsComponent stats = Mappers.stats.get(entity);
            Mappers.agency.get(entity).initiative = ((stats.agility + stats.intelligence) / 4
                    + ThreadLocalRandom.current().nextInt(1, 20));
        }

        actingInThisMoment.sort(Comparator.comparingInt(a -> Mappers.agency.get(a).initiative));
    }
}
