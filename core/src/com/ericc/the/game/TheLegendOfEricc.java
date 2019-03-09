package com.ericc.the.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ericc.the.game.Map.Tile;
import com.ericc.the.game.Map.Room;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TheLegendOfEricc extends ApplicationAdapter {
	// Controls
	private KeyboardControls controls;
	private OrthographicCamera camera;

	// Sprite data
	private SpriteBatch batch;
	private Texture img;
	private Sprite imgSprite;

	// Example room data
	private Room room;

	// Example player character
	private Player player;

	@Override
	public void create() {
		// Test sprite
		batch = new SpriteBatch();
		img = new Texture("eric.png");
		imgSprite = new Sprite(img);
		imgSprite.setColor(1, 1, 1, 0.5f);

		camera = new OrthographicCamera(12, 9);

		// Input settings
		controls = new KeyboardControls();
		Gdx.input.setInputProcessor(controls);

		room = new Room();

		player = new Player(room.center);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.145f, .075f, .102f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		player.update(controls, room);

		// Camera interpolation
		camera.position.lerp(player.camPos, .1f);
		camera.zoom = 1f;
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Drawing the room
		batch.begin();
		for (ArrayList<Tile> row : room.chunk.tiles) {
			for (Tile tile : row) {
				batch.draw(tile.texture, tile.pos.x, tile.pos.y, 1, 1);
			}
		}

		// A small test of transparency and sprite layering
		player.draw(batch);
		imgSprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
