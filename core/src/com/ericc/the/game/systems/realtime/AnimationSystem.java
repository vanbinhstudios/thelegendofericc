package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.components.RenderableComponent;

public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> affineAnimated; // Entities with affine animation currently attached.


    public AnimationSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        affineAnimated = engine.getEntitiesFor(Family.all(RenderableComponent.class, AnimationComponent.class).get());
    }

    // TODO support for multiple animations taking place at once
    @Override
    public void update(float deltaTime) {
        // Update the animation-derived local transform.
        for (Entity entity : affineAnimated) {
            RenderableComponent render = Mappers.renderable.get(entity);
            AnimationComponent animation = Mappers.animation.get(entity);

            animation.animation.update(deltaTime);
            animation.animation.apply(render);
            if (animation.animation.isOver()) {
                entity.remove(AnimationComponent.class);
            }
        }
    }
}
