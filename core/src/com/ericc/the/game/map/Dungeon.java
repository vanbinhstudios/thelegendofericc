package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.components.DescendingComponent;
import com.ericc.the.game.components.MobComponent;
import com.ericc.the.game.components.PlayerComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.entities.Stairs;
import com.ericc.the.game.helpers.Moves;
import com.sun.istack.internal.NotNull;

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

        if (currentLevel < entities.size()) {
            System.out.println(levels.isEmpty());
            loadProgress();
        } else if (currentLevel > 0) {
            generateLevel(levels.get(currentLevel));
        }

        if (currentLevel > 0) {
            placePlayersNextToStairs(levels.get(currentLevel).entrance);
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

        placePlayersNextToStairs(levels.get(currentLevel).exit);
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
            System.out.println("Loading");
            engines.addEntity(entity);
        }
    }

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
        System.out.println("call");
        for (int i = 0; i < 1; i++) {
            engines.addEntity(new Mob(map.getRandomPassableTile()));
        }

        for (int i = 0; i < 1; i++) {
            engines.addEntity(new PushableObject(map.getRandomPassableTile(), Media.crate));
        }

        engines.addEntity(new Stairs(map.makeStairs(true), Media.stairsDown, true));
        engines.addEntity(new Stairs(map.makeStairs(false), Media.stairsUp, false));
    }

    private void placePlayersNextToStairs(GridPoint2 stairsPosition) {
        ImmutableArray<Entity> players = engines.getEntitiesFor(Family.all(PlayerComponent.class).get());
        System.out.println("Placing player on");

        for (Entity player : players) {
            PositionComponent playersPosition = Mappers.position.get(player);

            for (GridPoint2 move : Moves.moves) {
                int x = stairsPosition.x + move.x;
                int y = stairsPosition.y + move.y;

                if (levels.get(currentLevel).isPassable(x, y)) {
                    System.out.println("Placing player on : [" + x + ", " + y + "]");
                    playersPosition.x = x;
                    playersPosition.y = y;
                    return;
                }
            }
        }
    }
}
