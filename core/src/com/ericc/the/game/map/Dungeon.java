package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Media;
import com.ericc.the.game.components.MobComponent;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.PushableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the entire dungeon, level by level.
 * By level here we mean the map.
 */
public class Dungeon {
    private ArrayList<Map> levels;
    private ArrayList<ArrayList<Entity>> entities;
    private int currentLevel;
    private Engines engines;

    public Dungeon(Engines engines) {
        this.engines = engines;
        this.entities = new ArrayList<>();

        levels = new ArrayList<>();
        currentLevel = -1;
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

        if (currentLevel >= 0) {
            saveLastProgress();
        }

        ++currentLevel;

        if (entities.size() < currentLevel) {
            loadProgress();
        } else if (currentLevel > 0) {
            generateLevel(levels.get(currentLevel));
        }

        return levels.get(currentLevel);
    }

    /**
     * Changes the current Map to the previous one if it exists,
     * if it does not, we stay on the first level.
     */
    public Map goToPrevious() {
        if (currentLevel <= 0) {
            return levels.get(0);
        }

        saveLastProgress();
        --currentLevel;
        loadProgress();

        return levels.get(currentLevel);
    }

    /**
     * Creates a new map, abstraction is for future references.
     */
    private void addNewMap() {
        levels.add(new Generator(30, 30, 9).generateMap());
    }

    private void loadProgress() {
        for (Entity entity : entities.get(currentLevel)) {
            engines.addEntity(entity);
        }
    }

    /**
     * TODO Saves the last map's progress / entities [.get(currentLevel)]]
     */
    private void saveLastProgress() {
        System.out.println("COS");
        if (entities.isEmpty() || entities.size() == currentLevel) {
            entities.add(new ArrayList<>());
        }

        entities.get(currentLevel).clear();

        Family family = Family.all(PositionComponent.class).exclude(PlayerComponent.class).get();
        ImmutableArray<Entity> arr = engines.getEntitiesFor(family);

        for (Entity entity : arr) {
            entities.get(currentLevel).add(entity);
        }

        engines.removeFamily(family);
    }

    public void generateLevel(Map map) {
        for (int i = 0; i < 15; i++) {
            engines.addEntity(new Mob(map.getRandomPassableTile()));
        }

        for (int i = 0; i < 10; i++) {
            engines.addEntity(new PushableObject(map.getRandomPassableTile(), Media.crate));
        }
    }
}
