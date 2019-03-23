package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.map.Map;

import java.util.Arrays;
import java.util.List;

/**
 * Field of View System is responsible for
 * updating the field of view of every Entity in a
 * system with that exact component.
 */
public class FieldOfViewSystem extends EntitySystem {

    private Map map;
    private ImmutableArray<Entity> entities; ///< all entities with fov available

    // a helper data structure with possible moves from one tile in horizontal and vertical directions
    private static List<GridPoint2> moves =
            Arrays.asList(
                    new GridPoint2(1, 0),
                    new GridPoint2(0, 1),
                    new GridPoint2(-1, 0),
                    new GridPoint2(0, -1)
            );

    // stores moves on diagonals
    private static List<GridPoint2> diagonalMoves =
            Arrays.asList(
                    new GridPoint2(1, 1),
                    new GridPoint2(-1, -1),
                    new GridPoint2(-1, 1),
                    new GridPoint2(1, -1)
            );

    // a helper data structure which reduces the code lines to check whether the given
    // position is a visible corner
    private static List<List<GridPoint2>> corners =
                Arrays.asList( // there are 4 cases here
                        Arrays.asList(
                                new GridPoint2(-1, 0), // each one of them has two coordinates
                                new GridPoint2(0, 1) // that should be checked for visible walls
                        ),
                        Arrays.asList(
                                new GridPoint2(0, 1),
                                new GridPoint2(1, 0)
                        ),
                        Arrays.asList(
                                new GridPoint2(1, 0),
                                new GridPoint2(0, -1)
                        ),
                        Arrays.asList(
                                new GridPoint2(0, -1),
                                new GridPoint2(-1, 0)
                        )
                );

    public FieldOfViewSystem(Map map) {
        super(9997);

        this.map = map;
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
     * @param pos a Position Component of an Entity
     * @param fov a Field of View Component of the same Entity
     */
    private void processEntity(PositionComponent pos, FieldOfViewComponent fov) {
        updateFOV(pos.x, pos.y, fov);
    }

    /**
     * Clears the given fov of any Entity but restricts clearing
     * only to visible map area (by player)
     * to improve performance.
     * @param fov a Field of View Component of any Entity
     */
    private void clearFOV(int entityXPos, int entityYPos, FieldOfViewComponent fov) {
        int updateMargin = FieldOfViewComponent.VIEW_RADIUS + 4;
        for (int y = entityYPos + updateMargin; y >= entityYPos - updateMargin; --y) {
            for (int x = entityXPos - updateMargin; x < entityXPos + updateMargin; ++x) {
                if (map.inBoundaries(x, y)) {
                    fov.visibility[x][y] = false;
                }
            }
        }
    }

    /**
     * Updates the given fov component.
     * @param entityXPos x position of an Entity
     * @param entityYPos y position of the same Entity
     * @param fov a Field of Component of the same Entity
     */
    private void updateFOV(int entityXPos, int entityYPos, FieldOfViewComponent fov) {
        clearFOV(entityXPos, entityYPos, fov);

        // sends a ray trace line every degree
        for (int i = 0; i < 360; i++) {
            float x = MathUtils.cos(i * .01745f); // in radians, that's why there is a .175.. const
            float y = MathUtils.sin(i * .01745f);

            // calls update for that ray trace line
            updateOneLine(x, y, fov, entityXPos, entityYPos);
        }
    }

    /**
     * Updates all tiles which are on the ray trace line, starting from the Entity's position.
     * @param x a float value indicating in which direction the ray trace line goes (x axis)
     * @param y a float value indicating in which direction the ray trace line goes (y axis)
     * @param fov a Field of View Component of a given Entity
     * @param posXEntity a x position of the same Entity
     * @param posYEntity a y position of the same Entity
     */
    private void updateOneLine(float x, float y, FieldOfViewComponent fov,
                               int posXEntity, int posYEntity) {
        float posx = posXEntity + .5f;
        float posy = posYEntity + .5f;

        // Entity can only see in its radius (radius indicates that it is a circle)
        for (int i = 0; i < FieldOfViewComponent.VIEW_RADIUS; ++i) {
            int castedX = (int) posx;
            int castedY = (int) posy;

            // if any of the coordinate is outside the map we should omit it
            if (!(map.inBoundaries(castedX, castedY))) {
                continue;
            }

            fov.visibility[castedX][castedY] = true;

            // this piece of code was written to ensure that corners and walls are
            // rendered properly -> id does render them sometimes even though they are not in view range
            if (map.isPassable(castedX, castedY)) {
                checkMoves(moves, true, castedX, castedY, fov);
                checkMoves(diagonalMoves, false, castedX, castedY, fov);
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
    private void checkMoves(List<GridPoint2> moves, boolean regularMoves,
                            int castedX, int castedY, FieldOfViewComponent fov) {
        for (GridPoint2 move : moves) {
            int posxTemp = castedX + move.x;
            int posyTemp = castedY + move.y;

            if (map.inBoundaries(posxTemp, posyTemp)
                    && !map.isPassable(posxTemp, posyTemp)
                    && (regularMoves || isCorner(posxTemp, posyTemp, fov))) {
                fov.visibility[posxTemp][posyTemp] = true;
            }
        }
    }

    /**
     * Checks whether the given tile is a (VISIBLE!) corner.
     */
    private boolean isCorner(int x, int y, FieldOfViewComponent fov) {
        for (List<GridPoint2> corner : corners) {
            GridPoint2 firstMove = corner.get(0);
            GridPoint2 secondMove = corner.get(1);

            if (map.inBoundaries(x + firstMove.x, y + firstMove.y)
                    && map.inBoundaries(x + secondMove.x, y + secondMove.y)
                    && !map.isPassable(x + firstMove.x, y + firstMove.y)
                    && !map.isPassable(x + secondMove.x, y + secondMove.y)
                    && fov.visibility[x + firstMove.x][y + firstMove.y]
                    && fov.visibility[x + secondMove.x][y + secondMove.y]) {
                return true;
            }
        }

        return false;
    }
}
