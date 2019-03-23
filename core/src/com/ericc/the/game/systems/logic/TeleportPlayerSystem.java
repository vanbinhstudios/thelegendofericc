package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DescendingComponent;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.TeleportScheduledComponent;
import com.ericc.the.game.map.CurrentMap;
import com.ericc.the.game.map.Dungeon;

public class TeleportPlayerSystem extends EntitySystem {

    private Dungeon dungeon;
    private Engines engines;

    public TeleportPlayerSystem(Dungeon dungeon, Engines engines) {
        super(10000);

        this.dungeon = dungeon;
        this.engines = engines;
    }

    @Override
    public void addedToEngine(Engine engine) {}

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> playersToTeleport = engines.getEntitiesFor(
                Family.all(PlayerComponent.class,
                TeleportScheduledComponent.class).get()
        );

        for (Entity player : playersToTeleport) {
            TeleportScheduledComponent teleport = Mappers.teleports.get(player);
            DescendingComponent stairs = Mappers.stairsComponent.get(teleport.stairs);
            player.remove(TeleportScheduledComponent.class);

            if (stairs.descending) {
                CurrentMap.setMap(dungeon.goToNext(), engines);
            } else {
                CurrentMap.setMap(dungeon.goToPrevious(), engines);
            }
        }
    }
}
