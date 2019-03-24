package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Media;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.entities.Stairs;

import java.util.ArrayList;

public class LevelFactory {
    private final static int ROOM_WIDTH = 30;
    private final static int ROOM_HEIGHT = 30;
    private final static int ROOM_SIZE = 9;
    private final static int MOBS_COUNT = 10;
    private final static int CRATES_COUNT = 10;

    public static Level generate(int levelNumber) {
        Map map = new MapGenerator(ROOM_WIDTH, ROOM_HEIGHT, ROOM_SIZE).generateMap();

        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < MOBS_COUNT; i++) {
            entities.add(new Mob(map.getRandomPassableTile()));
        }

        for (int i = 0; i < CRATES_COUNT; i++) {
            entities.add(new PushableObject(map.getRandomPassableTile(), Media.crate));
        }

        entities.add(new Stairs(map.makeStairs(StaircaseDestination.DESCENDING),
                Media.stairsDown,
                StaircaseDestination.DESCENDING)
        );

        if (levelNumber > 0) {
            entities.add(new Stairs(map.makeStairs(StaircaseDestination.ASCENDING),
                    Media.stairsUp,
                    StaircaseDestination.ASCENDING)
            );
        }

        return new Level(map, entities);
    }
}
