package com.ericc.the.game.map;

public class Map {
    private int width, height, tileSize;
    private Tile[][] map;
    private String[][] tileCodes; ///< A code of a single tile is stored in this array
    private MazeGenerator mazeGenerator;
    private TileTexturer tileTexturer;

    public Map(int width, int height, int tileSize, int maximalRoomSize) {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        this.tileTexturer = new TileTexturer();

        prepareMap(maximalRoomSize);
    }

    /**
     * Prepares map to be used externally, that means:
     * 1) allocating memory for a given data set
     * 2) generating passable and impassable tiles
     * 3) generating a code for every tile
     *
     * @param maximalRoomSize see
     */
    private void prepareMap(int maximalRoomSize) {
        map = new Tile[width][height];
        tileCodes = new String[width][height];
        clearMap();

        this.mazeGenerator = new MazeGenerator(width, height, maximalRoomSize, tileSize, this);
        this.mazeGenerator.generateMap();
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

    /**
     * Checks whether the given 2D grid parameters [x,y] are in boundaries of a map.
     * @param x a x-axis point in a grid
     * @param y a y-axis point in a grid
     * @return true if a given grid point is in map boundaries, false otherwise
     */
    private boolean inBoundaries(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Generates a code for a given tile in a grid of a map.
     * @param x first coordinate of a given tile
     * @param y second coordinate of a given tile
     *
     * Disclaimer:
     * A tile code is a string consisting of a 9 characters
     * (each one either '0' - indicates impassable tile -  or '1' - indicates passable tile)
     * f.e. lets imagine we have a grid of:
     *          0 0 1
     *          1 0 1
     *          1 1 1
     *  and we want to calculate the code for the middle '0' (coordinates [1, 1])
     *  the code would be the following: "111100110"
     *  (and to be more readable for humans I will write it like that too "111 100 110" -
     *  so to calculate the code when we look at the grid we go from right to left and from bottom to top)
     */
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

    /**
     * Generates a code for every tile in a map.
     */
    private void generateTilesCodes() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                generateTileCode(x, y);
                map[x][y].texture = tileTexturer.getTileTexture(tileCodes[x][y], map[x][y].isPassable());
            }
        }
    }

    /**
     * Prints map to stdout. Should be used to debug f.e. tiles' codes.
     */
    public void printMap() {
        this.mazeGenerator.printMap();
    }

    /**
     * Disposes of any used texture.
     */
    public void dispose() {
        this.tileTexturer.dispose();
    }
}
