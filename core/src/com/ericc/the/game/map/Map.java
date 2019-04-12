package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.helpers.FogOfWar;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.RectangularBitset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Map {

    public final HashMap<GridPoint, Entity> entityMap = new HashMap<>();
    public float[][] brightness;
    public float[][] saturation;
    public GridPoint entrance;
    public GridPoint exit;
    private int width, height;
    private RectangularBitset map;
    private int[][][] randomTileNumber;
    private int[][][] randomClutterNumber;
    private HashSet<GridPoint> passableTiles; ///< stores every passable tile in a map (AFTER THE FIRST GENERATION)
    // the above is NOT AN INVARIANT, this changes after spawning some entities on some tiles from this collection
    private HashSet<Room> rooms; ///< stores every room made while generating (without corridors)
    private FogOfWar fogOfWar;

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        brightness = new float[width][height];
        saturation = new float[width][height];
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
                randomClutterNumber[x][y][TileTextureIndicator.UP.getValue()] = MathUtils.random(0, 30 * Media.wallClutter.size);
                randomClutterNumber[x][y][TileTextureIndicator.FLOOR.getValue()] = MathUtils.random(0, 30 * Media.clutter.size);
            }
        }
    }

    void setTile(int x, int y, boolean passable) {
        map.set(x, y);

        if (passable) {
            passableTiles.add(new GridPoint(x, y));
        }
    }

    public int getRandomNumber(int x, int y, int direction) {
        return randomTileNumber[x][y][direction];
    }

    public int getRandomClutter(int x, int y, int direction) {
        return randomClutterNumber[x][y][direction];
    }

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

    public boolean inBoundaries(GridPoint xy) {
        return inBoundaries(xy.x, xy.y);
    }

    public boolean isFloor(int x, int y) {
        if (!inBoundaries(x, y)) {
            return false;
        }

        return map.get(x, y);
    }

    public boolean isFloor(GridPoint xy) {
        return isFloor(xy.x, xy.y);
    }

    public boolean isPassable(int x, int y) {
        if (!isFloor(x, y)) {
            return false;
        }

        Entity potentiallyBlocking = entityMap.get(new GridPoint(x, y));
        return potentiallyBlocking == null || !Mappers.collision.has(potentiallyBlocking);
    }

    public boolean isPassable(GridPoint xy) {
        return isPassable(xy.x, xy.y);
    }

    public Entity getEntity(GridPoint xy) {
        return entityMap.get(xy);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /**
     * @return random passable point in the 2D grid of this map
     * <p>
     * DISCLAIMER:
     * It does REMOVE the passable tile it is going to return from the passableTiles collection!
     */
    public GridPoint getRandomPassableTile() {
        GridPoint ret;

        try {
            ret = passableTiles.iterator().next();
        } catch (Exception e) {
            // TODO Instead of throwing an exception here, we would like to generate another map f.e.
            throw new IllegalStateException("Can't find room for more entities, check the map size.");
        }

        passableTiles.remove(ret);
        return ret;
    }

    /**
     * Returns random passable tile from any room which minimal dimension is
     * greater than 2. (This random passable tile for now is the right upper corner)
     */
    public GridPoint getRandomPassableTileFromRooms() {
        ArrayList<Room> roomsListed = new ArrayList<>(rooms);
        Room randomRoom = roomsListed.get(MathUtils.random(roomsListed.size() - 1));
        int ctr = 0;

        while (!(passableTiles.contains(randomRoom.getRightUpperCorner()) || randomRoom.getMinDimension() < 2)) {
            Collections.shuffle(roomsListed);
            randomRoom = roomsListed.get(MathUtils.random(roomsListed.size() - 1));
            ++ctr;

            if (ctr > 50) {
                // TODO Instead of throwing an exception here, we would like to generate another map f.e.
                throw new IllegalStateException("Cant find a room with width or length greater than 2.");
            }
        }

        passableTiles.remove(randomRoom.getRightUpperCorner());

        return randomRoom.getRightUpperCorner();
    }

    /**
     * Registers stairs in this map, determines whether that stairs are ascending or descending
     * and puts the entrance / exit in that position.
     */
    public void registerStairs(GridPoint xy, StaircaseDestination dest) {
        if (dest == StaircaseDestination.DESCENDING) {
            this.exit = xy;
        } else {
            this.entrance = xy;
        }
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

    public boolean hasBeenSeenByPlayer(GridPoint xy) {
        return fogOfWar.hasBeenSeenByPlayer(xy.x, xy.y);
    }

    public void makeFogCoverTheEntireMap() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (inBoundaries(i, j)) {
                    fogOfWar.markAsSeenByPlayer(i, j);
                }
            }
        }
    }

    public boolean hasAnimationDependency(GridPoint xy) {
        Entity potentiallyBlocking = entityMap.get(xy);
        if (potentiallyBlocking == null)
            return false;
        AnimationComponent animation = Mappers.animation.get(potentiallyBlocking);
        if (animation == null)
            return false;
        return animation.animation.isBlocking() && !animation.animation.isOver();
    }
}
