package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DirtyFlag;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.utils.GridPoint;

import java.util.Arrays;
import java.util.List;

/**
 * Field of View System is responsible for
 * updating the field of view of every Entity in a
 * system with that exact component.
 */
public class FieldOfViewSystem extends EntitySystem {

    private ImmutableArray<Entity> entities; ///< all entities with fov available

    public FieldOfViewSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, FieldOfViewComponent.class, DirtyFlag.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            updateFOV(Mappers.position.get(entity), Mappers.fov.get(entity));
        }
    }

    private void updateFOV(PositionComponent pos, FieldOfViewComponent fov) {
        fov.version += 1;
        fov.points.clear();
        for (GridPoint p : pos.map.calculateFOV(pos.xy, fov.RADIUS + 1)) {
            int dx = p.x - pos.xy.x;
            int dy = p.y - pos.xy.y;
            if (dx * dx + dy * dy <= fov.RADIUS * fov.RADIUS || !pos.map.isFloor(p)) {
                fov.points.add(p);
                fov.visibility[p.x][p.y] = fov.version;
            }
        }
    }
}
