package com.ericc.the.game.Map;

import java.util.ArrayList;

public class Chunk {

    public int n_rows, n_col;
    public int tile_size;
    public ArrayList<ArrayList<Tile>> tiles;

    public Chunk(int n_rows, int n_col, int tile_size) {
        tiles = new ArrayList<ArrayList<Tile>>();
        this.n_rows = n_rows;
        this.n_col = n_col;
        this.tile_size = tile_size;
    }

    public Tile getTile(int row, int col) {
        ArrayList<Tile> selected_row;

        if (row >= 0 && row < tiles.size()) {
            selected_row = tiles.get(row);

            if (selected_row != null && col < selected_row.size() && col >= 0) {
                return selected_row.get(col);
            }
        }
        return null;
    }

    public String getTileCode(int row, int col) {
        ArrayList<Tile> selected_row;

        if(row >= 0 && row < tiles.size()) {
            selected_row = tiles.get(row);

            if (selected_row != null && col < selected_row.size() && col >= 0) {
                return selected_row.get(col).isPassable() ? "1" : "0";
            }
        }
        return "0";
    }

}
