package com.ericc.the.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Entity;
import com.ericc.the.game.Enums.TILE;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    // Textures
    private Texture dunVoid;
    private Texture floorMid1, floorMid2;
    private Texture wallU1, wallU2;
    private Texture wallD1, wallD2;
    private Texture wallL1, wallL2, wallL3;
    private Texture wallR1, wallR2, wallR3;
    private Texture wallLU, wallRU, wallLD, wallRD;

    // Camera centering
    public Tile center;

    // Data about the room
    public Chunk chunk;
    ArrayList<Entity> entities = new ArrayList<Entity>();

    // Code-texture masks
    // TODO: Change strings to integers and use binary operators for code comparsion
    private String[] maskWallLeft = {"001001001", "001001000", "000001001"};
    private String[] maskWallRight = {"100100100", "100100000", "000100100"};
    private String[] maskWallUp = {"111000000", "011000000", "110000000"};
    private String[] maskWallDown = {"000000111", "000000011", "000000110"};
    private String[] maskWallUpLeft = {"000000001"};
    private String[] maskWallUpRight = {"000000100"};
    private String[] maskWallDownLeft = {"001000000"};
    private String[] maskWallDownRight = {"100000000"};

    public Room() {
        setImages();
        setTiles();
        encodeTiles();
    }

    // Method to generate an example chunk with an example room in the middle of the chunk
    private void setTiles() {
        chunk = new Chunk(16, 16, 16);

        int currentRow = 0;
        int w, h;

        w = MathUtils.random(3, 6);
        h = MathUtils.random(3, 6);

        // Center of the chunk
        int centralRow = chunk.nRows / 2;
        int centralCol = chunk.nCol / 2;

        // Room size limits
        int topRow = centralRow + h;
        int botRow = centralRow - h;
        int rightCol = centralCol + w;
        int leftCol = centralCol - w;

        // Container for a single row
        ArrayList<Tile> tileRow = new ArrayList<Tile>();

        for (int row = 0; row < chunk.nRows; row++) {
            for (int col = 0; col < chunk.nCol; col++) {
                // Defaulting tile to VOID type
                Tile tile = new Tile(col, row, chunk.tileSize, TILE.VOID, dunVoid);

                // If the tile is within the limits of the room type is set to FLOOR
                if (row < topRow && row > botRow && col < rightCol && col > leftCol) {
                    tile.texture = randomFloor();
                    tile.type = TILE.FLOOR;
                }

                // Adding tiles to their rows and finished rows to the chunk itself
                if (currentRow == row) {
                    tileRow.add(tile);

                    if (row == chunk.nRows - 1 && col == chunk.nCol - 1) {
                        chunk.tiles.add(tileRow);
                    }

                } else {
                    currentRow = row;
                    chunk.tiles.add(tileRow);

                    tileRow = new ArrayList<Tile>();
                    tileRow.add(tile);
                }

            }
        }

        center = chunk.getTile(centralCol, centralRow);
    }

    private void updateImage(Tile tile) {
        if (Arrays.asList(maskWallLeft).contains(tile.code)) {
            tile.texture = wallL1;
        } else if (Arrays.asList(maskWallRight).contains(tile.code)) {
            tile.texture = wallR1;
        } else if (Arrays.asList(maskWallUp).contains(tile.code)) {
            tile.texture = wallU1;
        } else if (Arrays.asList(maskWallDown).contains(tile.code)) {
            tile.texture = wallD1;
        } else if (Arrays.asList(maskWallUpLeft).contains(tile.code)) {
            tile.texture = wallLU;
        } else if (Arrays.asList(maskWallDownLeft).contains(tile.code)) {
            tile.texture = wallLD;
        } else if (Arrays.asList(maskWallUpRight).contains(tile.code)) {
            tile.texture = wallRU;
        } else if (Arrays.asList(maskWallDownRight).contains(tile.code)) {
            tile.texture = wallRD;
        }
    }

    // Assigning images to their texture objects
    private void setImages() {
        dunVoid = new Texture("void.png");

        floorMid1 = new Texture("mid_dun_flr1.png");
        floorMid2 = new Texture("mid_dun_flr2.png");

        wallD1 = new Texture("d_dun_wall1.png");
        wallU1 = new Texture("u_dun_wall1.png");
        wallL1 = new Texture("l_dun_wall1.png");
        wallR1 = new Texture("r_dun_wall1.png");

        wallLD = new Texture("ld_dun_wall.png");
        wallRD = new Texture("rd_dun_wall.png");
        wallLU = new Texture("lu_dun_wall.png");
        wallRU = new Texture("ru_dun_wall.png");
    }

    // Generating tile codes and assigning textures
    private void encodeTiles() {
        for (ArrayList<Tile> row : chunk.tiles) {
            for (Tile tile : row) {

                for (int r = 1; r >= -1; r--) {
                    for (int c = -1; c <= 1; c++) {
                        tile.code += chunk.getTileCode(tile.row + r, tile.col + c);
                    }
                }

                updateImage(tile);

                System.out.print("code for tile: " + tile.code);
                System.out.print("\n");
            }
        }

    }

    // TODO: Randomizer methods for wall tiles
    // TODO: Add in more floor tile types
    private Texture randomFloor() {
        int type = MathUtils.random(1, 3);

        switch (type) {
            case 1:
                return floorMid1;
            default:
                return floorMid2;
        }
    }

    public void dispose() {
        dunVoid.dispose();

        floorMid1.dispose();
        floorMid2.dispose();

        wallU1.dispose();
        wallD1.dispose();
        wallL1.dispose();
        wallR1.dispose();
        wallLU.dispose();
        wallRU.dispose();
        wallLD.dispose();
        wallRD.dispose();
    }
}
