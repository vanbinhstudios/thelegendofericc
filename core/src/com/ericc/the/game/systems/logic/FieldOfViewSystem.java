package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Mappers;
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

    // a helper data structure which reduces the code lines to check whether the given
    // position is a visible corner
    public static List<List<GridPoint>> corners =
            Arrays.asList( // there are 4 cases here
                    Arrays.asList(
                            new GridPoint(-1, 0), // each one of them has two coordinates
                            new GridPoint(0, 1) // that should be checked for visible walls
                    ),
                    Arrays.asList(
                            new GridPoint(0, 1),
                            new GridPoint(1, 0)
                    ),
                    Arrays.asList(
                            new GridPoint(1, 0),
                            new GridPoint(0, -1)
                    ),
                    Arrays.asList(
                            new GridPoint(0, -1),
                            new GridPoint(-1, 0)
                    )
            );
    private ImmutableArray<Entity> entities; ///< all entities with fov available

    public FieldOfViewSystem(int priority) {
        super(priority); // Depends on MovementSystem
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, FieldOfViewComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            processEntity(Mappers.position.get(entity), Mappers.fov.get(entity));
        }
    }

    /**
     * A decorator-like function to call with less arguments.
     *
     * @param pos a Position Component of an Entity
     * @param fov a Field of View Component of the same Entity
     */
    private void processEntity(PositionComponent pos, FieldOfViewComponent fov) {
        updateFOV(pos, fov);
    }

    /**
     * Clears the given fov of any Entity but restricts clearing
     * only to visible map area (by player)
     * to improve performance.
     *
     * @param fov a Field of View Component of any Entity
     */
    private void clearFOV(PositionComponent pos, FieldOfViewComponent fov) {
        int updateMargin = FieldOfViewComponent.VIEW_RADIUS + 4;
        for (int y = pos.getY() + updateMargin; y >= pos.getY() - updateMargin; --y) {
            for (int x = pos.getX() - updateMargin; x < pos.getX() + updateMargin; ++x) {
                if (pos.map.inBoundaries(x, y)) {
                    fov.visibility.clear(x, y);
                }
            }
        }
    }

    /**
     * Updates the given fov component.
     *
     * @param pos a PositionComponent of the same Entity
     * @param fov a Field of Component of the same Entity
     */
    private void updateFOV(PositionComponent pos, FieldOfViewComponent fov) {
        clearFOV(pos, fov);

        // sends a ray trace line every degree
        for (int i = 0; i < 360; i++) {
            float x = MathUtils.cos(i * .01745f); // in radians, that's why there is a .175.. const
            float y = MathUtils.sin(i * .01745f);

            // calls update for that ray trace line
            updateOneLine(x, y, fov, pos);
        }
    }

    /**
     * Updates all tiles which are on the ray trace line, starting from the Entity's position.
     *
     * @param x   a float value indicating in which direction the ray trace line goes (x axis)
     * @param y   a float value indicating in which direction the ray trace line goes (y axis)
     * @param fov a Field of View Component of a given Entity
     * @param pos a PositionComponent of the same Entity
     */
    private void updateOneLine(float x, float y, FieldOfViewComponent fov, PositionComponent pos) {
        float posx = pos.getX() + .5f;
        float posy = pos.getY() + .5f;

        // Entity can only see in its radius (radius indicates that it is a circle)
        for (int i = 0; i < FieldOfViewComponent.VIEW_RADIUS; ++i) {
            int castedX = (int) posx;
            int castedY = (int) posy;

            // if any of the coordinate is outside the map we should omit it
            if (!(pos.map.inBoundaries(castedX, castedY))) {
                continue;
            }

            fov.visibility.set(castedX, castedY);

            // this piece of code was written to ensure that corners and walls are
            // rendered properly -> id does render them sometimes even though they are not in view range
            if (pos.map.isFloor(castedX, castedY)) {
                checkMoves(Moves.moves, true, castedX, castedY, fov, pos);
                checkMoves(Moves.diagonalMoves, false, castedX, castedY, fov, pos);
            } else {
                // if this tile is a border, the hero does not see through that tile
                return;
            }

            posx += x;
            posy += y;
        }
    }

    /**
     * Checks whether a current fov should be expanded by one tile,
     * in order to render properly walls.
     */
    private void checkMoves(List<GridPoint> moves, boolean regularMoves,
                            int castedX, int castedY, FieldOfViewComponent fov, PositionComponent pos) {
        for (GridPoint move : moves) {
            int posxTemp = castedX + move.x;
            int posyTemp = castedY + move.y;

            if (pos.map.inBoundaries(posxTemp, posyTemp)
                    && !pos.map.isFloor(posxTemp, posyTemp)
                    && (regularMoves || isCorner(posxTemp, posyTemp, fov, pos))) {
                fov.visibility.set(posxTemp, posyTemp);
            }
        }
    }

    /**
     * Checks whether the given tile is a (VISIBLE!) corner.
     */
    private boolean isCorner(int x, int y, FieldOfViewComponent fov, PositionComponent pos) {
        for (List<GridPoint> corner : corners) {
            GridPoint firstMove = corner.get(0);
            GridPoint secondMove = corner.get(1);

            if (pos.map.inBoundaries(x + firstMove.x, y + firstMove.y)
                    && pos.map.inBoundaries(x + secondMove.x, y + secondMove.y)
                    && !pos.map.isFloor(x + firstMove.x, y + firstMove.y)
                    && !pos.map.isFloor(x + secondMove.x, y + secondMove.y)
                    && fov.visibility.get(x + firstMove.x, y + firstMove.y)
                    && fov.visibility.get(x + secondMove.x, y + secondMove.y)) {
                return true;
            }
        }

        return false;
    }
}
