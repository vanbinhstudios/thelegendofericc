package com.ericc.the.game.user_interface.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Models;
import com.ericc.the.game.user_interface.actors.TestActor;

public class GameOverlay {

    private Stage stage;
    private Viewport viewport;

    public GameOverlay() {
        viewport = new FitViewport(800, 600);

        stage = new Stage(viewport);

        //Gdx.input.setInputProcessor(stage);

        TestActor actor1 = new TestActor(Models.explosion3.sheet[0], "test");

        actor1.setPos(300, 400);

        stage.addActor(actor1);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }
}
