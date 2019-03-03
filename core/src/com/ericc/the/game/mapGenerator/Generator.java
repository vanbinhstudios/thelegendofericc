package com.ericc.the.game.mapGenerator;

import com.ericc.the.game.Tile;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    private int width, height;
    private Tile[][] map;
    private int granuality;
    private Random random;

    Generator(int width, int height, int granuality) {
        this.width = width;
        this.height = height;
        this.granuality = granuality;
        random = new Random();
        map = new Tile[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                map[i][j] = new Tile(false);
            }
        }
    }

    /**
     * Indicates the rectangle of [x][y] to [x + height][y + width]
     * @param x indicates the x axis of the top right corner
     * @param y same but indicates the y axis of the same corner
     * @param width
     * @param height
     */
    void generateMap(int x, int y, int width, int height) {
        if (width * height < granuality) {
            for (int i = x; i < x + width && i < this.width; ++i) {
                for (int j = y; j < y + height && j < this.height; ++j) {
                    System.out.println(j + " " + i);
                    map[j][i] = new Tile(true);
                }
            }

            return;
        }

        boolean pickHeight = random.nextBoolean();
        System.out.println("width " + width + ", height " + height);
        int slice = 1 + (pickHeight && height - 1 > 0 ? random.nextInt(height - 1)
                : width - 1 <= 0 ? 0 : random.nextInt(width - 1));
//        System.out.println(pickHeight ? "slice " + slice + " of " + height : "slice " + slice + " of " + width);

        if (pickHeight) {
//            System.out.println("calling " + x + " " + y + " " + width + " " + slice);
//            System.out.println("calling " + (x + slice) + " " + y + " " + width + " " + (height - slice));
            generateMap(x, y, width, slice);
            generateMap(x + slice, y, width, height - slice);
        } else {
//            System.out.println("calling " + x + " " + y + " " + slice + " " + height);
//            System.out.println("calling " + (x) + " " + (y + slice) + " " + (width - slice) + " " + (height));
            generateMap(x, y, slice, height);
            generateMap(x, y + slice, width - slice, height);
        }
    }

    void generateMap() {
        generateMap(0, 0, width, height);
    }

    void printMap() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main (String[] arg) {
        Generator mapGenerator = new Generator(100, 20, 80);
        mapGenerator.generateMap();
        mapGenerator.printMap();

    }
}
