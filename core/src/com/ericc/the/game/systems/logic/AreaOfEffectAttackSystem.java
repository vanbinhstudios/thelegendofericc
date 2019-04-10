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

public class AreaOfEffectAttackSystem extends IteratingSystem {
    public AreaOfEffectAttackSystem(int priority) {
        super(Family.all(PositionComponent.class, FieldOfViewComponent.class, AreaOfEffectAttackAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        FieldOfViewComponent fov = Mappers.fov.get(entity);
        AreaOfEffectAttackAction attack = Mappers.aoeattack.get(entity);
        GridPoint bottomLeftCorner = pos.xy.add(attack.relativeStart);

        for (int xOffset = 0; xOffset < attack.width; ++xOffset) {
            for (int yOffset = 0; yOffset < attack.height; ++yOffset) {
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffset, yOffset);

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
                GridPoint tileAffectedByAOE = bottomLeftCorner.shift(xOffset, yOffset);

                if (fov.visibility.get(tileAffectedByAOE)
                        && pos.map.isFloor(tileAffectedByAOE)
                        && !pos.xy.equals(tileAffectedByAOE)) {
                    getEngine().addEntity(
                            new Attack(
                                    bottomLeftCorner.shift(xOffset, yOffset),
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
