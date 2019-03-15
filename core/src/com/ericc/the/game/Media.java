package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Media {
    public static TextureRegion dunVoid;
    public static ArrayList<TextureRegion> floors, wallUp, wallDown, wallLeft, wallRight;
    public static TextureRegion wallLU, wallRU, wallLD, wallRD;
    public static TextureRegion playerFront, playerLeft, playerRight, playerBack;
    public static TextureAtlas atlas;

    static {
        floors = new ArrayList<>();
        wallUp = new ArrayList<>();
        wallDown = new ArrayList<>();
        wallLeft = new ArrayList<>();
        wallRight = new ArrayList<>();
    }

    public static void loadAssets() {
        atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));

        dunVoid = atlas.findRegion("void");

        floors.add(atlas.findRegion("mid_dun_flr1"));
        floors.add(atlas.findRegion("mid_dun_flr2"));
        floors.add(atlas.findRegion("mid_dun_flr3"));
        floors.add(atlas.findRegion("mid_dun_flr4"));
        floors.add(atlas.findRegion("mid_dun_flr5"));
        floors.add(atlas.findRegion("mid_dun_flr6"));

        wallUp.add(atlas.findRegion("u_dun_wall1"));
        wallUp.add(atlas.findRegion("u_dun_wall2"));
        wallUp.add(atlas.findRegion("u_dun_wall3"));
        wallUp.add(atlas.findRegion("u_dun_wall4"));

        wallDown.add(atlas.findRegion("d_dun_wall1"));
        wallDown.add(atlas.findRegion("d_dun_wall2"));

        wallLeft.add(atlas.findRegion("l_dun_wall1"));
        wallLeft.add(atlas.findRegion("l_dun_wall2"));
        wallLeft.add(atlas.findRegion("l_dun_wall3"));

        wallRight.add(atlas.findRegion("r_dun_wall1"));
        wallRight.add(atlas.findRegion("r_dun_wall2"));
        wallRight.add(atlas.findRegion("r_dun_wall3"));

        wallLD = atlas.findRegion("ld_dun_wall");
        wallRD = atlas.findRegion("rd_dun_wall");
        wallLU = atlas.findRegion("lu_dun_wall");
        wallRU = atlas.findRegion("ru_dun_wall");

        playerFront = atlas.findRegion("testhero_front");
        playerBack = atlas.findRegion("testhero_back");
        playerLeft = atlas.findRegion("testhero_left");
        playerRight = atlas.findRegion("testhero_right");
    }

    public static void dispose() {
        atlas.dispose();
    }
}
