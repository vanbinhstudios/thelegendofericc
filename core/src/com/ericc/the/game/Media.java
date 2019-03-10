package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

public class Media {

    public static Texture dunVoid;
    public static ArrayList<Texture> floors, wallUp, wallDown, wallLeft, wallRight;
    public static Texture wallLU, wallRU, wallLD, wallRD;
    public static Texture playerFront, playerLeft, playerRight, playerBack;
    public static ArrayList<ArrayList<Texture>> textures; ///< Stores every texture asset to safely dispose them later

    static {
        floors = new ArrayList<>();
        wallUp = new ArrayList<>();
        wallDown = new ArrayList<>();
        wallLeft = new ArrayList<>();
        wallRight = new ArrayList<>();
        textures = new ArrayList<>();
    }

    public static void loadAssets() {
        dunVoid = new Texture("void.png");
        textures.add(new ArrayList<>(Arrays.asList(dunVoid))); // you can add assets to textures as single asset too

        floors.add(new Texture("mid_dun_flr1.png"));
        floors.add(new Texture("mid_dun_flr2.png"));
        floors.add(new Texture("mid_dun_flr3.png"));
        floors.add(new Texture("mid_dun_flr4.png"));
        floors.add(new Texture("mid_dun_flr5.png"));
        floors.add(new Texture("mid_dun_flr6.png"));
        textures.add(floors);

        wallUp.add(new Texture("u_dun_wall1.png"));
        wallUp.add(new Texture("u_dun_wall2.png"));
        wallUp.add(new Texture("u_dun_wall3.png"));
        wallUp.add(new Texture("u_dun_wall4.png"));
        textures.add(wallUp);

        wallDown.add(new Texture("d_dun_wall1.png"));
        wallDown.add(new Texture("d_dun_wall2.png"));
        textures.add(wallDown);

        wallLeft.add(new Texture("l_dun_wall1.png"));
        wallLeft.add(new Texture("l_dun_wall2.png"));
        wallLeft.add(new Texture("l_dun_wall3.png"));
        textures.add(wallLeft);

        wallRight.add(new Texture("r_dun_wall1.png"));
        wallRight.add(new Texture("r_dun_wall2.png"));
        wallRight.add(new Texture("r_dun_wall3.png"));
        textures.add(wallRight);

        wallLD = new Texture("ld_dun_wall.png");
        wallRD = new Texture("rd_dun_wall.png");
        wallLU = new Texture("lu_dun_wall.png");
        wallRU = new Texture("ru_dun_wall.png");
        textures.add(new ArrayList<>(Arrays.asList(wallLD, wallRD, wallLU, wallRU)));

        playerFront = new Texture("testhero_front.png");
        playerBack = new Texture("testhero_back.png");
        playerLeft = new Texture("testhero_left.png");
        playerRight = new Texture("testhero_right.png");
        textures.add(new ArrayList<>(Arrays.asList(playerFront, playerBack, playerLeft, playerRight)));
    }

    public static void dispose() {
        for (ArrayList<Texture> bundle: textures) {
            for (Texture texture: bundle) {
                texture.dispose();
            }
        }
    }
}
