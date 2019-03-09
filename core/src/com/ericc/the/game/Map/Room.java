package com.ericc.the.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Entity;
import com.ericc.the.game.Enums.TILE;
import com.ericc.the.game.Media;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {

    // Camera centering
    public Vector2 center;

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
        Media.loadAssets();
        setTiles();
        encodeTiles();
    }

    // Method to generate an example chunk with an example room in the middle of the chunk
    private void setTiles() {
        chunk = new Chunk(16, 16);

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
                Tile tile = new Tile(col, row, 1, TILE.VOID, Media.dunVoid);

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

        center = new Vector2(centralCol, centralRow);
    }

    private void updateImage(Tile tile) {
        if (Arrays.asList(maskWallLeft).contains(tile.code)) {
            tile.texture = Media.wallL1;
        } else if (Arrays.asList(maskWallRight).contains(tile.code)) {
            tile.texture = Media.wallR1;
        } else if (Arrays.asList(maskWallUp).contains(tile.code)) {
            tile.texture = Media.wallU1;
        } else if (Arrays.asList(maskWallDown).contains(tile.code)) {
            tile.texture = Media.wallD1;
        } else if (Arrays.asList(maskWallUpLeft).contains(tile.code)) {
            tile.texture = Media.wallLU;
        } else if (Arrays.asList(maskWallDownLeft).contains(tile.code)) {
            tile.texture = Media.wallLD;
        } else if (Arrays.asList(maskWallUpRight).contains(tile.code)) {
            tile.texture = Media.wallRU;
        } else if (Arrays.asList(maskWallDownRight).contains(tile.code)) {
            tile.texture = Media.wallRD;
        }
    }

    // Assigning images to their texture objects

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
                return Media.floorMid1;
            default:
                return Media.floorMid2;
        }
    }
}
