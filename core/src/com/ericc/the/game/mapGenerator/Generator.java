package com.ericc.the.game.mapGenerator;

import java.util.Random;

public class Generator {
    private int width, height;
    private Tile[][] map;
    private Random random;
    private int maximalRoomSize;

    Generator(int width, int height, int maximalRoomSize) {
        this.width = width;
        this.height = height;
        this.maximalRoomSize = maximalRoomSize;

        random = new Random();
        map = new Tile[width][height];

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = Tile.generateNotAvailableTile();
            }
        }
    }

    /**
     * fills the given room (it randomizes a little bit the given arguments),
     * after that it return the middle of the room that it has just generated
     */
    Coordinates<Integer> fillRoomReturnCenter(int x, int y, int width, int height) {
        int roomX = random.nextInt(width / 8 + 1);
        int roomY = random.nextInt(height / 8 + 1);
        int roomWidth = Math.max(4, width / 8 + random.nextInt(Math.max(1 + width - roomX, 1)));
        int roomHeight = Math.max(4, height / 8 + random.nextInt(Math.max(1 + height - roomY, 1)));
        Coordinates<Integer> center = new Coordinates<Integer>(0, 0);

        for (int i = x + roomX; i < x + roomWidth && i < this.width && i < x + width - 1; ++i) {
            for (int j = y + roomY; j < y + roomHeight && j < this.height && j < y + height - 1; ++j) {
                map[i][j] = Tile.generateRoomTile();

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
     * Indicates the rectangle of [x][y] to [x + width)[y + height)
     * @param x indicates the x axis of the top right corner
     * @param y same but indicates the y axis of the same corner
     * @param width the width of a rectangle (from x till x + width (excluded))
     * @param height the height of a rectangle (from y till y + height (excluded))
     */
    Coordinates<Integer> generateMap(int x, int y, int width, int height) {
        if (width < this.maximalRoomSize || height < this.maximalRoomSize) {
            return fillRoomReturnCenter(x, y, width, height);
        }

        boolean pickHeight = random.nextBoolean();
        int slice = pickHeight ?
                random.nextInt(1 + (height - 1) / 2) + height / 4 :
                random.nextInt(1 + (width - 1) / 2) + width / 4;

        Coordinates<Integer> firstRoom, secondRoom;

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
    void generateMap() {
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
    void connectRooms(Coordinates<Integer> firstRoom, Coordinates<Integer> secondRoom) {
        boolean startFromFirst = firstRoom.x < secondRoom.x;
        int starter = Math.min(firstRoom.x, secondRoom.x);

        while (starter <= Math.max(firstRoom.x, secondRoom.x)) {
            if (!map[starter][startFromFirst ? firstRoom.y : secondRoom.y].isAvailable()) {
                map[starter][startFromFirst ? firstRoom.y : secondRoom.y] = Tile.generateCorridor();
            }

            ++starter;
        }

        starter = Math.min(firstRoom.y, secondRoom.y);

        while (starter <= Math.max(firstRoom.y, secondRoom.y)) {
            if (!map[startFromFirst ? secondRoom.x : firstRoom.x][starter].isAvailable()) {
                map[startFromFirst ? secondRoom.x : firstRoom.x][starter] = Tile.generateCorridor();
            }

            ++starter;
        }
    }

    /**
     * Prints the entire map, using toString method of a Tile object.
     */
    void printMap() {
        for (int j = 0; j < height; ++j) { // we have to draw the map in this way, cause i = x, j = y
            for (int i = 0; i < width; ++i) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] arg) {
        Generator mapGenerator = new Generator(100, 20, 8);
        mapGenerator.generateMap();
        mapGenerator.printMap();
    }
}
