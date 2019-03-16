package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Media {
    public static TextureRegion dunVoid;
    public static ArrayList<TextureRegion> floors, wallUp, wallDown, wallLeft, wallRight, clutter, wallClutter, floorsRev;
    public static TextureRegion wallLU, wallRU, wallLD, wallRD;
    public static TextureRegion playerFront, playerLeft, playerRight, playerBack;
    public static TextureRegion mobFront, mobLeft, mobRight, mobBack;
    public static TextureAtlas atlas;
    public static int floorsConfiguration;

    static {
        floors = new ArrayList<>();
        floorsRev = new ArrayList<>();
        wallUp = new ArrayList<>();
        wallDown = new ArrayList<>();
        wallLeft = new ArrayList<>();
        wallRight = new ArrayList<>();
        clutter = new ArrayList<>();
        wallClutter = new ArrayList<>();
        floorsConfiguration = 0;
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
        floorsRev.add(atlas.findRegion("mid_dun_flr1_rev")); // for now lets stick with only one reversed texture

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

        clutter.add(atlas.findRegion("clutter1"));
        clutter.add(atlas.findRegion("clutter2"));
        clutter.add(atlas.findRegion("clutter3"));
        clutter.add(atlas.findRegion("clutter4"));
        clutter.add(atlas.findRegion("clutter5"));
        clutter.add(atlas.findRegion("clutter6"));
        clutter.add(atlas.findRegion("clutter7"));
        clutter.add(atlas.findRegion("clutter8"));
        clutter.add(atlas.findRegion("clutter9"));
        clutter.add(atlas.findRegion("clutter10"));
        clutter.add(atlas.findRegion("clutter11"));
        clutter.add(atlas.findRegion("clutter12"));
        clutter.add(atlas.findRegion("clutter13"));
        clutter.add(atlas.findRegion("clutter14"));
        clutter.add(atlas.findRegion("clutter15"));
        clutter.add(atlas.findRegion("clutter16"));
        clutter.add(atlas.findRegion("clutter17"));

        wallClutter.add(atlas.findRegion("wallClutter1"));
        wallClutter.add(atlas.findRegion("wallClutter2"));
        wallClutter.add(atlas.findRegion("wallClutter3"));
        wallClutter.add(atlas.findRegion("wallClutter4"));
        wallClutter.add(atlas.findRegion("wallClutter5"));
        wallClutter.add(atlas.findRegion("wallClutter6"));
        wallClutter.add(atlas.findRegion("wallClutter7"));
        wallClutter.add(atlas.findRegion("wallClutter8"));

        wallLD = atlas.findRegion("ld_dun_wall");
        wallRD = atlas.findRegion("rd_dun_wall");
        wallLU = atlas.findRegion("lu_dun_wall");
        wallRU = atlas.findRegion("ru_dun_wall");

        playerFront = atlas.findRegion("testhero_front");
        playerBack = atlas.findRegion("testhero_back");
        playerLeft = atlas.findRegion("testhero_left");
        playerRight = atlas.findRegion("testhero_right");

        mobFront = atlas.findRegion("mage_front");
        mobBack = atlas.findRegion("mage_back");
        mobLeft = atlas.findRegion("mage_left");
        mobRight = atlas.findRegion("mage_right");
    }

    public static void dispose() {
        atlas.dispose();
    }

    public static TextureRegion getRandomFloorTile(int x, int y, int index) {
        if (floorsConfiguration == 0) {
            return ((x + y) % 2 == 0) ? floors.get(index) : floorsRev.get(0);
        } else {
            return ((x + y) % 2 == 0) ? floorsRev.get(0) : floors.get(index);
        }
    }
}