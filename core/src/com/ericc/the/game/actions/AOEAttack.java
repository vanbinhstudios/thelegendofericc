package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.effects.InflictDamage;
import com.ericc.the.game.entities.Attack;
import com.ericc.the.game.utils.GridPoint;

import java.util.List;

public class AOEAttack extends Action {
    // bottom left corner of the area we want to attack (relative to the position of the entity who casts the effect)
    public Model model;
    public int delay;
    public int power;
    public List<GridPoint> area;
    public Direction dir;
    public int cost;

    public AOEAttack(Model model, List<GridPoint> area, Direction dir, int delay, int power, int cost) {
        this.area = area;
        this.model = model;
        this.delay = delay;
        this.power = power;
        this.dir = dir;
        this.cost = cost;
    }


    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        if (pos.map.hasAnimationDependency(pos.xy)) {
            return true;
        }

        for (GridPoint p : area) {
            if (pos.map.hasAnimationDependency(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        FieldOfViewComponent fov = Mappers.fov.get(entity);
        pos.dir = dir;

        StatsComponent stats = Mappers.stats.get(entity);

        stats.mana -= cost;

        for (GridPoint p : area) {
            if (pos.map.isFloor(p) && !pos.xy.equals(p) && (fov == null ? true : fov.visibility.get(x, y))) {
                engine.addEntity(
                        new Attack(p, pos.map, power, model, dir)
                );
                Entity subject = pos.map.collisionMap.get(p);
                if (subject != null && subject != entity && Mappers.stats.has(subject)) {
                    new InflictDamage(power, entity).apply(subject, engine);
                }
            }
        }
    }
}
