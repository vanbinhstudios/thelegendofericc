package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.agencies.KeyboardAgency;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.CameraComponent;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.helpers.FpsThrottle;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.systems.logic.*;
import com.ericc.the.game.systems.realtime.*;

public class MainGame extends Game {

    public final static boolean DEBUG = true; ///< turns the debug mode on and off
    private final static int viewportWidth = 24;
    private final static int viewportHeight = 18;
    private final static boolean MUSIC = false; ///< turns the music on and off
    private KeyboardController controls;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Dungeon dungeon;
    private Player player;
    private GameEngine gameEngine = new GameEngine();
    private FpsThrottle fpsThrottle = new FpsThrottle(60);

    @Override
    public void create() {
        Media.loadAssets();

        // we need a camera here to have an instance of Orthographic one in a viewport
        this.camera = new OrthographicCamera();
        viewport = new FillViewport(viewportWidth, viewportHeight, camera);
        viewport.apply();

        this.dungeon = new Dungeon(gameEngine);
        dungeon.generateFirstLevel();

        controls = new KeyboardController(gameEngine, camera);
        Gdx.input.setInputProcessor(controls);

        player = new Player(
                dungeon.getCurrentMap().getRandomPassableTile(),
                dungeon.getCurrentMap(),
                new FieldOfViewComponent(dungeon.getCurrentMap().width(), dungeon.getCurrentMap().height()),
                new CameraComponent(viewport),
                new AgencyComponent(new KeyboardAgency(controls)));

        gameEngine.addEntity(player);

        int priority = 0;
        gameEngine.addLogicSystem(new DeathSystem(priority++));
        gameEngine.addLogicSystem(new ActivitySystem(gameEngine, priority++));
        gameEngine.addLogicSystem(new AgencySystem(priority++));
        gameEngine.addLogicSystem(new AreaOfEffectAttackSystem(priority++));
        gameEngine.addLogicSystem(new TeleportSystem(dungeon, priority++));
        gameEngine.addLogicSystem(new MovementSystem(priority++));
        gameEngine.addLogicSystem(new DamageSystem(priority++));
        gameEngine.addLogicSystem(new FieldOfViewSystem(priority++));
        gameEngine.addLogicSystem(new FogOfWarSystem(priority++));
        gameEngine.addLogicSystem(new EntityMapSystem());

        gameEngine.addRealtimeSystem(new AnimationSystem(priority++));
        gameEngine.addRealtimeSystem(new TileChanger(.75f, priority++));
        gameEngine.addRealtimeSystem(new CameraSystem(priority++));
        gameEngine.addRealtimeSystem(new FovFadeSystem(priority++));
        gameEngine.addRealtimeSystem(new DirectedSpritesheetSystem(priority++));

        gameEngine.addRealtimeSystem(new RenderSystem(priority++));

        gameEngine.getSystem(FieldOfViewSystem.class).update(0);
        gameEngine.getSystem(FogOfWarSystem.class).update(0);

        if (MUSIC) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/8bitAdventure.mp3"));
            sound.loop();
            sound.play();
        }
    }

    @Override
    public void render() {
        gameEngine.update();
        fpsThrottle.sleepToNextFrame();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
