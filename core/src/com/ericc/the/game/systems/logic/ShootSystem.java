package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.ShootAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Projectile;
import com.ericc.the.game.utils.GridPoint;

public class ShootSystem extends IteratingSystem {
    public ShootSystem(int priority) {
        super(Family.all(PositionComponent.class, ShootAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        Direction dir = Mappers.shoot.get(entity).direction;

        GridPoint offset = GridPoint.fromDirection(dir);
        GridPoint startPos = pos.xy.add(offset);

        if (pos.map.isFloor(startPos)) {
            getEngine().addEntity(
                    new Projectile(startPos, pos.map, dir, Mappers.shoot.get(entity).power, Models.arrow)
            );
        }

        entity.remove(ShootAction.class);
    }
}
