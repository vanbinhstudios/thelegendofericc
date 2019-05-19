package com.ericc.the.game.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.animations.Animation;
import com.ericc.the.game.animations.Animations;

import java.util.HashMap;
import java.util.Map;

public class Model {
    public static Map<AnimationState, Animation> attackAnimationSheet = new HashMap<>();
    public static Map<AnimationState, Animation> entityAnimationSheet = new HashMap<>();

    static {
        attackAnimationSheet.put(AnimationState.IDLE, Animations.IDLE);
        attackAnimationSheet.put(AnimationState.WALKING, Animations.WALK);
        attackAnimationSheet.put(AnimationState.RUNNING, Animations.RUN);
        attackAnimationSheet.put(AnimationState.SLIDING, Animations.SLIDE);
        attackAnimationSheet.put(AnimationState.PUSHING, Animations.PUSH);
        attackAnimationSheet.put(AnimationState.DYING, Animations.DISAPPEAR);
        attackAnimationSheet.put(AnimationState.HOVERING, Animations.HOVER);

        entityAnimationSheet.putAll(attackAnimationSheet);
        entityAnimationSheet.put(AnimationState.DYING, Animations.WITHER);
    }

    public TextureRegion[] sheet;
    public Affine2 defaultTransform;
    public Map<AnimationState, Animation> animationSheet;
    public float width = 1.0f;
    public float height = 1.0f;

    public Model(TextureRegion up, TextureRegion right, TextureRegion down, TextureRegion left,
                 Affine2 defaultTransform, boolean attack) {
        this.defaultTransform = defaultTransform;
        this.sheet = new TextureRegion[4];
        this.sheet[Direction.UP.getValue()] = up;
        this.sheet[Direction.DOWN.getValue()] = down;
        this.sheet[Direction.LEFT.getValue()] = left;
        this.sheet[Direction.RIGHT.getValue()] = right;
        this.animationSheet = attack ? attackAnimationSheet : entityAnimationSheet;
    }

    public Model(TextureRegion undirected, Affine2 defaultTransform, boolean attack) {
        this(undirected, undirected, undirected, undirected, defaultTransform, attack);
    }
}
