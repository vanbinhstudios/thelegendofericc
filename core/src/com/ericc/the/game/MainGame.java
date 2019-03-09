package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.ericc.the.game.Map.Tile;
import com.ericc.the.game.Map.Room;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MainGame extends Game {
	private KeyboardControls controls;
	private OrthographicCamera camera;

	private Room room;
	private Player player;

	private Engine engine = new Engine();

	@Override
	public void create() {
		camera = new OrthographicCamera(24, 18);

		controls = new KeyboardControls();
		Gdx.input.setInputProcessor(controls);

		room = new Room();
		player = new Player(room.center);

		engine.addEntity(player);
		engine.addSystem(new RenderSystem(room, camera));
	}

	@Override
	public void render() {
		// Camera interpolation
		camera.position.lerp(new Vector3(player.pos.x, player.pos.y, 0), .1f);
		camera.zoom = 1f;
		camera.update();

		player.update(controls, room);
		engine.update(Gdx.graphics.getDeltaTime());
	}
}
