package com.ericc.the.game.map;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.helpers.FogOfWar;
import com.ericc.the.game.utils.RectangularBitset;

import java.util.HashSet;

public class Map {

    private int width, height;
    private RectangularBitset map;
    private int[][][] randomTileNumber;
    private int[][][] randomClutterNumber;
    private HashSet<GridPoint2> passableTiles; ///< stores every passable tile in a map (AFTER THE FIRST GENERATION)
    // the above is NOT AN INVARIANT, this changes after spawning some entities on some tiles from this collection
    private HashSet<Room> rooms; ///< stores every room made while generating (without corridors)
    private FogOfWar fogOfWar;

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.passableTiles = new HashSet<>();
        this.rooms = new HashSet<>();
        this.fogOfWar = new FogOfWar(width, height);
        map = new RectangularBitset(width, height);
        randomTileNumber = new int[width][height][TileTextureIndicator.countValues()];
        randomClutterNumber = new int[width][height][TileTextureIndicator.countValues()];

        clearMap();
    }

    protected void clearMap() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                map.clear(x, y);

                // generated to preserve the same tile's texture across every render
                randomTileNumber[x][y][TileTextureIndicator.UP.getValue()] = MathUtils.random(0, Media.wallUp.size - 1);
                randomTileNumber[x][y][TileTextureIndicator.RIGHT.getValue()] = MathUtils.random(0, Media.wallRight.size - 1);
                randomTileNumber[x][y][TileTextureIndicator.DOWN.getValue()] = MathUtils.random(0, Media.wallDown.size - 1);
                randomTileNumber[x][y][TileTextureIndicator.LEFT.getValue()] = MathUtils.random(0, Media.wallLeft.size - 1);
                randomTileNumber[x][y][TileTextureIndicator.FLOOR.getValue()] = MathUtils.random(0, Media.floors.size - 1);

                // table for terrain clutter generation
                randomClutterNumber[x][y][TileTextureIndicator.UP.getValue()] = MathUtils.random(0, 30*Media.wallClutter.size);
                randomClutterNumber[x][y][TileTextureIndicator.FLOOR.getValue()] = MathUtils.random(0, 30*Media.clutter.size);
            }
        }
    }

    void setTile(int x, int y, boolean passable) {
        map.set(x, y);

        if (passable) {
            passableTiles.add(new GridPoint2(x, y));
        }
    }

    public int getRandomNumber(int x, int y, int direction) {
        return randomTileNumber[x][y][direction];
    }

    public int getRandomClutter(int x, int y, int direction) { return randomClutterNumber[x][y][direction]; }

    /**
     * Checks whether the given point in 2D grid is in boundaries of a map.
     *
     * @param x x coordinate of a given point in the 2D grid
     * @param y y coordinate of a given point in the 2D grid
     * @return true if the given point is in the map's boundaries, false otherwise
     */
    public boolean inBoundaries(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean inBoundaries(GridPoint2 pos) {
        return inBoundaries(pos.x, pos.y);
    }

    public boolean isPassable(int x, int y) {
        if (!inBoundaries(x, y)) {
            return false;
        }

        return map.get(x, y);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /**
     * @return random passable point in the 2D grid of this map
     *
     * DISCLAIMER:
     * It does REMOVE the passable tile it is going to return from the passableTiles collection!
     */
    public GridPoint2 getRandomPassableTile() {
        GridPoint2 ret = passableTiles.iterator().next();
        passableTiles.remove(ret);
        return ret;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public HashSet<Room> getRooms() {
        return rooms;
    }

    /**
     * Registers a tile in a fog of war structure, marks it as seen.
     */
    public void markAsSeenByPlayer(int x, int y) {
        fogOfWar.markAsSeenByPlayer(x, y);
    }

    /**
     * Returns whether an object at given position has even been in any fov.
     */
    public boolean hasBeenSeenByPlayer(int x, int y) {
        return fogOfWar.hasBeenSeenByPlayer(x, y);
    }
}
