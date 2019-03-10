package com.ericc.the.game.Map;

import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.Media;

public class Map {
    private int width, height;
    private boolean[][] map;
    private int[][][] randomTileNumber;

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[width][height];
        randomTileNumber = new int[width][height][TileTextureIndicator.countValues()];

        clearMap();
    }

    protected void clearMap() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                map[x][y] = false;

                randomTileNumber[x][y][TileTextureIndicator.UP.getValue()] = MathUtils.random(0, Media.wallUp.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.RIGHT.getValue()] = MathUtils.random(0, Media.wallRight.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.DOWN.getValue()] = MathUtils.random(0, Media.wallDown.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.LEFT.getValue()] = MathUtils.random(0, Media.wallLeft.size() - 1);
                randomTileNumber[x][y][TileTextureIndicator.FLOOR.getValue()] = MathUtils.random(0, Media.floors.size() - 1);
            }
        }
    }

    protected void setTile(int x, int y, boolean passable) {
        map[x][y] = passable;
    }

    public int getRandomNumber(int x, int y, int direction) {
        return randomTileNumber[x][y][direction];
    }

    public boolean isPassable(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;
        return map[x][y];
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
