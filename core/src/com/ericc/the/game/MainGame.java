package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Generator;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.systems.AnimationSystem;
import com.ericc.the.game.systems.RenderSystem;

public class MainGame extends Game {

    private KeyboardControls controls;
    private Viewport viewport;
    private final static int viewportWidth = 24;
    private final static int viewportHeight = 18;

    private Map map;
    private Player player;

    private Engine engine = new Engine();

    @Override
    public void create() {
        Media.loadAssets();
        viewport = new FillViewport(viewportWidth, viewportHeight);
        viewport.apply();

        controls = new KeyboardControls();
        Gdx.input.setInputProcessor(controls);

        map = new Generator(200, 50, 12).generateMap();
        player = new Player(map.getRandomPassableTile());

        engine.addEntity(player);
        engine.addSystem(new RenderSystem(map, viewport));
        engine.addSystem(new AnimationSystem());
    }

    @Override
    public void render() {
        centerCamera();

        player.update(controls, map);
        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        centerCamera();
    }

    private void centerCamera() {
        viewport.getCamera().position.lerp(new Vector3(player.pos.x, player.pos.y, 0), .1f);
        viewport.getCamera().update();
    }
}
