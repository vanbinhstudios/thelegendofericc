package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.entities.*;

import java.util.ArrayList;

public class LevelFactory {
    private final static int MAP_WIDTH = 40;
    private final static int MAP_HEIGHT = 40;
    private final static int ROOM_SIZE = 10;
    private final static int MOBS_COUNT = 20;
    private final static int TANKS_COUNT = 5;
    private final static int CRATES_COUNT = 15;

    public static Level generate(int levelNumber, Dungeon dungeon) {
        Map map = new MapGenerator(MAP_WIDTH, MAP_HEIGHT, ROOM_SIZE).generateMap();

        ArrayList<Entity> entities = new ArrayList<>();
        LevelFactory.generateMobs(levelNumber, map, entities);

        for (int i = 0; i < CRATES_COUNT; i++) {
            entities.add(new PushableObject(map.getRandomPassableTile(), map));
        }

        Stairs exit = new Stairs(map.getRandomPassableTileFromRooms(), map, dungeon,
                StaircaseDestination.DESCENDING
        );

        entities.add(exit);
        map.registerStairs(exit.pos.xy, StaircaseDestination.DESCENDING);

        if (levelNumber > 0) {
            Stairs entrance = new Stairs(map.getRandomPassableTileFromRooms(), map, dungeon,
                    StaircaseDestination.ASCENDING
            );

            entities.add(entrance);
            map.registerStairs(entrance.pos.xy, StaircaseDestination.ASCENDING);
        }

        return new Level(map, entities);
    }

    private static void generateStandardMobs(Map map, ArrayList<Entity> entities, int ctr) {
        for (int i = 0; i < ctr; i++) {
            Mob mob = new Mob(map.getRandomPassableTile(), map);
            entities.add(mob);
        }
    }

    private static void generateTankMobs(Map map, ArrayList<Entity> entities, int ctr) {
        for (int i = 0; i < ctr; i++) {
            MobTank mob = new MobTank(map.getRandomPassableTile(), map);
            entities.add(mob);
        }
    }

    private static void generateMageMobs(Map map, ArrayList<Entity> entities, int ctr) {
        for (int i = 0; i < ctr; i++) {
            MobMage mob = new MobMage(map.getRandomPassableTile(), map);
            entities.add(mob);
        }
    }

    private static void generateArcherMobs(Map map, ArrayList<Entity> entities, int ctr) {
        for (int i = 0; i < ctr; i++) {
            MobArcher mob = new MobArcher(map.getRandomPassableTile(), map);
            entities.add(mob);
        }
    }

    private static void generateMobs(int levelNumber, Map map, ArrayList<Entity> entities) {
        if (levelNumber == 0) {
            generateMageMobs(map, entities, 20);
            //generateTankMobs(map, entities, 3);
            //generateStandardMobs(map, entities, 15);
            //generateArcherMobs(map, entities, 20);
        } else if (levelNumber == 1) {
            generateMageMobs(map, entities, 5);
            generateStandardMobs(map, entities, 10);
            generateTankMobs(map, entities, 10);
        } else if (levelNumber == 2) {
            generateStandardMobs(map, entities, 5);
            generateMageMobs(map, entities, 10);
            generateTankMobs(map, entities, 10);
        }
    }
}
