package com.ericc.the.game.animations;

public class Animations {
    public static final JumpAnimation WALK = new JumpAnimation(0.4f, 0.15f);
    public static final JumpAnimation RUN = new JumpAnimation(0.2f, 0.10f);
    public static final JumpAnimation PUSH = new JumpAnimation(0.1f, 0.20f);
    public static final JumpAnimation SLIDE = new JumpAnimation(0.0f, 0.03f);
    public static final IdleAnimation IDLE = new IdleAnimation();
    public static final DeathAnimation DISAPPEAR = new DeathAnimation(1 / 2f, false, 1.0f);
    public static final DeathAnimation WITHER = new DeathAnimation(1 / 2f, true, 0.7f);
}
