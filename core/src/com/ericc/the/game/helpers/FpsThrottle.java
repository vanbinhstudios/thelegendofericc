package com.ericc.the.game.helpers;

import com.badlogic.gdx.utils.TimeUtils;

public class FpsThrottle {
    private long frameTimeMs;
    private long nextFrameTimestamp;

    public FpsThrottle(int fps) {
        frameTimeMs = 1000/fps + 1;
        nextFrameTimestamp = TimeUtils.millis() + frameTimeMs;
    }

    public void sleepToNextFrame() {
        long currentTimeMs = TimeUtils.millis();

        if (currentTimeMs < nextFrameTimestamp) {
            try {
                System.out.println(nextFrameTimestamp - currentTimeMs);
                Thread.sleep(nextFrameTimestamp - currentTimeMs);
            } catch (InterruptedException e) {
                System.err.println("Unexpected interruption of fps throttling sleep");
            }
            nextFrameTimestamp += frameTimeMs;
        } else {
            nextFrameTimestamp = currentTimeMs + frameTimeMs;
        }
    }
}
