package com.ericc.the.game.mapGenerator;

public class Map {
    private int width, height;
    private Tile[][] map;

    Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];

        clearMap();
    }

    protected void clearMap() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = Tile.generateNotAvailableTile();
            }
        }
    }

    protected void setTile(int x, int y, Tile temp) {
        map[x][y] = temp;
    }

    protected Tile getTile(int x, int y) {
        return map[x][y];
    }
}
