package com.ericc.the.game.mapGenerator;

import com.ericc.the.game.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generator {
    private int width, height;
    private Tile[][] map;
    private Random random;
    private int maximalRoomSize;
    private int minimalRoomSize;

    Generator(int width, int height, int maximalRoomSize, int minimalRoomSize) {
        this.width = width;
        this.height = height;
        this.maximalRoomSize = maximalRoomSize;
        this.minimalRoomSize = minimalRoomSize;

        random = new Random();
        map = new Tile[width][height];

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = new Tile(false);
            }
        }
    }

    /**
     * Indicates the rectangle of [x][y] to [x + height)[y + width)
     * @param x indicates the x axis of the top right corner
     * @param y same but indicates the y axis of the same corner
     * @param width
     * @param height
     */
    void generateMap(int x, int y, int width, int height, int depth) {
        if (width < this.minimalRoomSize || height < this.minimalRoomSize) {
            return;
        }

        if (width < this.maximalRoomSize || height < this.maximalRoomSize) {
            int roomX = random.nextInt(width / 8 + 1);
            int roomY = random.nextInt(height / 8 + 1);
            int roomWidth = Math.max(4, width / 8 + random.nextInt(Math.max(1 + width - roomX, 1)));
            int roomHeight = Math.max(4, height / 8 + random.nextInt(Math.max(1 + height - roomY, 1)));

            for (int i = x + roomX; i < x + roomWidth && i < this.width && i < x + width - 1; ++i) {
                for (int j = y + roomY; j < y + roomHeight && j < this.height && j < y + height - 1; ++j) {
                    map[i][j] = new Tile(true);
                }
            }

            return;
        }

        boolean pickHeight = random.nextBoolean();
        int slice = pickHeight ?
                random.nextInt(1 + (height - 1) / 2) + height / 4 :
                random.nextInt(1 + (width - 1) / 2) + width / 4;

        if (pickHeight) {
            generateMap(x, y, width, slice, depth + 1);
            generateMap(x, y + slice, width, height - slice, depth + 1);
            connectRooms((x + width) / 2, (y + slice) / 2, (x + width) / 2, (y + height) / 2);
        } else {
            generateMap(x, y, slice, height, depth + 1);
            generateMap(x + slice, y, width - slice, height, depth + 1);
            connectRooms((x + slice) / 2, (y + height) / 2, (x + width) / 2, (y + height) / 2);
        }
    }

    void generateMap() {
        generateMap(0, 0, width, height, 0);
    }

    void connectRooms(int x1, int y1, int x2, int y2) {
        boolean startFromFirst = x1 < x2;
        int starter = Math.min(x1, x2);

        while (starter <= Math.max(x1, x2)) {
            map[starter][startFromFirst ? y1 : y2] = new Tile(true);
            ++starter;
        }

        starter = Math.min(y1, y2);

        while (starter <= Math.max(y1, y2)) {
            map[startFromFirst ? x2 : x1][starter] = new Tile(true);
            ++starter;
        }
    }

    void printMap() {
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main (String[] arg) {
        Generator mapGenerator = new Generator(100, 20, 8, 4);
        mapGenerator.generateMap();
        mapGenerator.printMap();
    }
}
