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
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Map.Map;

public class RenderSystem extends EntitySystem  {

	private Map map;
	private Camera camera;

	public RenderSystem(Map map, Camera camera) {
		this.map = map;
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

		for (int j = map.height() - 1; j >= 0; --j) {
			for (int i = 0; i < map.width(); ++i) {
				drawTile(i, j);
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

	private void drawTile(int x, int y) {
		int code = encodeTile(x, y);

		if ((code & 0b000010000) != 0) {
			batch.draw(Media.floor[MathUtils.random(0, 1)/2], x, y, 1, 1);
			return;
		}

		if ((code & 0b000000010) != 0) {
			batch.draw(Media.wallD, x, y + 0.5f, 1, 1);
		} else {
			if ((code & 0b000100010) == 0 && (code & 0b000000100) != 0) {
				batch.draw(Media.wallRD, x, y + 0.5f, 0.5f, 1, 0, 1, 0.5f, 0);
			}
			if ((code & 0b000001010) == 0 && (code & 0b000000001) != 0) {
				batch.draw(Media.wallLD, x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 1, 1, 0);
			}
		}

		if ((code & 0b010000000) != 0) {
			batch.draw(Media.wallU, x, y, 1, 1);
		} else {
			if ((code & 0b010100000) == 0 && (code & 0b100000000) != 0) {
				batch.draw(Media.wallRU, x, y, 0.5f, 1, 0, 1, 0.5f, 0);
			}
			if ((code & 0b010001000) == 0 && (code & 0b001000000) != 0) {
				batch.draw(Media.wallLU, x + 0.5f, y, 0.5f, 1, 0.5f, 1, 1, 0);
			}
		}

		if ((code & 0b010100010) == 0b000100000 || (code & 0b010101010) == 0b010100010) {
			batch.draw(Media.wallR, x, y + 0.5f, 0.5f, 1, 0, 0, 0.5f, 1);
		} else if ((code & 0b010100010) == 0b000100010) {
			batch.draw(Media.wallR, x, y + 0.5f, 0.5f, 0.5f, 0, 0.5f, 0.5f, 1);
		} else if ((code & 0b010100010) == 0b010100000) {
			batch.draw(Media.wallR, x, y + 0.5f + 0.5f, 0.5f, 0.5f, 0, 0, 0.5f, 0.5f);
		}


		if ((code & 0b010001010) == 0b000001000 || (code & 0b010101010) == 0b010001010) {
			batch.draw(Media.wallL, x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 0, 1, 1);
		} else if ((code & 0b010001010) == 0b000001010) {
			batch.draw(Media.wallL, x + 0.5f, y + 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 1, 1);
		} else if ((code & 0b010001010) == 0b010001000) {
			batch.draw(Media.wallL, x + 0.5f, y + 0.5f + 0.5f, 0.5f, 0.5f, 0.5f, 0, 1, 0.5f);
		}
	}

	public int encodeTile(int x, int y) {
		int ans = 0;
		int cnt = 8;
		for (int j = y - 1; j <= y + 1; ++j) {
			for (int i = x - 1; i <= x + 1; ++i) {
				ans |= (map.isPassable(i, j)?1:0) << cnt;
				--cnt;
			}
		}
		return ans;
	}
}
