package com.ericc.the.game.helpers;

import com.ericc.the.game.utils.GridPoint;

import java.util.Arrays;
import java.util.List;

public class Moves {
    // a helper data structure with possible moves from one tile in horizontal and vertical directions
    public static List<GridPoint> moves =
            Arrays.asList(
                    new GridPoint(1, 0),
                    new GridPoint(0, 1),
                    new GridPoint(-1, 0),
                    new GridPoint(0, -1)
            );

    // stores moves on diagonals
    public static List<GridPoint> diagonalMoves =
            Arrays.asList(
                    new GridPoint(1, 1),
                    new GridPoint(-1, -1),
                    new GridPoint(-1, 1),
                    new GridPoint(1, -1)
            );

    public static List<GridPoint> standardAndDiagonals =
            Arrays.asList(
                    new GridPoint(1, 0),
                    new GridPoint(0, 1),
                    new GridPoint(-1, 0),
                    new GridPoint(0, -1),
                    new GridPoint(1, 1),
                    new GridPoint(-1, -1),
                    new GridPoint(-1, 1),
                    new GridPoint(1, -1)
            );
}
