package com.ericc.the.game.map;

public class Map {
    private int width, height, tileSize;
    private Tile[][] map;
    private String[][] tileCodes;
    private Generator generator;
    private TileTexturer tileTexturer;

    public Map(int width, int height, int tileSize, int maximalRoomSize) {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        this.tileTexturer = new TileTexturer();

        prepareMap(maximalRoomSize);
    }

    private void prepareMap(int maximalRoomSize) {
        map = new Tile[width][height];
        tileCodes = new String[width][height];
        clearMap();

        this.generator = new Generator(width, height, maximalRoomSize, tileSize, this);
        this.generator.generateMap();
        generateTilesCodes();
    }

    private void clearMap() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = Tile.generateImpassableTile(i, j, tileSize);
            }
        }
    }

    void setTile(int x, int y, Tile temp) {
        map[x][y] = temp;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    private boolean inBoundaries(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void generateTileCode(int x, int y) {
        StringBuilder code = new StringBuilder();

        for (int xOffset = 1; xOffset >= -1; --xOffset) {
            for (int yOffset = -1; yOffset <= 1; ++yOffset) {
                int newX = x + xOffset, newY = y + yOffset;
                code.append(inBoundaries(newX, newY) ? map[newX][newY].toString() : "0");
            }
        }

        tileCodes[x][y] = code.toString();
    }

    private void generateTilesCodes() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                generateTileCode(x, y);
                map[x][y].texture = tileTexturer.getTileTexture(tileCodes[x][y], map[x][y].isPassable());
            }
        }
    }

    public void printMap() {
        this.generator.printMap();
    }

    public void dispose() {
        this.tileTexturer.dispose();
    }
}
