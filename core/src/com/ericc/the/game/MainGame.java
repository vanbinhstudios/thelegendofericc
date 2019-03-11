package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Generator;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.systems.RenderSystem;

public class MainGame extends Game {

    private KeyboardControls controls;
    private OrthographicCamera camera;

    private Map map;
    private Player player;

    private Engine engine = new Engine();

    @Override
    public void create() {
        Media.loadAssets();
        camera = new OrthographicCamera(24, 18);

        controls = new KeyboardControls();
        Gdx.input.setInputProcessor(controls);

        map = new Generator(200, 50, 12).generateMap();
        player = new Player(map.getRandomPassableTile());

        engine.addEntity(player);
        engine.addSystem(new RenderSystem(map, camera));
    }

    @Override
    public void render() {
        // Camera interpolation
        camera.position.lerp(new Vector3(player.pos.x, player.pos.y, 0), .1f);
        camera.zoom = 1f;
        camera.update();

        player.update(controls, map);
        engine.update(Gdx.graphics.getDeltaTime());
    }
}
