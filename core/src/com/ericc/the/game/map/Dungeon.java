package com.ericc.the.game.map;

import com.badlogic.ashley.core.Family;
import com.ericc.the.game.Engines;
import com.ericc.the.game.components.MobComponent;

import java.util.ArrayList;

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

    public Map goToPrevious() {
        return levels.get(--currentLevel);
    }

    private void addNewMap() {
        levels.add(new Generator(30, 30, 9).generateMap());
    }

    private void saveLastProgress() {
        System.out.println("COS");
        engines.removeEntitiesFromRealtimeEngine(Family.all(MobComponent.class).get());
    }
}
