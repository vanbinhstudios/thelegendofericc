package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.CurrentActionComponent;
import com.ericc.the.game.components.IntentionComponent;
import com.ericc.the.game.components.MobComponent;

import java.util.concurrent.ThreadLocalRandom;

public class AiSystem extends EntitySystem {
    private ImmutableArray<Entity> mobs;

    static private Action[] actions = {Actions.MOVE_DOWN, Actions.MOVE_LEFT,
            Actions.MOVE_RIGHT, Actions.MOVE_UP, Actions.NOTHING};

    @Override
    public void addedToEngine(Engine engine) {
        mobs = engine.getEntitiesFor(Family.all(MobComponent.class, CurrentActionComponent.class, IntentionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : mobs) {
            IntentionComponent intent = Mappers.intention.get(entity);

            int random = ThreadLocalRandom.current().nextInt(0, 5);
            intent.currentIntent = actions[random];
        }
    }

    public AiSystem () {
        super(10000);
    }
}
