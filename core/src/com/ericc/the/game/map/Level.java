package com.ericc.the.game.map;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.Objects;

public class Level {
    private final Map map;
    private final ArrayList<Entity> entities;

    public Level(Map map, ArrayList<Entity> entities) {
        this.map = map;
        this.entities = entities;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Objects.equals(getMap(), level.getMap()) &&
                Objects.equals(getEntities(), level.getEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMap(), getEntities());
    }
}
