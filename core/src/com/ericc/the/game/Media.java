package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;

public class Media {
    public static Texture dunVoid;
    public static Texture floorMid1, floorMid2;
    public static Texture wallU1, wallU2;
    public static Texture wallD1, wallD2;
    public static Texture wallL1, wallL2, wallL3;
    public static Texture wallR1, wallR2, wallR3;
    public static Texture wallLU, wallRU, wallLD, wallRD;
    public static Texture playerFront, playerLeft, playerRight, playerBack;

    public static void loadAssets() {
        dunVoid = new Texture("void.png");

        floorMid1 = new Texture("mid_dun_flr1.png");
        floorMid2 = new Texture("mid_dun_flr2.png");

        wallD1 = new Texture("d_dun_wall1.png");
        wallU1 = new Texture("u_dun_wall1.png");
        wallL1 = new Texture("l_dun_wall1.png");
        wallR1 = new Texture("r_dun_wall1.png");

        wallLD = new Texture("ld_dun_wall.png");
        wallRD = new Texture("rd_dun_wall.png");
        wallLU = new Texture("lu_dun_wall.png");
        wallRU = new Texture("ru_dun_wall.png");

        playerFront = new Texture("testhero_front.png");
        playerBack = new Texture("testhero_back.png");
        playerLeft = new Texture("testhero_left.png");
        playerRight = new Texture("testhero_right.png");
    }

    public void dispose() {
        dunVoid.dispose();

        floorMid1.dispose();
        floorMid2.dispose();

        wallU1.dispose();
        wallD1.dispose();
        wallL1.dispose();
        wallR1.dispose();
        wallLU.dispose();
        wallRU.dispose();
        wallLD.dispose();
        wallRD.dispose();
    }
}
