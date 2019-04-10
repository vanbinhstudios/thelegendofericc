package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.AreaOfEffectAttackAction;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.entities.Attack;
import com.ericc.the.game.utils.GridPoint;

import static com.ericc.the.game.Direction.*;

public class AreaOfEffectAttackSystem extends IteratingSystem {
    public AreaOfEffectAttackSystem(int priority) {
        super(Family.all(PositionComponent.class, FieldOfViewComponent.class, AreaOfEffectAttackAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        FieldOfViewComponent fov = Mappers.fov.get(entity);
        AreaOfEffectAttackAction attack = Mappers.aoeattack.get(entity);

        int xMultiplier = 1;
        int yMultiplier = 1;
        GridPoint bottomLeftCorner = attack.isDirected
                ? pos.xy.add(GridPoint.fromDirection(attack.direction))
                : pos.xy.add(attack.relativeStart);

        if (attack.isDirected) {
            // this acts like a rotation (90 degrees, clockwise) when it comes to choosing the direction of the spell
            xMultiplier = attack.direction == LEFT ? -1 : 1;
            yMultiplier = attack.direction == DOWN ? -1 : 1;

            if (attack.direction == DOWN || attack.direction == UP) {
                int temp = attack.width;
                attack.width = attack.height;
                attack.height = temp;
            }

            pos.direction = attack.direction;
        }

        for (int xOffset = 0; xOffset < attack.width; ++xOffset) {
            for (int yOffset = 0; yOffset < attack.height; ++yOffset) {
                int xOffsetMul = xOffset * xMultiplier;
                int yOffsetMul = yOffset * yMultiplier;
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffsetMul, yOffsetMul);

                if (fov.visibility.get(tileAffectedByAOE)
                        && pos.map.isFloor(tileAffectedByAOE)
                        && pos.map.hasAnimationDependency(tileAffectedByAOE)) {
                    entity.add(SyncComponent.SYNC);
                    return;
                }
            }
        }

        for (int xOffset = 0; xOffset < attack.width; ++xOffset) {
            for (int yOffset = 0; yOffset < attack.height; ++yOffset) {
                int xOffsetMul = xOffset * xMultiplier;
                int yOffsetMul = yOffset * yMultiplier;
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffsetMul, yOffsetMul);

                System.out.println(tileAffectedByAOE.x + " " + tileAffectedByAOE.y);

                if (fov.visibility.get(tileAffectedByAOE)
                        && pos.map.isFloor(tileAffectedByAOE)
                        && !pos.xy.equals(tileAffectedByAOE)) {
                    getEngine().addEntity(
                            new Attack(
                                    bottomLeftCorner.shift(xOffsetMul, yOffsetMul),
                                    pos.map,
                                    attack.power,
                                    attack.model,
                                    attack.direction
                            )
                    );
                }

            }
        }

        entity.remove(AreaOfEffectAttackAction.class);
    }
}
