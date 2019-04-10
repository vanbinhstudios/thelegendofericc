package com.ericc.the.game;

import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.components.Model;

public class Models {
    public static Model hero = new Model(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft, new Affine2().translate(0, 0.35f));
    public static Model mage = new Model(Media.mobBack, Media.mobRight, Media.mobFront, Media.mobLeft, new Affine2().translate(0, 0.35f));
    public static Model crate = new Model(Media.crate, Media.crate, Media.crate, Media.crate, new Affine2().translate(0, 0.35f));
    public static Model stairsDown = new Model(Media.stairsDown, Media.stairsDown, Media.stairsDown, Media.stairsDown, new Affine2().translate(0, 0.1f));
    public static Model stairsUp = new Model(Media.stairsUp, Media.stairsUp, Media.stairsUp, Media.stairsUp, new Affine2().translate(0, 0.1f));
    public static Model sword = new Model(Media.swordUp, Media.swordRight, Media.swordDown, Media.swordLeft, new Affine2().translate(0, 0.35f));
    public static Model healthbar = new Model(Media.healthbar, Media.healthbar, Media.healthbar, Media.healthbar, new Affine2().translate(0, 0.9f));
}
