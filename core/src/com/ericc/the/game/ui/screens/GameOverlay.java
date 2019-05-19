package com.ericc.the.game.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.InventoryComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.ui.actors.InfoText;
import com.ericc.the.game.ui.actors.TopExpBar;
import com.ericc.the.game.ui.actors.TopHealthbar;

public class GameOverlay {

    public TopHealthbar hbar;
    public TopExpBar ebar;
    public InfoText info;
    public InfoText item;
    private Stage stage;
    private Viewport viewport;
    private Player player;

    public GameOverlay(int width, int height, Player currentPlayer) {
        viewport = new FitViewport(width, height);

        stage = new Stage(viewport);

        player = currentPlayer;

        info = new InfoText(10, 100, 30);
        item = new InfoText(10, 100, 30);
        hbar = new TopHealthbar(200, 15);
        ebar = new TopExpBar(200, 15);
        item.setPosition(20, height - 130);
        info.setPosition(20, height - 100);
        hbar.setPosition(20, height - 40);
        ebar.setPosition(20, height - 70);

        stage.addActor(info);
        stage.addActor(hbar);
        stage.addActor(ebar);
        stage.addActor(item);
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
            hbar.setValue((float) Mappers.stats.get(player).health / (float) Mappers.stats.get(player).maxHealth);
            ebar.setValue((float) Mappers.stats.get(player).experience / (float) Mappers.stats.get(player).maxExperience);
            info.setText("LVL: " + Mappers.stats.get(player).level + " HP: " + Mappers.stats.get(player).health + " Arrows: " + Mappers.stats.get(player).arrows);

            InventoryComponent inventory = Mappers.inventory.get(player);
            if (inventory != null && inventory.items.size() > 0) {
                item.setText("Chosen item: " + Mappers.inventory.get(player).items.get(Mappers.inventory.get(player).chosen).name);
            } else {
                item.setText("Empty inventory");
            }
        } else {
            hbar.setValue(0);
            ebar.setValue(0);
        }
    }
}
