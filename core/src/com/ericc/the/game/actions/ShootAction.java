package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Projectile;
import com.ericc.the.game.utils.GridPoint;

public class ShootAction extends Action {
    public Direction direction;
    public int power;

    public ShootAction(Direction direction, int power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public int getDelay() {
        return 100;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        return pos.map.hasAnimationDependency(pos.xy);
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);

        GridPoint offset = GridPoint.fromDirection(direction);
        GridPoint startPos = pos.xy.add(offset);

        if (pos.map.isFloor(startPos)) {
            engine.addEntity(
                    new Projectile(startPos, pos.map, direction, power, Models.arrow)
            );
        }
    }
}
