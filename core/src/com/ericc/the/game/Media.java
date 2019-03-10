package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;

public class Media {
    public static Texture dunVoid;
    public static Texture[] floor = new Texture[2];
    public static Texture wallU, wallD, wallL, wallR;
    public static Texture wallLU, wallRU, wallLD, wallRD;
    public static Texture playerFront, playerLeft, playerRight, playerBack;

    public static void loadAssets() {
        dunVoid = new Texture("void.png");

        floor[0] = new Texture("mid_dun_flr1.png");
        floor[1] = new Texture("mid_dun_flr2.png");

        wallD = new Texture("d_dun_wall1.png");
        wallU = new Texture("u_dun_wall1.png");
        wallL = new Texture("l_dun_wall1.png");
        wallR = new Texture("r_dun_wall1.png");

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

        floor[0].dispose();
        floor[1].dispose();

        wallU.dispose();
        wallD.dispose();
        wallL.dispose();
        wallR.dispose();
        wallLU.dispose();
        wallRU.dispose();
        wallLD.dispose();
        wallRD.dispose();
    }
}
