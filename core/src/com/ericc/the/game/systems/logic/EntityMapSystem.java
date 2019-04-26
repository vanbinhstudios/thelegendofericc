package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.*;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.CollisionComponent;
import com.ericc.the.game.components.PositionComponent;

public class EntityMapSystem extends EntitySystem {
    @Override
    public void addedToEngine(Engine engine) {
        Family family = Family.all(PositionComponent.class, CollisionComponent.class).get();
        engine.addEntityListener(family, new Handler());

        for (Entity entity : engine.getEntitiesFor(family)) {
            PositionComponent pos = Mappers.position.get(entity);
            CollisionComponent col = Mappers.collision.get(entity);
            if (col.type == CollisionComponent.Type.CRATE || col.type == CollisionComponent.Type.LIVING) {
                pos.map.collisionMap.put(pos.xy, entity);
            } else {
                pos.map.trapMap.put(pos.xy, entity);
            }
        }
    }

    public class Handler implements EntityListener {
        @Override
        public void entityRemoved(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            if (pos.map.collisionMap.get(pos.xy) == entity) {
                pos.map.collisionMap.remove(pos.xy);
            } else if (pos.map.trapMap.get(pos.xy) == entity) {
                pos.map.trapMap.remove(pos.xy);
            }
        }

        @Override
        public void entityAdded(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            CollisionComponent col = Mappers.collision.get(entity);
            if (col.type == CollisionComponent.Type.CRATE || col.type == CollisionComponent.Type.LIVING) {
                pos.map.collisionMap.put(pos.xy, entity);
            } else {
                pos.map.trapMap.put(pos.xy, entity);
            }
        }
    }
}
