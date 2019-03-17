package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Generator;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.systems.logic.AiSystem;
import com.ericc.the.game.systems.logic.MovementSystem;
import com.ericc.the.game.systems.realtime.AnimationSystem;
import com.ericc.the.game.systems.realtime.RenderSystem;
import com.ericc.the.game.systems.realtime.TileChanger;

public class MainGame extends Game {

    private KeyboardController controls;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final static int viewportWidth = 24;
    private final static int viewportHeight = 18;

    private Map map;
    private Player player;

    private Engines engines = new Engines();

    public final static boolean DEBUG = true; ///< turns the debug mode on and off
    private final static boolean MUSIC = false; ///< turns the music on and off

    @Override
    public void create() {
        Media.loadAssets();

        // we need a camera here to have an instance of Orthographic one in a viewport
        this.camera = new OrthographicCamera();
        viewport = new FillViewport(viewportWidth, viewportHeight, camera);
        viewport.apply();

        map = new Generator(30, 30, 9).generateMap();
        player = new Player(map.getRandomPassableTile());

        controls = new KeyboardController(engines.getLogicEngine(), player, camera);
        Gdx.input.setInputProcessor(controls);

        engines.addEntityToBothEngines(player);

        for (int i = 0; i < 10; i++) {
            engines.addEntityToBothEngines(new Mob(map.getRandomPassableTile()));
        }

        engines.getRealtimeEngine().addSystem(new RenderSystem(map, viewport, player));
        engines.getRealtimeEngine().addSystem(new AnimationSystem());
        engines.getRealtimeEngine().addSystem(new TileChanger(.75f));

        engines.getLogicEngine().addSystem(new AiSystem());
        engines.getLogicEngine().addSystem(new MovementSystem(map));

        if (MUSIC) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/8bitAdventure.mp3"));
            sound.loop();
            sound.play();
        }
    }

    @Override
    public void render() {
        centerCamera();

        engines.updateRealtimeEngine();

        // TODO: Make FPS cap more resistant against extreme framerate drops
        try {
            Thread.sleep(16);
        } catch(Exception e) {
            System.out.print("Unexpected sleep interruption\n");
        }

        System.out.print(Gdx.graphics.getFramesPerSecond() + "\n");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        centerCamera();
    }

    private void centerCamera() {
        viewport.getCamera().position.lerp(new Vector3(player.pos.x, player.pos.y, 0),
                1 - (float) Math.pow(.1f, Gdx.graphics.getDeltaTime()));
        viewport.getCamera().update();
    }
}
