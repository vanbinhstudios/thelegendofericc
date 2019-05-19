package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.helpers.FogOfWar;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.RectangularBitset;
import com.ericc.the.game.utils.WeightedGridPoint;

import java.util.*;

public class Map {

    public final HashMap<GridPoint, Entity> collisionMap = new HashMap<>();
    public final HashMap<GridPoint, Entity> trapMap = new HashMap<>();
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
        fov_tmp = new int[width][height];

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

        Entity potentiallyBlocking = collisionMap.get(new GridPoint(x, y));
        return potentiallyBlocking == null || !Mappers.collision.has(potentiallyBlocking);
    }

    public boolean isPassable(GridPoint xy) {
        return isPassable(xy.x, xy.y);
    }

    public Entity getEntity(GridPoint xy) {
        return collisionMap.get(xy);
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

    public void markAsSeenByPlayer(GridPoint xy) {
        fogOfWar.markAsSeenByPlayer(xy);
    }

    public boolean hasBeenSeenByPlayer(int x, int y) {
        return fogOfWar.hasBeenSeenByPlayer(x, y);
    }

    public boolean hasBeenSeenByPlayer(GridPoint xy) {
        return fogOfWar.hasBeenSeenByPlayer(xy.x, xy.y);
    }

    public boolean hasAnimationDependency(GridPoint xy) {
        Entity potentiallyBlocking = collisionMap.get(xy);
        if (potentiallyBlocking == null)
            return false;
        AnimationComponent animation = Mappers.animation.get(potentiallyBlocking);
        if (animation == null)
            return false;
        return animation.animation.isBlocking() && !animation.animation.isOver(animation.localTime);
    }

    public ArrayList<Direction> makePath(GridPoint source, GridPoint goal) {
        HashMap<GridPoint, GridPoint> parent = new HashMap<>();
        HashMap<GridPoint, Integer> currentWeight = new HashMap<>();
        PriorityQueue<WeightedGridPoint> queue = new PriorityQueue<>(
                new WeightedGridPoint.WeightedGridPointComparator()
        );

        queue.add(new WeightedGridPoint(source, 0));
        parent.put(source, source);
        currentWeight.put(source, 0);

        while (!queue.isEmpty()) {
            WeightedGridPoint top = queue.poll();

            if (top.xy.equals(goal)) {
                return reconstructPath(parent, source, goal);
            }

            for (GridPoint move : Moves.moves) {
                GridPoint pos = top.xy.add(move);

                if (!isPassable(pos) && !pos.equals(goal)) {
                    continue;
                }

                if (!currentWeight.containsKey(pos) || (top.weight + 1 < currentWeight.get(pos))) {
                    int priority = top.weight + 1 + estimatePathLength(pos, goal);

                    currentWeight.put(pos, top.weight + 1);
                    queue.add(new WeightedGridPoint(pos, priority));
                    parent.put(pos, top.xy);
                }
            }
        }

        return null;
    }

    private ArrayList<Direction> reconstructPath(HashMap<GridPoint, GridPoint> parent, GridPoint source, GridPoint goal) {
        ArrayList<Direction> directions = new ArrayList<>();

        while (goal != parent.get(goal)) {
            directions.add(Direction.fromGridPoints(goal, parent.get(goal)));
            goal = parent.get(goal);
        }

        Collections.reverse(directions);
        return directions;
    }

    private int estimatePathLength(GridPoint source, GridPoint goal) {
        return Math.abs(source.x - goal.x) + Math.abs(source.y - goal.y);
    }

    // Variables used to calculate FOV. Passing them all through the recursion would be ugly and costly.
    private float fov_radius;
    private int fov_x;
    private int fov_y;
    private int[][] fov_tmp;
    private int fov_version = 0;
    private List<GridPoint> fov_result;

    public List<GridPoint> calculateFOV(GridPoint xy, float radius) {
        this.fov_radius = radius;
        this.fov_x = xy.x;
        this.fov_y = xy.y;
        this.fov_version += 1;

        fov_tmp[fov_x][fov_y] = fov_version;

        fov_result = new ArrayList<>();
        fov_result.add(xy);

        castLight(1, 1.0f, 0.0f, 0, 1, 1, 0);
        castLight(1, 1.0f, 0.0f, 1, 0, 0, 1);
        castLight(1, 1.0f, 0.0f, 0, 1, -1, 0);
        castLight(1, 1.0f, 0.0f, 1, 0, 0, -1);
        castLight(1, 1.0f, 0.0f, 0, -1, 1, 0);
        castLight(1, 1.0f, 0.0f, -1, 0, 0, 1);
        castLight(1, 1.0f, 0.0f, 0, -1, -1, 0);
        castLight(1, 1.0f, 0.0f, -1, 0, 0, -1);

        return fov_result;
    }

    private void castLight(int row, float start, float end, int xx, int xy, int yx, int yy) {
        float newStart = 0.0f;
        if (start < end) {
            return;
        }
        boolean blocked = false;
        for (int distance = row; distance <= fov_radius && !blocked; ++distance) {
            int deltaY = -distance;
            for (int deltaX = -distance; deltaX <= 0; ++deltaX) {
                int currentX = fov_x + deltaX * xx + deltaY * xy;
                int currentY = fov_y + deltaX * yx + deltaY * yy;
                float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
                float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);

                if (!(currentX >= 0 && currentY >= 0 && currentX < this.width && currentY < this.height) || start < rightSlope) {
                    continue;
                } else if (end > leftSlope) {
                    break;
                }

                if (
                        fov_tmp[currentX][currentY] != fov_version
                        && deltaX * deltaX + deltaY * deltaY <= fov_radius * fov_radius
                ) {
                    fov_result.add(new GridPoint(currentX, currentY));
                    fov_tmp[currentX][currentY] = fov_version;
                }

                if (blocked) {
                    if (!isFloor(currentX, currentY)) {
                        newStart = rightSlope;
                    } else {
                        blocked = false;
                        start = newStart;
                    }
                } else {
                    if (!isFloor(currentX, currentY) && distance < fov_radius) {
                        blocked = true;
                        castLight(distance + 1, start, leftSlope, xx, xy, yx, yy);
                        newStart = rightSlope;
                    }
                }
            }
        }
    }

    public List<GridPoint> calculateRay(GridPoint origin, Direction dir, int length) {
        List<GridPoint> ret = new ArrayList<>();
        GridPoint offset = GridPoint.fromDirection(dir);
        for (int i = 0; i < length; ++i) {
            origin = origin.add(offset);
            if (!isFloor(origin))
                break;
            ret.add(origin);
        }
        return ret;
    }
}
