package com.ericc.the.game.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.KeyboardController;
import com.ericc.the.game.Media;
import com.ericc.the.game.agencies.KeyboardAgency;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.CameraComponent;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.helpers.FpsThrottle;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.systems.logic.*;
import com.ericc.the.game.systems.realtime.*;

public class GameScreen implements Screen {
    public static GameScreen gameScreen;

    public final static int viewportWidth = 800;
    public final static int viewportHeight = 600;
    private final static boolean MUSIC = false; ///< turns the music on and off
    public GameOverlay overlay;
    private KeyboardController controls;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Dungeon dungeon;
    private Player player;
    public GameEngine gameEngine = new GameEngine();
    private FpsThrottle fpsThrottle = new FpsThrottle(60);

    @Override
    public void show() {
        gameScreen = this;
        Media.loadAssets();

        // we need a camera here to have an instance of Orthographic one in a viewport
        this.camera = new OrthographicCamera();
        camera.zoom = 0.03f;
        viewport = new FillViewport(viewportWidth, viewportHeight, camera);
        viewport.apply();

        this.dungeon = new Dungeon(gameEngine);
        dungeon.generateFirstLevel();

        controls = new KeyboardController(gameEngine, camera);

        player = new Player(
                dungeon.getCurrentMap().getRandomPassableTile(),
                dungeon.getCurrentMap(),
                new FieldOfViewComponent(dungeon.getCurrentMap().width(), dungeon.getCurrentMap().height()),
                new CameraComponent(viewport),
                new AgencyComponent(new KeyboardAgency(controls), false));


        overlay = new GameOverlay(viewportWidth, viewportHeight, player);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(controls);
        multiplexer.addProcessor(overlay.getStage());
        Gdx.input.setInputProcessor(multiplexer);


        gameEngine.addEntity(player);

        int priority = 0;
        gameEngine.addLogicSystem(new ActivitySystem(gameEngine, priority++));
        gameEngine.addLogicSystem(new FieldOfViewSystem(priority++));
        gameEngine.addLogicSystem(new FogOfWarSystem(priority++));
        gameEngine.addLogicSystem(new SafetyMapSystem(priority++));
        gameEngine.addLogicSystem(new FlagRemover(priority++));
        gameEngine.addLogicSystem(new EntityMapSystem());

        gameEngine.addRealtimeSystem(new AnimationSystem(priority++));
        gameEngine.addRealtimeSystem(new TileChanger(.75f, priority++));
        gameEngine.addRealtimeSystem(new CameraSystem(priority++));
        gameEngine.addRealtimeSystem(new FovFadeSystem(priority++));
        gameEngine.addRealtimeSystem(new GarbageCollector(priority++));

        gameEngine.addRealtimeSystem(new RenderSystem(priority++));

        if (MUSIC) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/8bitAdventure.mp3"));
            sound.loop();
            sound.play();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        overlay.fitOverlay(width, height);
    }

    @Override
    public void render(float delta) {
        controls.tick(delta);
        gameEngine.update();
        overlay.getStage().act(delta);
        overlay.getStage().draw();
        overlay.updateOverlay();
        fpsThrottle.sleepToNextFrame();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
