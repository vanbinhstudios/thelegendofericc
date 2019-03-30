package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.ImmutableArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores the entire dungeon, level by level.
 * By level here we mean the map.
 */
public class Dungeon {
    private final HashMap<Integer, Level> levels;
    private final GameEngine engines;
    private int currentLevelNumber;

    public Dungeon(GameEngine engines) {
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
        placePlayer(initialPlayerPosition, newLevel.getMap());
    }

    private void loadProgress(Level level) {
        for (Entity entity : level.getEntities()) {
            engines.addEntity(entity);
        }
    }

    private void saveProgress() {
        Family notPlayersFamily = Family.all(PositionComponent.class).exclude(PlayerComponent.class).get();
        Family playersFamily = Family.all(PlayerComponent.class).get();
        ArrayList<Entity> notPlayers = ImmutableArrayUtils.toArrayList(engines.getEntitiesFor(notPlayersFamily));
        ImmutableArray<Entity> players = engines.getEntitiesFor(playersFamily);
        Map currentMap = players.get(0).getComponent(PositionComponent.class).map;
        levels.put(currentLevelNumber, new Level(currentMap, notPlayers));

        engines.removeFamily(notPlayersFamily);
    }

    private void placeEntity(Entity entity, GridPoint desiredPosition, Map map) {
        PositionComponent entityPosition = Mappers.position.get(entity);

        for (GridPoint move : Moves.moves) {
            GridPoint newPosition = desiredPosition.add(move);

            if (map.isFloor(newPosition.x, newPosition.y)) {
                entityPosition.map.entityMap.remove(newPosition);
                entityPosition.xy = newPosition;
                entityPosition.map = map;
                entityPosition.map.entityMap.put(newPosition, entity);
                return;
            }
        }

        throw new AssertionError("Entity could not be placed at ("
                + desiredPosition.x + ", " + desiredPosition.y + ")");
    }

    private void placePlayer(InitialPlayerPosition positionType, Map map) {
        Family family = Family.all(PlayerComponent.class).get();
        for (Entity entity : engines.getEntitiesFor(family)) {
            GridPoint position = null;
            switch (positionType) {
                case LEVEL_EXIT:
                    position = levels.get(currentLevelNumber).getMap().exit;
                    break;
                case LEVEL_ENTRANCE:
                    position = levels.get(currentLevelNumber).getMap().entrance;
                    break;
                case RANDOM_PASSABLE:
                    position = levels.get(currentLevelNumber).getMap().getRandomPassableTileFromRooms();
                    break;
                default:
                    throw new IllegalArgumentException("Not a proper positionType was given");
            }
            placeEntity(entity, position, map);
        }
    }

    public void generateFirstLevel() {
        Level firstLevel = LevelFactory.generate(0);
        levels.put(0, firstLevel);
        loadProgress(firstLevel);
        // Player will be manually placed in MainGame
    }

    public Map getCurrentMap() {
        return levels.get(getCurrentLevelNumber()).getMap();
    }
}
