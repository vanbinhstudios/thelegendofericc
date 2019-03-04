package com.ericc.the.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Entity;
import com.ericc.the.game.Enums.TILE;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {

    // Textures
    private Texture dun_void;
    private Texture floor_mid_1, floor_mid_2;
    private Texture wall_u_1, wall_u_2;
    private Texture wall_d_1, wall_d_2;
    private Texture wall_l_1, wall_l_2, wall_l_3;
    private Texture wall_r_1, wall_r_2, wall_r_3;
    private Texture wall_lu, wall_ru, wall_ld, wall_rd;

    // Camera centering
    public Tile center;

    // Data about the room
    public Chunk chunk;
    ArrayList<Entity> entities = new ArrayList<Entity>();

    // Code-texture maps
    private String[] mask_wall_left = {"001001001", "001001000", "000001001"};
    private String[] mask_wall_right = {"100100100", "100100000", "000100100"};
    private String[] mask_wall_up = {"111000000", "011000000", "110000000"};
    private String[] mask_wall_down = {"000000111", "000000011", "000000110"};
    private String[] mask_wall_up_left = {"000000001"};
    private String[] mask_wall_up_right = {"000000100"};
    private String[] mask_wall_down_left = {"001000000"};
    private String[] mask_wall_down_right = {"100000000"};

    public Room() {
        setImages();
        setTiles();
        encodeTiles();
    }

    private void setTiles() {
        chunk = new Chunk(16, 16, 16);

        int current_row = 0;
        int w, h;
        w = MathUtils.random(3, 6);
        h = MathUtils.random(3, 6);

        int central_row = chunk.n_rows / 2;
        int central_col = chunk.n_col / 2;

        int top_row = central_row + h;
        int bot_row = central_row - h;
        int right_col = central_col + w;
        int left_col = central_col - w;

        // Container for a single row
        ArrayList<Tile> tile_row = new ArrayList<Tile>();

        for (int row = 0; row < chunk.n_rows; row++) {
            for (int col = 0; col < chunk.n_col; col++) {
                Tile tile = new Tile(col, row, chunk.tile_size, TILE.VOID, dun_void);

                if (row < top_row && row > bot_row && col < right_col && col > left_col) {
                    tile.texture = randomFloor();
                    tile.type = TILE.FLOOR;
                }

                if (current_row == row) {
                    tile_row.add(tile);

                    if (row == chunk.n_rows - 1 && col == chunk.n_col - 1) {
                        chunk.tiles.add(tile_row);
                    }
                } else {
                    current_row = row;

                    chunk.tiles.add(tile_row);

                    tile_row = new ArrayList<Tile>();

                    tile_row.add(tile);
                }
            }
        }

        center = chunk.getTile(central_col, central_row);
    }

    private void updateImage(Tile tile) {
        if (Arrays.asList(mask_wall_left).contains(tile.code)) {
            tile.texture = wall_l_1;
        } else if (Arrays.asList(mask_wall_right).contains(tile.code)) {
            tile.texture = wall_r_1;
        } else if (Arrays.asList(mask_wall_up).contains(tile.code)) {
            tile.texture = wall_u_1;
        } else if (Arrays.asList(mask_wall_down).contains(tile.code)) {
            tile.texture = wall_d_1;
        } else if (Arrays.asList(mask_wall_up_left).contains(tile.code)) {
            tile.texture = wall_lu;
        } else if (Arrays.asList(mask_wall_down_left).contains(tile.code)) {
            tile.texture = wall_ld;
        } else if (Arrays.asList(mask_wall_up_right).contains(tile.code)) {
            tile.texture = wall_ru;
        } else if (Arrays.asList(mask_wall_down_right).contains(tile.code)) {
            tile.texture = wall_rd;
        }
    }

    private void setImages() {
        dun_void = new Texture("void.png");

        floor_mid_1 = new Texture("mid_dun_flr1.png");
        floor_mid_2 = new Texture("mid_dun_flr2.png");

        wall_d_1 = new Texture("d_dun_wall1.png");
        wall_u_1 = new Texture("u_dun_wall1.png");
        wall_l_1 = new Texture("l_dun_wall1.png");
        wall_r_1 = new Texture("r_dun_wall1.png");

        wall_ld = new Texture("ld_dun_wall.png");
        wall_rd = new Texture("rd_dun_wall.png");
        wall_lu = new Texture("lu_dun_wall.png");
        wall_ru = new Texture("ru_dun_wall.png");
    }

    private void encodeTiles() {
        for(ArrayList<Tile> row : chunk.tiles) {
            for(Tile tile : row) {

                int[] rows = {1,0,-1};
                int[] cols = {-1,0,1};

                for(int r: rows){
                    for(int c: cols){
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

    private Texture randomFloor() {
        int type = MathUtils.random(1, 3);
        // TODO: Add in more floor tile types
        switch (type) {

            case 1: return floor_mid_1;

            default: return floor_mid_2;
        }
    }

    public void dispose() {
        dun_void.dispose();

        floor_mid_1.dispose();
        floor_mid_2.dispose();

        wall_u_1.dispose();
        wall_d_1.dispose();
        wall_l_1.dispose();
        wall_r_1.dispose();
        wall_lu.dispose();
        wall_ru.dispose();
        wall_ld.dispose();
        wall_rd.dispose();
    }
}
