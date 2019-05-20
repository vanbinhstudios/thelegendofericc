package com.ericc.the.game;

import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.components.Model;

public class Models {
    public static Model hero = new Model(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft,
            new Affine2().translate(0, 0.35f), false);
    public static Model mage = new Model(Media.mobBack, Media.mobRight, Media.mobFront, Media.mobLeft,
            new Affine2().translate(0, 0.35f), false);
    public static Model crate = new Model(Media.crate,
            new Affine2().translate(0, 0.35f), false);
    public static Model stairsDown = new Model(Media.stairsDown,
            new Affine2().translate(0, 0f), false);
    public static Model stairsUp = new Model(Media.stairsUp,
            new Affine2().translate(0, 0f), false);
    public static Model sword = new Model(Media.swordUp, Media.swordRight, Media.swordDown, Media.swordLeft,
            new Affine2().translate(0, 0.35f), true);
    public static Model healthBar = new Model(Media.healthBar,
            new Affine2().translate(0, 1.1f), false);
    public static Model experienceBar = new Model(Media.experienceBar,
            new Affine2().translate(0, 0.9f), false);
    public static Model arrow = new Model(Media.arrowUp, Media.arrowRight, Media.arrowDown, Media.arrowLeft,
            new Affine2().translate(0, 0.35f), true);
    public static Model explosion1 = new Model(Media.explosion1,
            new Affine2().translate(0, 0.35f), true);
    public static Model explosion2 = new Model(Media.explosion2,
            new Affine2().translate(0, 0.35f), true);
    public static Model explosion3 = new Model(Media.explosion3,
            new Affine2().translate(0, 0.35f), true);
    public static Model scrollOfStorms = new Model(Media.scrollOfStorms,
            new Affine2().translate(0, 0.35f), false);
    public static Model scrollOfInvulnerability = new Model(Media.scrollOfInvulnerability,
            new Affine2().translate(0, 0.35f), false);
    public static Model food = new Model(Media.food,
            new Affine2().translate(0, 0.35f), false);
    public static Model storm = new Model(Media.storm,
            new Affine2().translate(0, 0.35f), false);

    static {
        healthBar.width = 0.9f;
    }

    static {
        experienceBar.width = 0.9f;
    }
}
