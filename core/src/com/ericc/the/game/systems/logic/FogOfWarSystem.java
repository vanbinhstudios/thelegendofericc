package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.EntitySystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.CurrentMap;


public class FogOfWarSystem extends EntitySystem {

    private Player player;

    public FogOfWarSystem(Player player) {
        super(9998); // remember to change it so it is larger than FOV priority

        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        FieldOfViewComponent playersFov = Mappers.fov.get(player);
        PositionComponent playersPos = Mappers.position.get(player);

        // Fog is updated in a matrix, where the player's position is the center of it
        int top = Math.min(playersPos.y + FieldOfViewComponent.VIEW_RADIUS, CurrentMap.map.height());
        int bottom = Math.max(playersPos.y - FieldOfViewComponent.VIEW_RADIUS, 0);
        int left = Math.max(playersPos.x - FieldOfViewComponent.VIEW_RADIUS, 0);
        int right = Math.min(playersPos.x + FieldOfViewComponent.VIEW_RADIUS, CurrentMap.map.width());

        for (int y = top; y >= bottom; --y) {
            for (int x = left; x <= right; ++x) {
                if (playersFov.visibility.get(x, y)) {
                    CurrentMap.map.markAsSeenByPlayer(x, y);
                }
            }
        }
    }
}
