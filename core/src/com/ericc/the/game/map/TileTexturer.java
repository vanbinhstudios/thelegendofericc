package com.ericc.the.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.ericc.the.game.Enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TileTexturer {
    private Texture dunVoid;
    private ArrayList<Texture> floors;
    private ArrayList<Texture> up;
    private ArrayList<Texture> down;
    private Texture wallL1, wallL2, wallL3;
    private Texture wallR1, wallR2, wallR3;
    private Texture wallLU, wallRU, wallLD, wallRD;
    private Texture wallLUcorner, wallLDcorner, wallRUcorner, wallRDcorner,
            wallFancyCornerSmallLeft, wallTwoCorners, wallFancyCornerSmallRight;
    private Texture wallCorridorMiddle, wallCorridorUp;

    private HashMap<String, Enums.TILETEXTURE> textures;
    private Random random;
    private ArrayList<ArrayList<Texture>> texturesBundle;

    TileTexturer() {
        this.textures = new HashMap<String, Enums.TILETEXTURE>();
        this.random = new Random();
        this.floors = new ArrayList<Texture>();
        this.up = new ArrayList<Texture>();
        this.down = new ArrayList<Texture>();
        this.texturesBundle = new ArrayList<ArrayList<Texture>>();

        loadTextures();
        generateTextures();
    }

    private void loadTextures() {
        dunVoid = new Texture("void.png");
        texturesBundle.add(new ArrayList<Texture>(Arrays.asList(dunVoid)));

        floors.add(new Texture("mid_dun_flr1.png"));
        floors.add(new Texture("mid_dun_flr2.png"));
        floors.add(new Texture("mid_dun_flr3.png"));
        floors.add(new Texture("mid_dun_flr4.png"));
        floors.add(new Texture("mid_dun_flr5.png"));
        floors.add(new Texture("mid_dun_flr6.png"));
        texturesBundle.add(floors);

        down.add(new Texture("d_dun_wall1.png"));
        down.add(new Texture("d_dun_wall2.png"));
        texturesBundle.add(down);

        up.add(new Texture("u_dun_wall1.png"));
        up.add(new Texture("u_dun_wall2.png"));
        up.add(new Texture("u_dun_wall3.png"));
        up.add(new Texture("u_dun_wall4.png"));
        texturesBundle.add(up);

        wallL1 = new Texture("l_dun_wall1.png");
        wallR1 = new Texture("r_dun_wall1.png");
        wallLD = new Texture("ld_dun_wall.png");
        wallRD = new Texture("rd_dun_wall.png");
        wallLU = new Texture("lu_dun_wall.png");
        wallRU = new Texture("ru_dun_wall.png");
        wallLUcorner = new Texture("left_up_corner.png");
        wallLDcorner = new Texture("left_down_corner.png");
        wallRUcorner = new Texture("right_up_corner.png");
        wallRDcorner = new Texture("right_down_corner.png");
        wallCorridorMiddle = new Texture("middle.png");
        wallCorridorUp = new Texture("corridor_up.png");
        wallFancyCornerSmallLeft = new Texture("corner_fancy.png");
        wallTwoCorners = new Texture("two_corners.png");
        wallFancyCornerSmallRight = new Texture("corner_fancy_right.png");
    }

    public void dispose() {
        for (ArrayList<Texture> singleBundle : texturesBundle) {
            for (Texture texture : singleBundle) {
                texture.dispose();
            }
        }

        wallL1.dispose();
        wallR1.dispose();
        wallLU.dispose();
        wallRU.dispose();
        wallLD.dispose();
        wallRD.dispose();
        wallLUcorner.dispose();
        wallLDcorner.dispose();
        wallRUcorner.dispose();
        wallRDcorner.dispose();
        wallCorridorMiddle.dispose();
        wallCorridorUp.dispose();
        wallFancyCornerSmallRight.dispose();
        wallFancyCornerSmallLeft.dispose();
        wallTwoCorners.dispose();
    }

    Texture getTileTexture(String tileCode, boolean passable) {
        if (passable) {
            return floors.get(random.nextInt(floors.size()));
        }

        if ((tileCode.charAt(0) == '1' && tileCode.charAt(3) == '1' && tileCode.charAt(6) == '1')
                || (tileCode.charAt(3) == '1')) {
            return up.get(random.nextInt(up.size()));
        }

        if (!textures.containsKey(tileCode)) {
            return dunVoid;
        }

        switch (textures.get(tileCode)) {
            case WALL_LEFT:
                return wallL1;
            case WALL_RIGHT:
                return wallR1;
            case WALL_LEFT_DOWN_CORNER:
            case WALL_RIGHT_DOWN_CORNER:
            case WALL_UP:
                return up.get(random.nextInt(up.size()));
            case WALL_DOWN:
                return down.get(random.nextInt(down.size()));
            case WALL_UP_LEFT:
                return wallLU;
            case WALL_UP_RIGHT:
                return wallRU;
            case WALL_DOWN_LEFT:
                return wallLD;
            case WALL_DOWN_RIGHT:
                return wallRD;
            case WALL_LEFT_UP_CORNER:
                return wallLUcorner;
            case WALL_RIGHT_UP_CORNER:
                return wallRUcorner;
            case WALL_CORRIDOR_MIDDLE:
                return wallCorridorMiddle;
            case WALL_CORRIDOR_UP:
                return wallCorridorUp;
            case WALL_CORNER_FANCY_SMALL_LEFT:
                return wallFancyCornerSmallLeft;
            case WALL_CORNER_FANCY_SMALL_RIGHT:
                return wallFancyCornerSmallRight;
            case WALL_TWO_CORNERS:
                return wallTwoCorners;
            case FLOOR:
                return floors.get(random.nextInt(floors.size()));
            case VOID:
                return dunVoid;
            default:
                return dunVoid;
        }
    }

    private void generateTexture(String[] arr, Enums.TILETEXTURE tex) {
        for (String str : arr) {
            textures.put(str, tex);
        }
    }

    private void generateTextures() {
        String[] maskWallLeft = {"111000000", "110000000", "011000000", "010000000",
                "101000000", "110000001"};
        String[] maskWallRight = {"000000111", "000000110", "000000011", "000000010",
                "000000101", "001000110"};
        String[] maskWallUp = {"100100100", "100100000", "000100100",
                "101101101", "100101101", "101100101", "101101100",
                "101100100", "100101100", "100100101",
                "111100100", "111101100", "111100101", "111101101",
                "111100000", "0001000111"};
        String[] maskWallDown = {"001001001", "000001001", "001001000", "000001000"};
        String[] maskWallUpLeft = {"100000000"};
        String[] maskWallUpRight = {"000000100"};
        String[] maskWallDownLeft = {"001000000"};
        String[] maskWallDownRight = {"000000001"};
        String[] maskVoid = {"000000000"};
        String[] maskFloor = {"111111111"};
        String[] maskWallUpLeftCorner = {"001001111", "000001011", "001001011", "001001111",
                "000001010", "000001101", "000001111", "001001101", "001001100"};
        String[] maskWallUpRightCorner = {"111001001", "011001001", "111001000", "011001000",
                "010001000", "111001000", "101001000", "101001001", "100001001"};
        String[] maskWallDownLeftCorner = {"100100111"};
        String[] maskWallDownRightCorner = {"111100100"};
        String[] maskWallCorridorMiddle = {"111000111", "111000110", "110000111", "110000110",
                "111000100", "110000100", "100000110", "100000111", "011000011", "101000111",
                "101000110", "011000111", "100000100", "111000011", "011000111", "011000110",
                "110000011", "100000011", "011000001", "011000100", "111000101", "111000010",
                "010000011", "011000010", "010000111", "111000010", "101000100"};
        String[] maskWallCorridorUp = {"111001111", "011001111", "111001011", "011001011",
                "111001101", "011001101", "101001101", "100001111", "111001100", "101001111",
                "111001101", "101001011", "011001101", "101001100", "100001101"};
        String[] maskCornerFancyLeft = {"011000001", "111000001", "100000001", "010000001"};
        String[] maskCornerFancyRight = {"001000011", "001000111", "001000100", "001000010"};
        String[] maskTwoCorners = {"001000001"};

        HashMap<String[], Enums.TILETEXTURE> tempTextures = new HashMap<String[], Enums.TILETEXTURE>();

        tempTextures.put(maskWallLeft, Enums.TILETEXTURE.WALL_LEFT);
        tempTextures.put(maskWallRight, Enums.TILETEXTURE.WALL_RIGHT);
        tempTextures.put(maskWallUp, Enums.TILETEXTURE.WALL_UP);
        tempTextures.put(maskWallDown, Enums.TILETEXTURE.WALL_DOWN);
        tempTextures.put(maskWallUpLeft, Enums.TILETEXTURE.WALL_UP_LEFT);
        tempTextures.put(maskWallUpRight, Enums.TILETEXTURE.WALL_UP_RIGHT);
        tempTextures.put(maskWallDownLeft, Enums.TILETEXTURE.WALL_DOWN_LEFT);
        tempTextures.put(maskWallDownRight, Enums.TILETEXTURE.WALL_DOWN_RIGHT);
        tempTextures.put(maskVoid, Enums.TILETEXTURE.VOID);
        tempTextures.put(maskFloor, Enums.TILETEXTURE.FLOOR);
        tempTextures.put(maskWallUpLeftCorner, Enums.TILETEXTURE.WALL_LEFT_UP_CORNER);
        tempTextures.put(maskWallUpRightCorner, Enums.TILETEXTURE.WALL_RIGHT_UP_CORNER);
        tempTextures.put(maskWallDownLeftCorner, Enums.TILETEXTURE.WALL_LEFT_DOWN_CORNER);
        tempTextures.put(maskWallDownRightCorner, Enums.TILETEXTURE.WALL_RIGHT_DOWN_CORNER);
        tempTextures.put(maskWallCorridorMiddle, Enums.TILETEXTURE.WALL_CORRIDOR_MIDDLE);
        tempTextures.put(maskWallCorridorUp, Enums.TILETEXTURE.WALL_CORRIDOR_UP);
        tempTextures.put(maskCornerFancyLeft, Enums.TILETEXTURE.WALL_CORNER_FANCY_SMALL_LEFT);
        tempTextures.put(maskCornerFancyRight, Enums.TILETEXTURE.WALL_CORNER_FANCY_SMALL_RIGHT);
        tempTextures.put(maskTwoCorners, Enums.TILETEXTURE.WALL_TWO_CORNERS);

        for (HashMap.Entry<String[], Enums.TILETEXTURE> entry : tempTextures.entrySet()) {
            generateTexture(entry.getKey(), entry.getValue());
        }
    }
}