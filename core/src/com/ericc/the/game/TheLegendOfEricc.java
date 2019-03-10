package com.ericc.the.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.map.Tile;

public class TheLegendOfEricc extends ApplicationAdapter {
	// Controls
	private KeyboardControls controls;
	private OrthographicCamera camera;

	// Sprite data
	private SpriteBatch batch;
	private Texture img;
	private Sprite imgSprite;

	// Window size
	private int displayW;
	private int displayH;

    private int width, height, tileSize, maximalRoomSize;
    private Map map;

    // Movement tracking
    private int dirX, dirY;
    private int spd = 100;

	@Override
	public void create() {
		batch = new SpriteBatch();

		displayH = Gdx.graphics.getHeight();
		displayW = Gdx.graphics.getWidth();
		int h = (int) (displayH / Math.floor(displayH / 160));
		int w = (int) (displayW / (displayH / (displayH / Math.floor(displayH / 160))));

		camera = new OrthographicCamera(w, h);
		camera.zoom = 1.2f;

		controls = new KeyboardControls(displayH, displayW, camera);
		Gdx.input.setInputProcessor(controls);

        setMapProperties();
        map = new Map(width, height, tileSize, maximalRoomSize);
        map.printMap();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.145f, .075f, .102f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		dirX = 0;
		dirY = 0;

        if (controls.up) dirY = 3;
        if (controls.down) dirY = -3;
        if (controls.right) dirX = 3;
        if (controls.left) dirX = -3;

		camera.position.x += dirX * spd * Gdx.graphics.getDeltaTime();
		camera.position.y += dirY * spd * Gdx.graphics.getDeltaTime();
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                Tile tile = map.getTile(x, y);
                tile.draw(batch);
            }
        }

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
        map.dispose();
    }

    private void setMapProperties() {
        this.width = 100;
        this.height = 60;
        this.tileSize = 16;
        this.maximalRoomSize = 8;
    }
}
