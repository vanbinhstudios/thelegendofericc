package com.ericc.the.game.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.InventoryComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.ui.actors.InfoText;
import com.ericc.the.game.ui.actors.TopBar;

public class GameOverlay {

    public TopBar healthBar;
    public TopBar manaBar;
    public TopBar experienceBar;
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
        healthBar = new TopBar(200, 15, Color.RED, Color.GREEN);
        manaBar = new TopBar(200, 15, Color.NAVY, Color.BLUE);
        experienceBar = new TopBar(200, 15, Color.GOLD, Color.YELLOW);

        info.setPosition(20, height - 100);
        healthBar.setPosition(20, height - 40);
        manaBar.setPosition(20, height - 60);
        experienceBar.setPosition(20, height - 80);
        item.setPosition(20, height - 120);

        stage.addActor(info);
        stage.addActor(healthBar);
        stage.addActor(experienceBar);
        stage.addActor(manaBar);
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
            healthBar.setValue((float) Mappers.stats.get(player).health / (float) Mappers.stats.get(player).maxHealth);
            manaBar.setValue((float) Mappers.stats.get(player).mana / (float) Mappers.stats.get(player).maxMana);
            experienceBar.setValue((float) Mappers.stats.get(player).experience / (float) Mappers.stats.get(player).maxExperience);
            info.setText("LVL: " + Mappers.stats.get(player).level + " HP: " + Mappers.stats.get(player).health + " Arrows: " + Mappers.stats.get(player).arrows);

            InventoryComponent inventory = Mappers.inventory.get(player);
            if (inventory != null && inventory.items.size() > 0) {
                item.setText("Chosen item: " + Mappers.inventory.get(player).items.get(Mappers.inventory.get(player).chosen).name);
            } else {
                item.setText("Empty inventory");
            }
        } else {
            healthBar.setValue(0);
            experienceBar.setValue(0);
            manaBar.setValue(0);
        }
    }
}
