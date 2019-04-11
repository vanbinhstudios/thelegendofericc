package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.entities.Stairs;

import java.util.ArrayList;

public class LevelFactory {
    private final static int MAP_WIDTH = 40;
    private final static int MAP_HEIGHT = 40;
    private final static int ROOM_SIZE = 10;
    private final static int MOBS_COUNT = 20;
    private final static int CRATES_COUNT = 15;

    public static Level generate(int levelNumber) {
        Map map = new MapGenerator(MAP_WIDTH, MAP_HEIGHT, ROOM_SIZE).generateMap();

        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < MOBS_COUNT; i++) {
            Mob mob = new Mob(map.getRandomPassableTile(), map);
            entities.add(mob);
        }

        for (int i = 0; i < CRATES_COUNT; i++) {
            entities.add(new PushableObject(map.getRandomPassableTile(), map));
        }

        Stairs exit = new Stairs(map.getRandomPassableTileFromRooms(), map,
                StaircaseDestination.DESCENDING
        );

        entities.add(exit);
        map.registerStairs(exit.pos.xy, StaircaseDestination.DESCENDING);

        if (levelNumber > 0) {
            Stairs entrance = new Stairs(map.getRandomPassableTileFromRooms(), map,
                    StaircaseDestination.ASCENDING
            );

            entities.add(entrance);
            map.registerStairs(entrance.pos.xy, StaircaseDestination.ASCENDING);
        }

        return new Level(map, entities);
    }
}
