package com.ericc.the.game.helpers;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Map;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * For reference:
 * http://www.roguebasin.com/index.php?title=Eligloscode
 */
public class FOV {
    private Player player;
    private static final int VIEW_RADIUS = 6;
    private boolean[][] visibility; ///< calculated every render, tiles that are visible atm
    private Map map;
    private FOG fog;

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

    public FOV(Player player, Map map, FOG fog) {
        this.player = player;
        this.map = map;
        this.fog = fog;

        visibility = new boolean[map.width()][map.height()];
    }

    private void clearFOV(int top, int bottom, int left, int right) {
        for (int y = top; y >= bottom; --y) {
            for (int x = left; x <= right; ++x) {
                visibility[x][y] = false;
            }
        }
    }

    public void updateFOV(int top, int bottom, int left, int right) {
        clearFOV(top, bottom, left, right);

        // sends a ray trace line every degree
        for (int i = 0; i < 360; i++) {
            float x = MathUtils.cos(i * .01745f); // in radians, that's why there is a .175.. const
            float y = MathUtils.sin(i * .01745f);

            updateOneLine(x, y);
        }
    }

    private void updateOneLine(float x, float y) {
        float posx = player.pos.x + .5f;
        float posy = player.pos.y + .5f;

        for (int i = 0; i < VIEW_RADIUS; ++i) {
            int castedX = (int) posx;
            int castedY = (int) posy;

            if (!(map.inBoundaries(castedX, castedY))) {
                continue;
            }

            visibility[castedX][castedY] = true;
            fog.registerTile(castedX, castedY);

            // this piece of code was written to ensure that corners and walls are
            // rendered properly -> id does render them sometimes even though they are not in view range
            if (map.isPassable(castedX, castedY)) {
                checkMoves(moves, true, castedX, castedY);
                checkMoves(cornerMoves, false, castedX, castedY);
            } else {
                // if this tile is a border, the hero does not see through that tile
                return;
            }

            posx += x;
            posy += y;
        }
    }

    /**
     * Returns whether an object at given position is in current fov.
     */
    public boolean inFOV(int x, int y) {
        return visibility[x][y];
    }

    private void checkMoves(ArrayList<GridPoint2> moves, boolean regularMoves, int castedX, int castedY) {
        for (GridPoint2 move : moves) {
            int posxTemp = castedX + move.x;
            int posyTemp = castedY + move.y;

            if (map.inBoundaries(posxTemp, posyTemp)
                    && !map.isPassable(posxTemp, posyTemp)
                    && (regularMoves || isCorner(posxTemp, posyTemp))) {
                visibility[posxTemp][posyTemp] = true;
                fog.registerTile(posxTemp, posyTemp);
            }
        }
    }

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

    /**
     * Checks whether the given tile is a corner.
     */
    public boolean isCorner(int x, int y) {
        for (ArrayList<GridPoint2> corner : corners) {
            GridPoint2 firstMove = corner.get(0);
            GridPoint2 secondMove = corner.get(1);

            if (map.inBoundaries(x + firstMove.x, y + firstMove.y)
                    && map.inBoundaries(x + secondMove.x, y + secondMove.y)
                    && !map.isPassable(x + firstMove.x, y + firstMove.y)
                    && !map.isPassable(x + secondMove.x, y + secondMove.y)
                    && inFOV(x + firstMove.x, y + firstMove.y)
                    && inFOV(x + secondMove.x, y + secondMove.y)) {
                return true;
            }
        }

        return false;
    }
}
