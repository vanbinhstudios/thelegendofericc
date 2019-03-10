package com.ericc.the.game.Map;

public class Map {
    private int width, height;
    private boolean[][] map;

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[width][height];

        clearMap();
    }

    protected void clearMap() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = false;
            }
        }
    }

    protected void setTile(int x, int y, boolean passable) {
        map[x][y] = passable;
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
