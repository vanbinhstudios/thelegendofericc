package com.ericc.the.game;

import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.components.Model;

public class Models {
    public static Model hero = new Model(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft, new Affine2().translate(0, 0.35f));
    public static Model mage = new Model(Media.mobBack, Media.mobRight, Media.mobFront, Media.mobLeft, new Affine2().translate(0, 0.35f));
    public static Model crate = new Model(Media.crate, Media.crate, Media.crate, Media.crate, new Affine2().translate(0, 0.35f));
    public static Model stairsDown = new Model(Media.stairsDown, Media.stairsDown, Media.stairsDown, Media.stairsDown, new Affine2().translate(0, 0f));
    public static Model stairsUp = new Model(Media.stairsUp, Media.stairsUp, Media.stairsUp, Media.stairsUp, new Affine2().translate(0, 0f));
    public static Model sword = new Model(Media.swordUp, Media.swordRight, Media.swordDown, Media.swordLeft, new Affine2().translate(0, 0.35f));
    public static Model healthbar = new Model(Media.healthbar, Media.healthbar, Media.healthbar, Media.healthbar, new Affine2().translate(0, 1f));
    public static Model arrow = new Model(Media.arrowUp, Media.arrowRight, Media.arrowDown, Media.arrowLeft, new Affine2().translate(0, 0.35f));
    public static Model explosion1 = new Model(Media.explosion1, Media.explosion1, Media.explosion1, Media.explosion1, new Affine2().translate(0, 0.35f));
    public static Model explosion2 = new Model(Media.explosion2, Media.explosion2, Media.explosion2, Media.explosion2, new Affine2().translate(0, 0.35f));
    public static Model explosion3 = new Model(Media.explosion3, Media.explosion3, Media.explosion3, Media.explosion3, new Affine2().translate(0, 0.35f));
    public static Model none = new Model(Media.dunVoid, Media.dunVoid, Media.dunVoid, Media.dunVoid, new Affine2());
}
