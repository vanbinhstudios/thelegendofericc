package com.ericc.the.game.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Media;
import com.ericc.the.game.ui.UiSkin;
import com.ericc.the.game.ui.actors.Popup;

public class MainMenu implements Screen {
    static Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    private static Image background = new Image(new TextureRegionDrawable(Media.background));
    protected Stage stage;
    protected Skin skin;
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Label title;

    public MainMenu() {
        skin = UiSkin.get();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameScreen.viewportWidth, GameScreen.viewportHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        title = new Label("The Legend of Ericc", style);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setPosition(0, -GameScreen.viewportHeight / 4, Align.center);

        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
                // TODO Proper intro text
                Popup.show("", "Down beneath the Faculty of Mathematics, Informatics and Mechanics\n" +
                        "there is a lair belonging to the Dark Master of Probability\n" +
                        "\n" +
                        "                ERICC                \n" +
                        "\n" +
                        "Your goal is to reach the deepest levels of this dungeon\n" +
                        "and prevent ERICC from turning the entire world into a\n" +
                        "GIANT HYPERBOLIC PLANE");
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(exitButton);

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(mainTable);

        title.setFontScale(4);
//        title.setFillParent(true);
        title.setAlignment(Align.center);
        title.setPosition(GameScreen.viewportWidth / 2, GameScreen.viewportHeight * 0.8f);
        stage.addActor(title);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
