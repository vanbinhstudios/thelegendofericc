package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.CurrentActionComponent;
import com.ericc.the.game.components.InitiativeComponent;
import com.ericc.the.game.components.IntentionComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.entities.Stairs;

import java.util.Comparator;
import java.util.HashMap;

public class ActionSetterSystem extends SortedIteratingSystem {

    private final HashMap<GridPoint2, Entity> interactives;
    private boolean shouldResetIntention = false;

    public ActionSetterSystem() {
        super(Family.all(PositionComponent.class, CurrentActionComponent.class, InitiativeComponent.class).get(),
                new EntityComparator(), 1); // Depends on InitiativeSystem and AiSystem
        interactives = new HashMap<>();
    }

    // Entity comparator based on value value in the current turn
    private static class EntityComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity myself, Entity other) {
            return Integer.compare(Mappers.initiative.get(other).value, Mappers.initiative.get(myself).value);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        // Movables contains entities capable of independent movement and decision-making
        // MovableInitiatives contains entity-value pair for the current turn
        // Interactives contains a map of all interactive entities and their positions
        super.addedToEngine(engine);

        Family family = Family.all(PositionComponent.class,
                CurrentActionComponent.class).get();
        engine.addEntityListener(family, new InteractivesHandler());

        for (Entity entity : engine.getEntitiesFor(family)) {
            PositionComponent pos = Mappers.position.get(entity);
            interactives.put(new GridPoint2(pos.x, pos.y), entity);
        }
    }

    @Override
    public void processEntity(Entity currentEntity, float deltaTime) {
        this.shouldResetIntention = true;
        boolean canProceed = analyzeMove(currentEntity);
        // If the entity can proceed it's intent can be put into action
        if (canProceed) {
            Mappers.currentAction.get(currentEntity).action = Mappers.intention.get(currentEntity).currentIntent;
            updateHashMapData(currentEntity);
        }

        // Reset intention after the move is made
        if (shouldResetIntention) {
            Mappers.intention.get(currentEntity).currentIntent = Actions.NOTHING;
        }
    }

    private boolean analyzeMove(Entity entity) {
        // This section will require expansion after more actions are added in.
        IntentionComponent intent = Mappers.intention.get(entity);
        if (intent.currentIntent instanceof MovementAction) {
            return analyzeMovementAction(entity, intent);
        }
        return true;
    }

    private boolean analyzeMovementAction(Entity entity, IntentionComponent intent) {
        PositionComponent pos = Mappers.position.get(entity);
        MovementAction move = (MovementAction) intent.currentIntent;
        int targetX, targetY;

        targetX = ((move.direction == Direction.RIGHT) ? 1 : 0)
                + ((move.direction == Direction.LEFT) ? -1 : 0)
                + pos.x;
        targetY = ((move.direction == Direction.UP) ? 1 : 0)
                + ((move.direction == Direction.DOWN) ? -1 : 0)
                + pos.y;

        // If the target field is not passable movement is not possible
        if (!pos.map.isPassable(targetX, targetY)) {
            if (entity instanceof Player) System.out.print("Player tried running into the wall.\n");
            return false;
        }

        GridPoint2 targetPos = new GridPoint2(targetX, targetY);

        // Searching for entities which could collide with current entity's move
        Entity collidingEntity = interactives.get(targetPos);

        if (collidingEntity != null) {
            if (collidingEntity instanceof PushableObject) {
                // If the colliding entity is a pushable world object we recursively check if it will
                // push other pushable world objects along it's way and whether the target tile is
                // obstructed or not
                boolean canBePushed = analyzeMovementAction(collidingEntity, intent);

                if (canBePushed) {
                    Mappers.currentAction.get(collidingEntity).action = new MovementAction(move.direction);
                    updateHashMapData(collidingEntity);
                    return true;
                } else {
                    return false;
                }
            }
            if (collidingEntity instanceof Player) {
                // Damage system will apply damage to the Player here
                // if the entity is an agressive mob
                return false;
            }
            if (collidingEntity instanceof Mob) {
                // Damage system will apply damage to the Mob here if the entity
                // is a player, or will not move if it is a mob or some other object
                if (entity instanceof Player) System.out.print("Player tried running into a mob.\n");
                return false;
            }
            if (collidingEntity instanceof Stairs) {
                if (entity instanceof Player) {
                    intent.currentIntent = new TeleportAction(collidingEntity);
                    interactives.remove(new GridPoint2(pos.x, pos.y));
                    this.shouldResetIntention = false;
                }

                return false;
            }
        }
        return true;
    }

    private void updateHashMapData(Entity entity) {
        PositionComponent pos = Mappers.position.get(entity);
        CurrentActionComponent action = Mappers.currentAction.get(entity);

        // If the position has changed we need to update the information
        // about the current entity's position mid-turn for upcoming entities
        // to see - we would be risking moving two entities to the same spot otherwise
        if (action.action instanceof MovementAction) {
            Direction dir = ((MovementAction) action.action).direction;
            int targetX, targetY;

            targetX = ((dir == Direction.RIGHT) ? 1 : 0)
                    + ((dir == Direction.LEFT) ? -1 : 0)
                    + pos.x;
            targetY = ((dir == Direction.UP) ? 1 : 0)
                    + ((dir == Direction.DOWN) ? -1 : 0)
                    + pos.y;

            GridPoint2 targetPos = new GridPoint2(targetX, targetY);

            interactives.remove(new GridPoint2(pos.x, pos.y), entity);
            interactives.put(targetPos, entity);
        }
    }

    public class InteractivesHandler implements EntityListener {
        @Override
        public void entityRemoved(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            interactives.remove(new GridPoint2(pos.x, pos.y));
        }

        @Override
        public void entityAdded(Entity entity) {
            PositionComponent pos = Mappers.position.get(entity);
            interactives.put(new GridPoint2(pos.x, pos.y), entity);
        }
    }
}
