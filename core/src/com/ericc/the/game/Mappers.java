package com.ericc.the.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.ericc.the.game.actions.AttackAction;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.*;


/**
 * ComponentMappers are the preferred (fastest) way of retrieving components from entities.
 * Instead of using Entity.getComponent() or creating a redundant ComponentMapper every time,
 * use the statics from here.
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<AffineAnimationComponent> affineAnimation = ComponentMapper.getFor(AffineAnimationComponent.class);
    public static final ComponentMapper<RenderableComponent> renderable = ComponentMapper.getFor(RenderableComponent.class);
    public static final ComponentMapper<FieldOfViewComponent> fov = ComponentMapper.getFor(FieldOfViewComponent.class);
    public static final ComponentMapper<StatsComponent> stats = ComponentMapper.getFor(StatsComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<AgencyComponent> agency = ComponentMapper.getFor(AgencyComponent.class);
    public static final ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<DeathComponent> death = ComponentMapper.getFor(DeathComponent.class);

    public static final ComponentMapper<MovementAction> movementAction = ComponentMapper.getFor(MovementAction.class);
    public static final ComponentMapper<TeleportAction> teleport = ComponentMapper.getFor(TeleportAction.class);
    public static final ComponentMapper<AttackAction> attack = ComponentMapper.getFor(AttackAction.class);
}
