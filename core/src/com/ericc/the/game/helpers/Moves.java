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

    // a helper data structure which reduces the code lines to check whether the given
    // position is a visible corner
    public static List<List<GridPoint2>> corners =
            Arrays.asList( // there are 4 cases here
                    Arrays.asList(
                            new GridPoint2(-1, 0), // each one of them has two coordinates
                            new GridPoint2(0, 1) // that should be checked for visible walls
                    ),
                    Arrays.asList(
                            new GridPoint2(0, 1),
                            new GridPoint2(1, 0)
                    ),
                    Arrays.asList(
                            new GridPoint2(1, 0),
                            new GridPoint2(0, -1)
                    ),
                    Arrays.asList(
                            new GridPoint2(0, -1),
                            new GridPoint2(-1, 0)
                    )
            );
}
