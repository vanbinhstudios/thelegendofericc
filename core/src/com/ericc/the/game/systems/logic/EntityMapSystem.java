package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.math.GridPoint2;
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
            pos.map.entityMap.put(new GridPoint2(pos.x, pos.y), entity);
        }
    }

    public class Handler implements EntityListener {
        @Override
        public void entityRemoved(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            pos.map.entityMap.remove(new GridPoint2(pos.x, pos.y));
        }

        @Override
        public void entityAdded(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            pos.map.entityMap.put(new GridPoint2(pos.x, pos.y), entity);
        }
    }
}
