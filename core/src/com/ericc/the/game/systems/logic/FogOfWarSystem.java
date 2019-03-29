package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;

public class FogOfWarSystem extends EntitySystem {

    private ImmutableArray<Entity> entities; ///< all entities able to discover new tiles

    public FogOfWarSystem(int priority) {
        super(priority); // Depends on FieldOfViewSystem
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, PlayerComponent.class, FieldOfViewComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            FieldOfViewComponent fov = Mappers.fov.get(entity);
            PositionComponent position = Mappers.position.get(entity);

            int updateMargin = FieldOfViewComponent.VIEW_RADIUS + 3;

            for (int y = position.y + updateMargin; y >= position.y - updateMargin; --y) {
                for (int x = position.x - updateMargin; x < position.x + updateMargin; ++x) {
                    if (position.map.inBoundaries(x, y) && fov.visibility.get(x, y)) {
                        position.map.markAsSeenByPlayer(x, y);
                    }
                }
            }
        }
    }
}
