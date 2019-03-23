package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AffineAnimationComponent;
import com.ericc.the.game.components.DirectionComponent;
import com.ericc.the.game.components.SpriteSheetComponent;

/**
 * The system responsible for:
 * - updating sprite textures accordingly to entity state changes
 * - updating the sprite-local transforms based on affine animations
 */
public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> affineAnimated; // Entities with affine animation currently attached.
    private ImmutableArray<Entity> directed; // Entities with appearance varying with their orientation.

    @Override
    public void addedToEngine(Engine engine) {
        affineAnimated = engine.getEntitiesFor(Family.all(SpriteSheetComponent.class,
                            AffineAnimationComponent.class).get());
        directed = engine.getEntitiesFor(Family.all(SpriteSheetComponent.class,
                    DirectionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        // Update sprites according to orientation (face direction) of the Entity.
        for (Entity entity : directed) {
            SpriteSheetComponent render = Mappers.spriteSheet.get(entity);
            DirectionComponent dir = Mappers.direction.get(entity);

            render.sprite.setRegion(render.sheet[dir.direction.getValue()]);
        }
        // Update the animation-derived local transform.
        for (Entity entity : affineAnimated) {
            SpriteSheetComponent render = Mappers.spriteSheet.get(entity);
            AffineAnimationComponent animation = Mappers.affineAnimation.get(entity);

            animation.update(deltaTime);
            render.transform.set(animation.getTransform());
        }
    }
}
