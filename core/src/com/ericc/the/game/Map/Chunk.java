package com.ericc.the.game.Map;

import java.util.ArrayList;

public class Chunk {
    public int nRows, nCol;
    public ArrayList<ArrayList<Tile>> tiles;

    public Chunk(int nRows, int nCol) {
        tiles = new ArrayList<ArrayList<Tile>>();
        this.nRows = nRows;
        this.nCol = nCol;
    }

    public Tile getTile(int row, int col) {
        ArrayList<Tile> selectedRow;

        if (row >= 0 && row < tiles.size()) {
            selectedRow = tiles.get(row);

            if (selectedRow != null
                    && col < selectedRow.size()
                    && col >= 0) {
                return selectedRow.get(col);
            }
        }
        return null;
    }

    public String getTileCode(int row, int col) {
        ArrayList<Tile> selectedRow;

        if (row >= 0 && row < tiles.size()) {
            selectedRow = tiles.get(row);

            if (selectedRow != null && col < selectedRow.size() && col >= 0) {
                return selectedRow.get(col).isPassable() ? "1" : "0";
            }

        }
        return "0";
    }

}
