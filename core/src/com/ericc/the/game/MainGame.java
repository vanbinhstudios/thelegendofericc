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
import com.ericc.the.game.entities.Screen;
import com.ericc.the.game.helpers.FpsThrottle;
import com.ericc.the.game.map.CurrentMap;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.map.Generator;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.systems.logic.AiSystem;
import com.ericc.the.game.systems.logic.FieldOfViewSystem;
import com.ericc.the.game.systems.logic.FogOfWarSystem;
import com.ericc.the.game.systems.logic.MovementSystem;
import com.ericc.the.game.systems.realtime.ScreenBoundariesGetterSystem;
import com.ericc.the.game.systems.realtime.AnimationSystem;
import com.ericc.the.game.systems.realtime.RenderSystem;
import com.ericc.the.game.systems.realtime.TileChanger;

public class MainGame extends Game {

    private KeyboardController controls;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final static int viewportWidth = 24;
    private final static int viewportHeight = 18;

    private Dungeon dungeon;
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

        this.dungeon = new Dungeon(engines);
        CurrentMap.setMap(dungeon.goToNext());

        player = new Player(CurrentMap.map.getRandomPassableTile(), new FieldOfViewComponent(CurrentMap.map.width(), CurrentMap.map.height()));
        FieldOfViewComponent playersFieldOfView = Mappers.fov.get(player);
        Screen screen = new Screen();

        controls = new KeyboardController(engines, player, camera, dungeon);
        Gdx.input.setInputProcessor(controls);

        engines.addEntity(player);
        engines.addEntity(screen);

        for (int i = 0; i < 10; i++) {
            engines.addEntity(new Mob(CurrentMap.map.getRandomPassableTile()));
        }

        ScreenBoundariesGetterSystem visibleMapAreaSystem = new ScreenBoundariesGetterSystem(viewport, screen);
        engines.addRealtimeSystem(new RenderSystem(viewport, playersFieldOfView, screen));
        engines.addRealtimeSystem(new AnimationSystem());
        engines.addRealtimeSystem(new TileChanger(.75f));
        engines.addRealtimeSystem(visibleMapAreaSystem);

        FieldOfViewSystem fieldOfViewSystem = new FieldOfViewSystem(screen);
        FogOfWarSystem fogOfWarSystem = new FogOfWarSystem(player);
        engines.addLogicSystem(new AiSystem());
        engines.addLogicSystem(new MovementSystem());
        engines.addLogicSystem(fieldOfViewSystem);
        engines.addLogicSystem(fogOfWarSystem);

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

//        System.out.print(Gdx.graphics.getFramesPerSecond() + "\n");
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
        System.out.println("Ticking");
        visibleMapAreaSystem.update(0);
        fieldOfViewSystem.update(0); // update to calculate the initial fov
        fogOfWarSystem.update(0);
    }

    private void reload() {

    }
}
