package com.ericc.the.game.map;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Map {

    private int width, height;
    private boolean[][] map;
    private int[][][] randomTileNumber;
    private HashSet<GridPoint2> passableTiles; ///< stores every passable tile in a map
    private HashSet<Room> rooms; ///< stores every room made while generating (without corridors)

    // Helper structure, static in order not to waste memory
    private static ArrayList<GridPoint2> moves = new ArrayList<>(
            Arrays.asList(
                    new GridPoint2(-2, 0),
                    new GridPoint2(2, 0),
                    new GridPoint2(0, -2),
                    new GridPoint2(0, 2)
            )
    );

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.passableTiles = new HashSet<>();
        this.rooms = new HashSet<>();
        map = new boolean[width][height];
        randomTileNumber = new int[width][height][TileTextureIndicator.countValues()];

        clearMap();
    }

    protected void clearMap() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                map[x][y] = false;

                // generated to preserve the same tile's texture across every render
                randomTileNumber[x][y][TileTextureIndicator.UP.getValue()] = MathUtils.random(0, Media.wallUp.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.RIGHT.getValue()] = MathUtils.random(0, Media.wallRight.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.DOWN.getValue()] = MathUtils.random(0, Media.wallDown.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.LEFT.getValue()] = MathUtils.random(0, Media.wallLeft.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.FLOOR.getValue()] = MathUtils.random(0, Media.floors.size() - 1);
            }
        }
    }

    void setTile(int x, int y, boolean passable) {
        map[x][y] = passable;

        if (passable) {
            passableTiles.add(new GridPoint2(x, y));

            /* in order to have a map which is more condensed we will sometimes connect rooms or corridors
               which are next to each other, the constant in shouldConnect indicates the % of that action happening */
            for (GridPoint2 move : moves) {
                int newX = x + move.x;
                int newY = y + move.y;
                boolean shouldConnect = MathUtils.random(0, 100) < 7; // for now it is 7%

                if (shouldConnect && inBoundaries(newX, newY) && map[newX][newY]) {
                    map[(newX + x) / 2][(newY + y) / 2] = true;
                }
            }
        }
    }

    public int getRandomNumber(int x, int y, int direction) {
        return randomTileNumber[x][y][direction];
    }

    /**
     * Checks whether the given point in 2D grid is in boundaries of a map.
     *
     * @param x x coordinate of a given point in the 2D grid
     * @param y y coordinate of a given point in the 2D grid
     * @return true if the given point is in the map's boundaries, false otherwise
     */
    private boolean inBoundaries(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean inBoundaries(GridPoint2 pos) {
        return inBoundaries(pos.x, pos.y);
    }

    public boolean isPassable(int x, int y) {
        if (!inBoundaries(x, y)) {
            return false;
        }

        return map[x][y];
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /**
     * @return random passable point in the 2D grid of this map
     */
    public GridPoint2 getRandomPassableTile() {
        return passableTiles.iterator().next();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public HashSet<Room> getRooms() {
        return rooms;
    }
}
