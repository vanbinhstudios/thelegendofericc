package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DirtyFlag;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SafetyMapComponent;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.WeightedGridPoint;

import java.util.PriorityQueue;

public class SafetyMapSystem extends IteratingSystem {

    public SafetyMapSystem(int priority) {
        super(Family.all(SafetyMapComponent.class, PositionComponent.class, DirtyFlag.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final int INF = 1000000;
        PositionComponent pos = Mappers.position.get(entity);
        Map map = pos.map;
        SafetyMapComponent sm = Mappers.safety.get(entity);

        // Initialization
        if (sm.width != pos.map.width() || sm.height != pos.map.height()) {
            sm.width = pos.map.width();
            sm.height = pos.map.height();
            sm.distance = new int[sm.width][sm.height];
        }

        for (int y = 0; y < sm.height; ++y)
            for (int x = 0; x < sm.width; ++x) {
                sm.distance[x][y] = INF;
            }

        // First pass
        sm.distance[pos.xy.x][pos.xy.y] = 0;

        PriorityQueue<WeightedGridPoint> queue = new PriorityQueue<>(
                new WeightedGridPoint.WeightedGridPointComparator()
        );

        queue.add(new WeightedGridPoint(pos.xy, 0));

        while (!queue.isEmpty()) {
            WeightedGridPoint top = queue.poll();

            for (GridPoint move : Moves.moves) {
                GridPoint point = top.xy.add(move);

                if (!map.isFloor(point)) {
                    continue;
                }

                if (top.weight + 1 < sm.distance[point.x][point.y]) {
                    int priority = top.weight + 1;

                    sm.distance[point.x][point.y] = priority;
                    queue.add(new WeightedGridPoint(point, priority));
                }
            }
        }

        // Second pass
        for (int y = 0; y < sm.height; ++y)
            for (int x = 0; x < sm.width; ++x) {
                if (sm.distance[x][y] < INF) {
                    sm.distance[x][y] *= -1.1;
                    queue.add(new WeightedGridPoint(new GridPoint(x, y), sm.distance[x][y]));
                }
            }

        while (!queue.isEmpty()) {
            WeightedGridPoint top = queue.poll();

            for (GridPoint move : Moves.moves) {
                GridPoint point = top.xy.add(move);

                if (!map.isFloor(point)) {
                    continue;
                }

                if (top.weight + 1 < sm.distance[point.x][point.y]) {
                    int priority = top.weight + 1;

                    sm.distance[point.x][point.y] = priority;
                    queue.add(new WeightedGridPoint(point, priority));
                }
            }
        }
    }
}
