package com.ericc.the.game.map;

import com.badlogic.ashley.core.Family;
import com.ericc.the.game.Engines;
import com.ericc.the.game.components.MobComponent;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;

import java.util.ArrayList;

/**
 * This class stores the entire dungeon, level by level.
 * By level here we mean the map.
 */
public class Dungeon {
    private ArrayList<Map> levels;
    private int currentLevel;
    private Engines engines;

    public Dungeon(Engines engines) {
        this.engines = engines;

        levels = new ArrayList<>();
        currentLevel = 0;
    }

    public void addMap(Map map) {
        levels.add(map);
    }

    /**
     * Changes the current Map to the next one if it exists,
     * if not it does create one.
     */
    public Map goToNext() {
        if (levels.isEmpty() || currentLevel + 1 == levels.size()) {
            addNewMap();
        }

        if (levels.size() == 1) {
            return levels.get(0);
        }

        saveLastProgress();

        return levels.get(++currentLevel);
    }

    /**
     * Changes the current Map to the previous one if it exists,
     * if it does not, we stay on the first level.
     */
    public Map goToPrevious() {
        if (currentLevel == 0) {
            return levels.get(0);
        }

        return levels.get(--currentLevel);
    }

    /**
     * Creates a new map, abstraction is for future references.
     */
    private void addNewMap() {
        levels.add(new Generator(30, 30, 9).generateMap());
    }

    /**
     * TODO Saves the last map's progress / entities [.get(currentLevel)]]
     */
    private void saveLastProgress() {
        System.out.println("COS");
        engines.removeFamily(Family.all(PositionComponent.class).exclude(PlayerComponent.class).get());
    }
}
