package com.ericc.the.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.ericc.the.game.components.AffineAnimationComponent;
import com.ericc.the.game.components.DirectionComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SpriteSheetComponent;


/**
 * ComponentMappers are the preferred (fastest) way of retrieving components from entities.
 * Instead of using Entity.getComponent() or creating a redundant ComponentMapper every time,
 * use the statics from here.
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<DirectionComponent> direction = ComponentMapper.getFor(DirectionComponent.class);
    public static final ComponentMapper<AffineAnimationComponent> affineAnimation = ComponentMapper.getFor(AffineAnimationComponent.class);
    public static final ComponentMapper<SpriteSheetComponent> spriteSheet = ComponentMapper.getFor(SpriteSheetComponent.class);

}
