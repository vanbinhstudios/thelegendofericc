package com.ericc.the.game.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ericc.the.game.MainGame;

/**
 * A Class to manipulate the camera's zoom.
 */
public class CameraZoom {

    private OrthographicCamera camera;
    private float initialCameraZoom;
    private final float cameraZoomMaxDeviation = .3f;
    private final float cameraChange = .05f; // a single step by which we can affect the camera

    public CameraZoom(OrthographicCamera camera) {
        this.camera = camera;
        this.initialCameraZoom = this.camera.zoom;
    }

    /**
     * Zooms the camera by the given value (either out or in)
     * @param zoom the value to add to a camera zoom
     */
    private void zoomCamera(float zoom) {
        if (canZoom(camera.zoom + zoom)) {
            camera.zoom += zoom;
        }
    }

    public void zoomOutCamera() {
        zoomCamera(this.cameraChange);
    }

    public void zoomInCamera() {
        zoomCamera((-1) * this.cameraChange);
    }

    /**
     * Zooms the camera in any direction, multiplying the given parameter with a single step.
     */
    public void zoomAnyCamera(float multiplier) {
        zoomCamera(multiplier * this.cameraChange);
    }

    /**
     * Checks whether we can zoom in or zoom out
     * @param zoom camera zoom altogether (sum of the previous zoom and the adder)
     * @return true if zoom is possible, false otherwise
     */
    private boolean canZoom(float zoom) {
        if (MainGame.DEBUG) {
            return true;
        }

        return zoom >= (initialCameraZoom - cameraZoomMaxDeviation)
                && zoom <= (initialCameraZoom + cameraZoomMaxDeviation);
    }
}
