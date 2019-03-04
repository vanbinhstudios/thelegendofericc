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
	KeyboardControls controls;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Sprite img_sprite;

	// Window size
	private int displayW;
	private int displayH;

	// Current coords
	int x, y;

	Room room;

	// Movement tracking
	int dir_x, dir_y;
	int spd = 100;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("eric.png");
		img_sprite = new Sprite(img);
		img_sprite.setColor(1, 1, 1, 0.5f);

		// Camera settings
		displayH = Gdx.graphics.getHeight();
		displayW = Gdx.graphics.getWidth();

		int h = (int) (displayH/Math.floor(displayH/160));
		int w = (int) (displayW/(displayH/(displayH/Math.floor(displayH/160))));

		camera = new OrthographicCamera(w, h);
		camera.zoom = 1.2f;

		// Input settings
		controls = new KeyboardControls(displayH, displayW, camera);
		Gdx.input.setInputProcessor(controls);

		room = new Room();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.145f, .075f, .102f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		dir_x = 0;
		dir_y = 0;

		if(controls.up) dir_y = 1;
		if(controls.down) dir_y = -1;
		if(controls.right) dir_x = 1;
		if(controls.left) dir_x = -1;

		camera.position.x += dir_x*spd*Gdx.graphics.getDeltaTime();
		camera.position.y += dir_y*spd*Gdx.graphics.getDeltaTime();
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Drawing the room
		batch.begin();
		for (ArrayList<Tile> row : room.chunk.tiles) {
			for (Tile tile : row) {
				batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
			}
		}
		// A small test of transparency and sprite layering
		img_sprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
