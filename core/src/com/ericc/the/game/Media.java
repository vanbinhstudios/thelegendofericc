package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class Media {
    public static TextureRegion dunVoid;
    public static Array<TextureAtlas.AtlasRegion> wallUp, wallDown, wallLeft, wallRight, clutter, wallClutter;
    public static Array<TextureAtlas.AtlasRegion> floors, floorsRev;
    public static TextureRegion wallLU, wallRU, wallLD, wallRD;
    public static TextureRegion playerFront, playerLeft, playerRight, playerBack;
    public static TextureRegion mageFront, mageLeft, mageRight, mageBack;
    public static TextureRegion tankFront, tankLeft, tankRight, tankBack;
    public static TextureRegion slimeSmallFront, slimeSmallLeft, slimeSmallRight, slimeSmallBack;
    public static TextureRegion slimeBigFront, slimeBigLeft, slimeBigRight, slimeBigBack;
    public static TextureRegion stairsDown, stairsUp;
    public static TextureRegion crate;
    public static TextureAtlas atlas;
    public static TextureRegion swordLeft, swordRight, swordUp, swordDown;
    public static TextureRegion healthBar;
    public static TextureRegion experienceBar;
    public static TextureRegion lvlUp;
    public static TextureRegion arrowUp, arrowRight, arrowLeft, arrowDown;
    public static TextureRegion explosion1, explosion2, explosion3;
    public static TextureRegion scrollOfStorms, scrollOfInvulnerability, food, storm;
    public static TextureRegion fireBeam, fireExplosion, arrowShot;
    public static TextureRegion white;
    public static int floorsConfiguration = 0;

    public static void loadAssets() {
        atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));

        dunVoid = atlas.findRegion("map/void");

        floors = atlas.findRegions("map/floors/floor");
        floorsRev = atlas.findRegions("map/floors/floor_reversed"); // for now lets stick with only one reversed texture

        wallUp = atlas.findRegions("map/walls/wall_up");
        wallDown = atlas.findRegions("map/walls/wall_down");
        wallLeft = atlas.findRegions("map/walls/wall_left");
        wallRight = atlas.findRegions("map/walls/wall_right");

        wallLD = atlas.findRegion("map/walls/wall_corner_left_down");
        wallRD = atlas.findRegion("map/walls/wall_corner_right_down");
        wallLU = atlas.findRegion("map/walls/wall_corner_left_up");
        wallRU = atlas.findRegion("map/walls/wall_corner_right_up");

        clutter = atlas.findRegions("map/clutter/clutter");

        wallClutter = atlas.findRegions("map/clutter/wallClutter");

        playerFront = atlas.findRegion("entity/hero/hero_front");
        playerBack = atlas.findRegion("entity/hero/hero_back");
        playerLeft = atlas.findRegion("entity/hero/hero_left");
        playerRight = atlas.findRegion("entity/hero/hero_right");

        mageFront = atlas.findRegion("entity/mage/mage_front");
        mageBack = atlas.findRegion("entity/mage/mage_back");
        mageLeft = atlas.findRegion("entity/mage/mage_left");
        mageRight = atlas.findRegion("entity/mage/mage_right");

        tankFront = atlas.findRegion("entity/knight/knight_front");
        tankBack = atlas.findRegion("entity/knight/knight_back");
        tankLeft = atlas.findRegion("entity/knight/knight_left");
        tankRight = atlas.findRegion("entity/knight/knight_right");

        slimeSmallFront = atlas.findRegion("entity/small_slime/small_slime_front");
        slimeSmallBack = atlas.findRegion("entity/small_slime/small_slime_back");
        slimeSmallLeft = atlas.findRegion("entity/small_slime/small_slime_left");
        slimeSmallRight = atlas.findRegion("entity/small_slime/small_slime_right");

        slimeBigFront = atlas.findRegion("entity/big_slime/big_slime_front");
        slimeBigBack = atlas.findRegion("entity/big_slime/big_slime_back");
        slimeBigLeft = atlas.findRegion("entity/big_slime/big_slime_left");
        slimeBigRight = atlas.findRegion("entity/big_slime/big_slime_right");

        crate = atlas.findRegion("entity/crate/crate");
        stairsUp = atlas.findRegion("map/stairs/stairs_up");
        stairsDown = atlas.findRegion("map/stairs/stairs_down");

        swordRight = atlas.findRegion("entity/sword");
        swordLeft = new TextureRegion(swordRight);
        swordLeft.flip(true, false);
        swordDown = new TextureRegion(swordRight);
        swordDown.flip(true, true);
        swordUp = swordRight;

        arrowRight = atlas.findRegion("entity/weapons/arrow");
        arrowLeft = new TextureRegion(arrowRight);
        arrowLeft.flip(true, false);
        arrowUp = atlas.findRegion("entity/weapons/arrowup");
        arrowDown = new TextureRegion(arrowUp);
        arrowDown.flip(false, true);

        explosion1 = atlas.findRegion("entity/weapons/explosion1");
        explosion2 = atlas.findRegion("entity/weapons/explosion2");
        explosion3 = atlas.findRegion("entity/weapons/explosion3");

        healthBar = atlas.findRegion("entity/healthBar");
        experienceBar = atlas.findRegion("entity/experienceBar");
        lvlUp = atlas.findRegion("entity/lvlUp");

        scrollOfStorms = atlas.findRegion("entity/items/scrollofstorm");
        scrollOfInvulnerability = atlas.findRegion("entity/items/scrollofinvulnerability");
        food = atlas.findRegion("entity/items/food");
        storm = atlas.findRegion("entity/items/storm");
        fireBeam = atlas.findRegion("entity/skills/firebeam");
        fireExplosion = atlas.findRegion("entity/skills/fireexplosion");
        arrowShot = atlas.findRegion("entity/skills/arrowshot");

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        white = new TextureRegion(new Texture(pixmap));
    }

    public static void dispose() {
        atlas.dispose();
    }

    /**
     * Return a random floor tile.
     *
     * @param x        coords of a tile
     * @param y        coords of a tile
     * @param index    which floor tile to return
     * @param isStatic indicates whether the tile should dynamically change the texture
     * @return
     */
    public static TextureRegion getRandomFloorTile(int x, int y, int index, boolean isStatic) {
        if (floorsConfiguration == 0 || isStatic) {
            return ((x + y) % 2 == 0) ? floors.get(index) : floorsRev.get(0);
        } else {
            return ((x + y) % 2 == 0) ? floorsRev.get(0) : floors.get(index);
        }
    }
}
