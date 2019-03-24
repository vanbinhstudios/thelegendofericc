package com.ericc.the.game;

import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.components.Model;

public class Models {
    public static Model hero = new Model(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft, new Affine2().translate(0, 0.35f));
    public static Model mage = new Model(Media.mobBack, Media.mobRight, Media.mobFront, Media.mobLeft, new Affine2().translate(0, 0.35f));
    public static Model crate = new Model(Media.crate, Media.crate, Media.crate, Media.crate, new Affine2().translate(0, 0.35f));
}
