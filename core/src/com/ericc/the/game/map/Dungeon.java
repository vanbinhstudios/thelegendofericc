package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.utils.ImmutableArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores the entire dungeon, level by level.
 * By level here we mean the map.
 */
public class Dungeon {
    private final HashMap<Integer, Level> levels;
    private final Engines engines;
    private int currentLevelNumber;

    public Dungeon(Engines engines) {
        levels = new HashMap<>();
        currentLevelNumber = 0;
        this.engines = engines;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void changeLevel(int levelNumber, InitialPlayerPosition initialPlayerPosition) {
        saveProgress();
        Level newLevel;
        if (!levels.containsKey(levelNumber)) {
            newLevel = LevelFactory.generate(levelNumber);
            levels.put(levelNumber, newLevel);
        } else {
            newLevel = levels.get(levelNumber);
        }
        currentLevelNumber = levelNumber;
        loadProgress(newLevel);
        placePlayer(initialPlayerPosition);
    }

    private void loadProgress(Level level) {
        CurrentMap.setMap(level.getMap());
        for (Entity entity : level.getEntities()) {
            engines.addEntity(entity);
        }
    }

    private void saveProgress() {
        Family family = Family.all(PositionComponent.class).exclude(PlayerComponent.class).get();
        ArrayList<Entity> entities = ImmutableArrayUtils.toArrayList(engines.getEntitiesFor(family));
        levels.put(currentLevelNumber, new Level(CurrentMap.map, entities));

        engines.removeFamily(family);
    }

    private void placeEntity(Entity entity, GridPoint2 desiredPosition) {
        PositionComponent entityPosition = Mappers.position.get(entity);

        for (GridPoint2 move : Moves.moves) {
            int x = desiredPosition.x + move.x;
            int y = desiredPosition.y + move.y;

            if (levels.get(currentLevelNumber).getMap().isPassable(x, y)) {
                entityPosition.x = x;
                entityPosition.y = y;
                return;
            }
        }

        throw new AssertionError("Entity could not be placed at ("
                + desiredPosition.x + ", " + desiredPosition.y + ")");
    }

    private void placePlayer(InitialPlayerPosition positionType) {
        Family family = Family.all(PlayerComponent.class).get();
        for (Entity entity : engines.getEntitiesFor(family)) {
            GridPoint2 position = null;
            switch (positionType) {
                case LEVEL_EXIT:
                    position = levels.get(currentLevelNumber).getMap().exit;
                    break;
                case LEVEL_ENTRANCE:
                    position = levels.get(currentLevelNumber).getMap().entrance;
                    break;
                case RANDOM_PASSABLE:
                    position = levels.get(currentLevelNumber).getMap().getRandomPassableTileFromRooms();
            }
            placeEntity(entity, position);
        }
    }

    public void generateFirstLevel() {
        Level firstLevel = LevelFactory.generate(0);
        levels.put(0, firstLevel);
        loadProgress(firstLevel);
        // Player will be manually placed in MainGame
    }
}
