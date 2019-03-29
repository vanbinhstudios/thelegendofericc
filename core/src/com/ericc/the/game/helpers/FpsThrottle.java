package com.ericc.the.game.helpers;

import com.badlogic.gdx.utils.TimeUtils;

// All units used here are milliseconds.

/**
 * An FPS limiter.
 */
public class FpsThrottle {
    private final long frameDuration; // The desired time to be spent on every frame (total of working and sleeping)
    private long currentFrameStart; // The moment in which the current frame has begun

    public FpsThrottle(int maxFps) {
        frameDuration = 1000 / maxFps + 1;
        currentFrameStart = TimeUtils.millis();
    }

    /**
     * Called one per frame, adjusts the maximum FPS to the chosen value.
     */
    public void sleepToNextFrame() {
        long currentTimeMs = TimeUtils.millis();

        if (currentTimeMs - currentFrameStart < frameDuration) {
            // If rendering took less than the target frame duration,
            // sleep to extend the frame to that duration.
            try {
                Thread.sleep(frameDuration - (currentTimeMs - currentFrameStart));
            } catch (InterruptedException e) {
                System.err.println("Unexpected interruption of fps throttling sleep");
            }
            // We don't take TimeUtils.millis() as the new frame start here,
            // because Thread.sleep() could have slept longer than asked,
            // and the next frame would have already begun in that case.
            currentFrameStart = currentFrameStart + frameDuration;
        } else {
            // Rendering time exceeded the target duration. We begin the next frame immediately.
            currentFrameStart = currentTimeMs;
        }
    }
}
