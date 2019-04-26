package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Effects;
import com.ericc.the.game.components.*;

public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> animated; // Entities with affine state currently attached.


    public AnimationSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        animated = engine.getEntitiesFor(Family.all(PositionComponent.class, RenderableComponent.class, AnimationComponent.class).get());
    }

    // TODO support for multiple animations taking place at once
    @Override
    public void update(float deltaTime) {
        // Update the state-derived local transform.
        for (Entity entity : animated) {
            PositionComponent pos = Mappers.position.get(entity);
            RenderableComponent render = Mappers.renderable.get(entity);
            AnimationComponent animation = Mappers.animation.get(entity);

            render.region = render.model.sheet[pos.dir.getValue()];

            animation.localTime += deltaTime;
            animation.animation.apply(pos.dir, animation.localTime, render);

            if (Mappers.healthbar.has(entity)) {
                HealthbarComponent bar = Mappers.healthbar.get(entity);
                animation.animation.apply(pos.dir, animation.localTime, bar);
            }

            if (animation.animation.isOver(animation.localTime)) {
                Effects.setAnimation(entity, AnimationState.IDLE);
            }
        }
    }
}
