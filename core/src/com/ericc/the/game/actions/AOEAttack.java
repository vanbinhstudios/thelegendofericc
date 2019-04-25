package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Attack;
import com.ericc.the.game.utils.GridPoint;

import static com.ericc.the.game.Direction.*;

public class AOEAttack extends Action {
    // bottom left corner of the area we want to attack (relative to the position of the entity who casts the effect)
    public GridPoint relativeStart;
    public Model model;
    public Direction direction;
    public int width;
    public int height;
    public int delay;
    public int power;
    //< indicates whether attack action is directed, if not the position of attack is determined by the relativeStart
    public boolean isDirected;

    public AOEAttack(GridPoint relativeStart, Model model, Direction direction,
                     int width, int height, int delay, int power) {
        this(model, direction, width, height, delay, power);
        this.relativeStart = relativeStart;
        this.isDirected = false;
    }

    public AOEAttack(Model model, Direction direction,
                     int width, int height, int delay, int power) {
        this.model = model;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.delay = delay;
        this.power = power;
        this.isDirected = true;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);

        int xMultiplier = 1;
        int yMultiplier = 1;

        GridPoint bottomLeftCorner = isDirected
                ? pos.xy.add(GridPoint.fromDirection(direction))
                : pos.xy.add(relativeStart);

        if (isDirected) {
            // this acts like a rotation (90 degrees, clockwise) when it comes to choosing the direction of the spell
            xMultiplier = direction == LEFT ? -1 : 1;
            yMultiplier = direction == DOWN ? -1 : 1;

            if (direction == DOWN || direction == UP) {
                width = height;
                height = width;
            }

            pos.direction = direction;
        }

        for (int xOffset = 0; xOffset < width; ++xOffset) {
            for (int yOffset = 0; yOffset < height; ++yOffset) {
                int xOffsetMul = xOffset * xMultiplier;
                int yOffsetMul = yOffset * yMultiplier;
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffsetMul, yOffsetMul);

                if (pos.map.hasAnimationDependency(tileAffectedByAOE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        FieldOfViewComponent fov = Mappers.fov.get(entity);

        int xMultiplier = 1;
        int yMultiplier = 1;

        GridPoint bottomLeftCorner = isDirected
                ? pos.xy.add(GridPoint.fromDirection(direction))
                : pos.xy.add(relativeStart);

        if (isDirected) {
            // this acts like a rotation (90 degrees, clockwise) when it comes to choosing the direction of the spell
            xMultiplier = direction == LEFT ? -1 : 1;
            yMultiplier = direction == DOWN ? -1 : 1;

            if (direction == DOWN || direction == UP) {
                width = height;
                height = width;
            }

            pos.direction = direction;
        }


        for (int xOffset = 0; xOffset < width; ++xOffset) {
            for (int yOffset = 0; yOffset < height; ++yOffset) {
                int xOffsetMul = xOffset * xMultiplier;
                int yOffsetMul = yOffset * yMultiplier;
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffsetMul, yOffsetMul);

                if (pos.map.isFloor(tileAffectedByAOE) && !pos.xy.equals(tileAffectedByAOE)) {
                    engine.addEntity(
                            new Attack(
                                    bottomLeftCorner.shift(xOffsetMul, yOffsetMul),
                                    pos.map, power, model, direction
                            )
                    );
                    Entity subject = pos.map.entityMap.get(tileAffectedByAOE);
                    if (subject != null && !Mappers.player.has(subject) && Mappers.stats.has(subject)) {
                        Effects.inflictDamage(subject, power);
                    }
                }

            }
        }
    }
}
