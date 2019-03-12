package com.ericc.the.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.components.AffineAnimationComponent;
import com.ericc.the.game.components.DirectionComponent;
import com.ericc.the.game.components.SpriteSheetComponent;

public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> affineAnimated;
    private ImmutableArray<Entity> directed;

    @Override
    public void addedToEngine(Engine engine) {
        affineAnimated = engine.getEntitiesFor(Family.all(SpriteSheetComponent.class, AffineAnimationComponent.class).get());
        directed = engine.getEntitiesFor(Family.all(SpriteSheetComponent.class, DirectionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : directed) {
            SpriteSheetComponent render = entity.getComponent(SpriteSheetComponent.class);
            DirectionComponent dir = entity.getComponent(DirectionComponent.class);

            render.sprite.setTexture(render.sheet[dir.direction.getValue()]);
        }
        for (Entity entity : affineAnimated) {
            SpriteSheetComponent render = entity.getComponent(SpriteSheetComponent.class);
            AffineAnimationComponent animation = entity.getComponent(AffineAnimationComponent.class);

            animation.update(deltaTime);
            render.transform = animation.getTransform();
        }
    }
}
