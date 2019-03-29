package com.ericc.the.game.helpers;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.List;

public class Moves {
    // a helper data structure with possible moves from one tile in horizontal and vertical directions
    public static List<GridPoint2> moves =
            Arrays.asList(
                    new GridPoint2(1, 0),
                    new GridPoint2(0, 1),
                    new GridPoint2(-1, 0),
                    new GridPoint2(0, -1)
            );

    // stores moves on diagonals
    public static List<GridPoint2> diagonalMoves =
            Arrays.asList(
                    new GridPoint2(1, 1),
                    new GridPoint2(-1, -1),
                    new GridPoint2(-1, 1),
                    new GridPoint2(1, -1)
            );

    public static List<GridPoint2> standardAndDiagonals =
            Arrays.asList(
                    new GridPoint2(1, 0),
                    new GridPoint2(0, 1),
                    new GridPoint2(-1, 0),
                    new GridPoint2(0, -1),
                    new GridPoint2(1, 1),
                    new GridPoint2(-1, -1),
                    new GridPoint2(-1, 1),
                    new GridPoint2(1, -1)
            );
}
