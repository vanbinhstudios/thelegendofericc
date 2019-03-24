package com.ericc.the.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.ericc.the.game.components.*;


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
    public static final ComponentMapper<CurrentActionComponent> currentAction = ComponentMapper.getFor(CurrentActionComponent.class);
    public static final ComponentMapper<IntentionComponent> intention = ComponentMapper.getFor(IntentionComponent.class);
    public static final ComponentMapper<FieldOfViewComponent> fov = ComponentMapper.getFor(FieldOfViewComponent.class);
    public static final ComponentMapper<AgilityComponent> agility = ComponentMapper.getFor(AgilityComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<IntelligenceComponent> intelligence = ComponentMapper.getFor(IntelligenceComponent.class);
    public static final ComponentMapper<MovementPointsComponent> movementPoints = ComponentMapper.getFor(MovementPointsComponent.class);
    public static final ComponentMapper<StrengthComponent> strength = ComponentMapper.getFor(StrengthComponent.class);
    public static final ComponentMapper<InitiativeComponent> initiative = ComponentMapper.getFor(InitiativeComponent.class);
    public static final ComponentMapper<ScreenBoundariesComponent> screenBoundaries = ComponentMapper.getFor(ScreenBoundariesComponent.class);
    public static final ComponentMapper<DescendingComponent> stairsComponent = ComponentMapper.getFor(DescendingComponent.class);
}
