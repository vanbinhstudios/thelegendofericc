package com.ericc.the.game.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Map;

/**
 * For reference:
 * http://www.roguebasin.com/index.php?title=Eligloscode
 */
public class FOV {
    private Player player;
    private static final int VIEW_RADIUS = 6;
    private boolean[][] visibility; ///< calculated every render, tiles that are visible atm
    private Map map;
    private FOG fog;

    public FOV(Player player, Map map, FOG fog) {
        this.player = player;
        this.map = map;
        this.fog = fog;

        visibility = new boolean[map.width()][map.height()];
    }

    private void clearFOV(int top, int bottom, int left, int right) {
        for (int y = top; y >= bottom; --y) {
            for (int x = left; x <= right; ++x) {
                visibility[x][y] = false;
            }
        }
    }

    public void updateFOV(int top, int bottom, int left, int right) {
        clearFOV(top, bottom, left, right);

        // sends a ray trace line every degree
        for (int i = 0; i < 360; i++) {
            float x = MathUtils.cos(i * .01745f); // in radians, that's why there is a .175.. const
            float y = MathUtils.sin(i * .01745f);

            updateOneLine(x, y);
        }
    }

    private void updateOneLine(float x, float y) {
        float posx = player.pos.x + .5f;
        float posy = player.pos.y + .5f;

        for (int i = 0; i < VIEW_RADIUS; ++i) {
            if (!(map.inBoundaries((int) posx, (int) posy))) {
                continue;
            }

            visibility[(int) posx][(int) posy] = true;
            fog.registerTile((int) posx, (int) posy);

            // if this tile is a border, the hero does not see through that tile
            if (!map.isPassable((int) posx, (int) posy)) {
                return;
            }

            posx += x;
            posy += y;
        }
    }

    /**
     * Returns whether an object at given position is in current fov.
     */
    public boolean inFOV(int x, int y) {
        return visibility[x][y];
    }
}
