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
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.helpers.ScreenBoundsGetter;
import com.ericc.the.game.map.Map;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldOfViewSystem extends EntitySystem {

    private Map map;
    private ImmutableArray<Entity> otherMobs;
    private ImmutableArray<Entity> heroes;
    private ScreenBoundsGetter sbh;

    // a helper data structure with possible moves from one tile in horizontal and vertical directions
    private static ArrayList<GridPoint2> moves =
            new ArrayList<>(Arrays.asList(
                    new GridPoint2(1, 0),
                    new GridPoint2(0, 1),
                    new GridPoint2(-1, 0),
                    new GridPoint2(0, -1)
            ));

    // stores moves on diagonals
    private static ArrayList<GridPoint2> cornerMoves =
            new ArrayList<>(Arrays.asList(
                    new GridPoint2(1, 1),
                    new GridPoint2(-1, -1),
                    new GridPoint2(-1, 1),
                    new GridPoint2(1, -1)
            ));

    private static ArrayList<ArrayList<GridPoint2>> corners =
            new ArrayList<>(
                    Arrays.asList(
                            new ArrayList<>(
                                    Arrays.asList(
                                            new GridPoint2(-1, 0),
                                            new GridPoint2(0, 1)
                                    )
                            ),
                            new ArrayList<>(
                                    Arrays.asList(
                                            new GridPoint2(0, 1),
                                            new GridPoint2(1, 0)
                                    )
                            ),
                            new ArrayList<>(
                                    Arrays.asList(
                                            new GridPoint2(1, 0),
                                            new GridPoint2(0, -1)
                                    )
                            ),
                            new ArrayList<>(
                                    Arrays.asList(
                                            new GridPoint2(0, -1),
                                            new GridPoint2(-1, 0)
                                    )
                            )
                    )
            );

    public FieldOfViewSystem(Map map, ScreenBoundsGetter sbh) {
        this.map = map;
        this.sbh = sbh;
    }

    @Override
    public void addedToEngine(Engine engine) {
        otherMobs = engine.getEntitiesFor(
                Family.all(PositionComponent.class, FieldOfViewComponent.class).exclude(PlayerComponent.class).get()
        );
        heroes = engine.getEntitiesFor(
                Family.all(PositionComponent.class, FieldOfViewComponent.class, PlayerComponent.class).get()
        );
    }

    @Override
    public void update(float deltaTime) {
        sbh.update();

        for (Entity entity : heroes) {
            processEntity(Mappers.position.get(entity), Mappers.fov.get(entity), true);
        }

        for (Entity entity : otherMobs) {
            processEntity(Mappers.position.get(entity), Mappers.fov.get(entity), false);
        }
    }

    private void processEntity(PositionComponent pos, FieldOfViewComponent fov, boolean isHero) {
        updateFOV(sbh.top, sbh.bottom, sbh.left, sbh.right, pos.x, pos.y, fov, isHero);
    }

    private void clearFOV(int top, int bottom, int left, int right, FieldOfViewComponent fov) {
        for (int y = top; y >= bottom; --y) {
            for (int x = left; x <= right; ++x) {
                if (map.inBoundaries(x, y)) {
                    fov.visibility[x][y] = false;
                }
            }
        }
    }

    private void updateFOV(int top, int bottom,
                          int left, int right,
                          int posXentity, int posYentity,
                          FieldOfViewComponent fov, boolean isHero) {
        clearFOV(top, bottom, left, right, fov);

        // sends a ray trace line every degree
        for (int i = 0; i < 360; i++) {
            float x = MathUtils.cos(i * .01745f); // in radians, that's why there is a .175.. const
            float y = MathUtils.sin(i * .01745f);

            updateOneLine(x, y, fov, posXentity, posYentity, isHero);
        }
    }

    private void updateOneLine(float x, float y, FieldOfViewComponent fov,
                               int posXEntity, int posYEntity, boolean isHero) {
        float posx = posXEntity + .5f;
        float posy = posYEntity + .5f;

        for (int i = 0; i < FieldOfViewComponent.VIEW_RADIUS; ++i) {
            int castedX = (int) posx;
            int castedY = (int) posy;

            if (!(map.inBoundaries(castedX, castedY))) {
                continue;
            }

            fov.visibility[castedX][castedY] = true;

            if (isHero) {
                map.registerTile(castedX, castedY);
            }

            // this piece of code was written to ensure that corners and walls are
            // rendered properly -> id does render them sometimes even though they are not in view range
            if (map.isPassable(castedX, castedY)) {
                checkMoves(moves, true, castedX, castedY, fov, isHero);
                checkMoves(cornerMoves, false, castedX, castedY, fov, isHero);
            } else {
                // if this tile is a border, the hero does not see through that tile
                return;
            }

            posx += x;
            posy += y;
        }
    }

    private void checkMoves(ArrayList<GridPoint2> moves, boolean regularMoves,
                            int castedX, int castedY, FieldOfViewComponent fov, boolean isHero) {
        for (GridPoint2 move : moves) {
            int posxTemp = castedX + move.x;
            int posyTemp = castedY + move.y;

            if (map.inBoundaries(posxTemp, posyTemp)
                    && !map.isPassable(posxTemp, posyTemp)
                    && (regularMoves || isCorner(posxTemp, posyTemp, fov))) {
                fov.visibility[posxTemp][posyTemp] = true;

                if (isHero) {
                    map.registerTile(posxTemp, posyTemp);
                }
            }
        }
    }

    /**
     * Checks whether the given tile is a  (VISIBLE!) corner.
     */
    private boolean isCorner(int x, int y, FieldOfViewComponent fov) {
        for (ArrayList<GridPoint2> corner : corners) {
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
