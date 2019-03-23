package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.entities.Mob;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.entities.Screen;
import com.ericc.the.game.helpers.FpsThrottle;
import com.ericc.the.game.map.Generator;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.systems.logic.*;
import com.ericc.the.game.systems.realtime.*;

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

    private FpsThrottle fpsThrottle = new FpsThrottle(60);

    @Override
    public void create() {
        Media.loadAssets();

        // we need a camera here to have an instance of Orthographic one in a viewport
        this.camera = new OrthographicCamera();
        viewport = new FillViewport(viewportWidth, viewportHeight, camera);
        viewport.apply();

        map = new Generator(30, 30, 9).generateMap();
        player = new Player(map.getRandomPassableTile(), new FieldOfViewComponent(map.width(), map.height()));
        FieldOfViewComponent playersFieldOfView = Mappers.fov.get(player);
        Screen screen = new Screen();

        controls = new KeyboardController(engines.getLogicEngine(), player, camera);
        Gdx.input.setInputProcessor(controls);

        engines.addEntityToBothEngines(player);
        engines.addEntityToBothEngines(screen);

        for (int i = 0; i < 10; i++) {
            engines.addEntityToBothEngines(new Mob(map.getRandomPassableTile()));
        }

        for (int i = 0; i < 10; i++) {
            engines.addEntityToBothEngines(new PushableObject(map.getRandomPassableTile(), Media.crate));
        }

        ScreenBoundariesGetterSystem visibleMapAreaSystem = new ScreenBoundariesGetterSystem(viewport, map, screen);
        engines.getRealtimeEngine().addSystem(new RenderSystem(map, viewport, playersFieldOfView, screen));
        engines.getRealtimeEngine().addSystem(new AnimationSystem());
        engines.getRealtimeEngine().addSystem(new TileChanger(.75f));
        engines.getRealtimeEngine().addSystem(visibleMapAreaSystem);
        engines.getRealtimeEngine().addSystem(new LightSystem(playersFieldOfView, map, screen));

        FieldOfViewSystem fieldOfViewSystem = new FieldOfViewSystem(map);
        FogOfWarSystem fogOfWarSystem = new FogOfWarSystem(player, map);
        engines.getLogicEngine().addSystem(new AiSystem());
        engines.getLogicEngine().addSystem(new InitiativeSystem());
        engines.getLogicEngine().addSystem(new ActionHandlingSystem(map));
        engines.getLogicEngine().addSystem(new MovementSystem(map));
        engines.getLogicEngine().addSystem(fieldOfViewSystem);
        engines.getLogicEngine().addSystem(fogOfWarSystem);

        initialisePlayersComponents(visibleMapAreaSystem, fieldOfViewSystem, fogOfWarSystem);

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
        fpsThrottle.sleepToNextFrame();

        System.out.print(Gdx.graphics.getFramesPerSecond() + "\n");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        centerCamera();
    }

    // TODO: Use PositionComponent instead of the pos attribute and remove Player's attributes.
    private void centerCamera() {
        viewport.getCamera().position.lerp(new Vector3(player.pos.x, player.pos.y, 0),
                1 - (float) Math.pow(.1f, Gdx.graphics.getDeltaTime()));
        viewport.getCamera().update();
    }

    /**
     * Before any turn is taken by a player, there are some values that should be initialised like:
     * - player's fov
     * - player's fog of war
     * - initial screen boundaries
     *
     * And that is exactly what this function is meant to do.
     */
    private void initialisePlayersComponents(ScreenBoundariesGetterSystem visibleMapAreaSystem,
                                             FieldOfViewSystem fieldOfViewSystem,
                                             FogOfWarSystem fogOfWarSystem) {
        visibleMapAreaSystem.update(0);
        fieldOfViewSystem.update(0); // update to calculate the initial fov
        fogOfWarSystem.update(0);
    }
}
