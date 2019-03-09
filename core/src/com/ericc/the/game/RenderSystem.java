package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ericc.the.game.Map.Room;
import com.ericc.the.game.Map.Tile;

import java.util.ArrayList;

public class RenderSystem extends EntitySystem  {

	private Room room;
	private Camera camera;

	public RenderSystem(Room room, Camera camera) {
		this.room = room;
		this.camera = camera;
	}

	SpriteBatch batch = new SpriteBatch();
	ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RenderableComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(.145f, .075f, .102f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		batch.begin();

		for (int i = room.chunk.tiles.size() - 1; i >= 0; --i) {
			for (Tile tile : room.chunk.tiles.get(i)) {
				tile.renderable.sprite.draw(batch);
			}
		}

		for (Entity entity : entities) {
			PositionComponent pos = entity.getComponent(PositionComponent.class);
			RenderableComponent render = entity.getComponent(RenderableComponent.class);
			render.sprite.setOriginBasedPosition(pos.x, pos.y);
			render.sprite.draw(batch);
		}

		batch.end();
	}
}
