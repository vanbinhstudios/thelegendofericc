package com.ericc.the.game.map;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

public class MazeGenerator {
    private int width, height, tileSize;
    private Map map;
    private Random random;
    private int maximalRoomSize;

    /**
     * MazeGenerator class.
     *
     * @param width           dimension of a map to generate, must be > 1 (NOT IN PIXELS, IT IS A CUSTOM METRIC)
     * @param height          same as above
     * @param maximalRoomSize if any of the dimensions of a generated room are less than
     *                        this value, the room is being generated, otherwise it calls
     *                        the generator method recursively
     * @param tileSize        size of a single tile in pixels
     * @param map             a Map object to generate passages in
     *                        <p>
     *                        Disclaimer:
     *                        Note that it uses O(width * height) memory, so time complexity is also bound by that value.
     *                        Keep that in mind when calling the MazeGenerator methods.
     */
    MazeGenerator(int width, int height, int maximalRoomSize, int tileSize, Map map) {
        assert width > 1 && height > 1;

        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.maximalRoomSize = maximalRoomSize;

        random = new Random();
        this.map = map;
    }

    /**
     * MazeGenerator class ctor without a given Map object.
     */
    MazeGenerator(int width, int height, int maximalRoomSize, int tileSize) {
        this(width, height, maximalRoomSize, tileSize, new Map(width, height, tileSize, maximalRoomSize));
    }

    /**
     * Fills the given room (it randomizes the given arguments a little bit),
     * after that it returns the middle of the room that it has just generated
     */
    private GridPoint2 fillRoomReturnCenter(int x, int y, int width, int height) {
        int roomX = random.nextInt(width / 8 + 1);
        int roomY = random.nextInt(height / 8 + 1);
        int roomWidth = Math.max(4, width / 8 + random.nextInt(Math.max(1 + width - roomX, 1)));
        int roomHeight = Math.max(4, height / 8 + random.nextInt(Math.max(1 + height - roomY, 1)));
        GridPoint2 center = new GridPoint2(0, 0);

        for (int i = x + roomX; i < x + roomWidth && i < this.width && i < x + width - 1; ++i) {
            for (int j = y + roomY; j < y + roomHeight && j < this.height && j < y + height - 1; ++j) {
                map.setTile(i, j, Tile.generateRoomTile(i, j, tileSize));

                if (center.x == 0) {
                    center.y++;
                }
            }

            center.x++;
        }

        center.x = x + roomX + (center.x / 2);
        center.y = y + roomY + (center.y / 2);

        return center;
    }

    /**
     * Recursive function that generates a room in a given
     * rectangle of [x][y] to [x + width)[y + height) if (and only if)
     * one of the dimensions passed is less than maximalRoomSize.
     *
     * @param x      indicates the x axis of the top right corner
     * @param y      same but indicates the y axis of the same corner
     * @param width  the width of a rectangle (from x till x + width (excluded))
     * @param height the height of a rectangle (from y till y + height (excluded))
     */
    private GridPoint2 generateMap(int x, int y, int width, int height) {
        if (width < this.maximalRoomSize || height < this.maximalRoomSize) {
            return fillRoomReturnCenter(x, y, width, height);
        }

        boolean pickHeight = random.nextBoolean();
        int slice = pickHeight ?
                random.nextInt(1 + (height - 1) / 2) + height / 4 :
                random.nextInt(1 + (width - 1) / 2) + width / 4;

        GridPoint2 firstRoom, secondRoom;

        if (pickHeight) {
            firstRoom = generateMap(x, y, width, slice);
            secondRoom = generateMap(x, y + slice, width, height - slice);
        } else {
            firstRoom = generateMap(x, y, slice, height);
            secondRoom = generateMap(x + slice, y, width - slice, height);
        }

        connectRooms(firstRoom, secondRoom);
        return random.nextBoolean() ? firstRoom : secondRoom;
    }

    /**
     * Generates the entire map.
     */
    public void generateMap() {
        generateMap(1, 1, width - 1, height - 1);
    }

    /**
     * Given two centres of rooms, connects them making a new corridor.
     *
     * @param firstRoom  centre of a first room
     * @param secondRoom centre of a second room
     *                   <p>
     *                   Disclaimer:
     *                   Rooms can be given in any order. The function indicates
     *                   the minimal (x-axis wise) and draws a corridor from x1 (minimal one)
     *                   to x2 (maximal). Then does the same (from the position it is on at that time)
     *                   but considers the y-axis this time - so starts from [x2][y1] and draws
     *                   a corridor till it reaches [x2][y2]. (Assumptions for the explanation x1 <= x2
     *                   y1 <= y2, the function look for that order on its own, though.)
     */
    private void connectRooms(GridPoint2 firstRoom, GridPoint2 secondRoom) {
        boolean startFromFirst = firstRoom.x < secondRoom.x;
        int starter = Math.min(firstRoom.x, secondRoom.x);

        while (starter <= Math.max(firstRoom.x, secondRoom.x)) {
            if (!map.getTile(starter, startFromFirst ? firstRoom.y : secondRoom.y).isPassable()) {
                map.setTile(starter, startFromFirst ? firstRoom.y : secondRoom.y,
                        Tile.generateCorridor(starter, startFromFirst ? firstRoom.y : secondRoom.y, tileSize));
            }

            ++starter;
        }

        starter = Math.min(firstRoom.y, secondRoom.y);

        while (starter <= Math.max(firstRoom.y, secondRoom.y)) {
            if (!map.getTile(startFromFirst ? secondRoom.x : firstRoom.x, starter).isPassable()) {
                map.setTile(startFromFirst ? secondRoom.x : firstRoom.x, starter,
                        Tile.generateCorridor(startFromFirst ? secondRoom.x : firstRoom.x, starter, tileSize));
            }

            ++starter;
        }
    }

    /**
     * Prints the entire map, using toString method of a Tile object.
     */
    public void printMap() {
        for (int j = height - 1; j >= 0; --j) { // we have to draw the map in this way, cause i = x, j = y
            for (int i = 0; i < width; ++i) {
                System.out.print(map.getTile(i, j));
            }
            System.out.println();
        }
    }
}
