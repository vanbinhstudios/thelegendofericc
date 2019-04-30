package com.ericc.the.game.user_interface.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.user_interface.actors.InfoText;
import com.ericc.the.game.user_interface.actors.TopExpBar;
import com.ericc.the.game.user_interface.actors.TopHealthbar;

public class GameOverlay {

    private Stage stage;
    private Viewport viewport;
    private Player player;
    public TopHealthbar hbar;
    public TopExpBar ebar;
    public InfoText info;

    public GameOverlay(int width, int height, Player currentPlayer) {
        viewport = new FitViewport(width, height);

        stage = new Stage(viewport);

        player = currentPlayer;

        info = new InfoText(10, 100);
        hbar = new TopHealthbar(200, 15);
        ebar = new TopExpBar(200, 15);
        info.setPosition(20, height - 100);
        hbar.setPosition(20, height - 40);
        ebar.setPosition(20, height - 70);

        stage.addActor(info);
        stage.addActor(hbar);
        stage.addActor(ebar);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }

    public void fitOverlay(int x, int y) {
        viewport.update(x, y);
    }

    public void updateOverlay() {
        if (player != null) {
            hbar.setValue((float) Mappers.stats.get(player).health / (float)Mappers.stats.get(player).maxHealth);
            ebar.setValue((float)Mappers.stats.get(player).experience / (float)Mappers.stats.get(player).maxExperience);
            info.setText("LVL: " + Mappers.stats.get(player).level + " HP: " + Mappers.stats.get(player).health);
        } else {
            hbar.setValue(0);
            ebar.setValue(0);
        }
    }
}
