package com.ericc.the.game.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.components.InventoryComponent;
import com.ericc.the.game.items.Item;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.ui.actors.*;

public class GameOverlay {

    private static final Image images[] = {
            new Image(new TextureRegionDrawable(Media.fireExplosion)),
            new Image(new TextureRegionDrawable(Media.fireBeam)),
            new Image(new TextureRegionDrawable(Media.arrowShot))
    };
    private static final String constBindings[] = {"Q", "E", "F", "I"};
    public TopBar healthBar;
    public TopBar manaBar;
    public TopBar experienceBar;
    public ItemTable skillTable;
    public ItemTableTextOverlay skillTableOverlay;
    public ItemTable inventoryTable;
    public InfoText info;
    public InfoText item;
    public BitmapFont font = new BitmapFont();
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
        skillTable = new ItemTable(170, 50, 5);
        skillTableOverlay = new ItemTableTextOverlay(170, 50, 5);
        inventoryTable = new ItemTable(170, 170, 5);


        info.setPosition(20, height - 100);
        healthBar.setPosition(20, height - 40);
        manaBar.setPosition(20, height - 60);
        experienceBar.setPosition(20, height - 80);
        item.setPosition(20, height - 120);
        skillTable.setPosition(20, 20);
        skillTableOverlay.setPosition(20, 20);
        inventoryTable.setPosition(20, 90);
        inventoryTable.content.align(Align.topLeft);

        updateSkillBar();
        updateInventoryBar();

        stage.addActor(info);
        stage.addActor(healthBar);
        stage.addActor(experienceBar);
        stage.addActor(skillTable);
        stage.addActor(skillTableOverlay);
        stage.addActor(inventoryTable);
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
                Item itemAux = Mappers.inventory.get(player).items.get(Mappers.inventory.get(player).chosen);

                item.setText("Chosen item: " + itemAux.name + " x " + itemAux.quantity);
            } else {
                item.setText("Empty inventory");
            }

            updateSkillBar();
            updateInventoryBar();

        } else {
            healthBar.setValue(0);
            experienceBar.setValue(0);
            manaBar.setValue(0);
        }
    }

    public void updateSkillBar() {
        skillTable.content.clearChildren();

        InventoryComponent inventory = Mappers.inventory.get(player);

        Box container, element;

        for (int i = 0; i < images.length; i++) {
            container = new Box(40, 40, Color.LIGHT_GRAY);
            element = new Box(30, 30, Color.GRAY);
            element.add(images[i]);
            container.add(element);
            skillTable.content.add(container);
        }

        container = new Box(40, 40, Color.LIGHT_GRAY);
        element = new Box(30, 30, Color.GRAY);
        if (!inventory.items.isEmpty()) {
            element.add(new Image(new TextureRegionDrawable(inventory.items.get(inventory.chosen).model.sheet[0])));
        }
        container.add(element);
        skillTable.content.add(container);

        skillTableOverlay.content.clearChildren();

        for (int i = 0; i < constBindings.length; i++) {
            element = new Box(40, 40, Color.CLEAR);
            Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);

            if (constBindings[i].equals("Q") && Mappers.stats.get(player).level < 4) {
                style.fontColor = Color.RED;
            } else if (constBindings[i].equals("E") && Mappers.stats.get(player).level < 2) {
                style.fontColor = Color.RED;
            } else if (constBindings[i].equals("F") && Mappers.stats.get(player).arrows <= 0) {
                style.fontColor = Color.RED;
            } else if (constBindings[i].equals("I") && Mappers.inventory.get(player).items.isEmpty()) {
                style.fontColor = Color.RED;
            }
            Label skill = new Label(constBindings[i], style);
            element.add(skill);
            skillTableOverlay.content.add(element);
        }
    }

    public void updateInventoryBar() {
        inventoryTable.content.clearChildren();

        InventoryComponent inventory = Mappers.inventory.get(player);

        Box container, element;

        for (int i = 0; i < Math.min(inventory.items.size(), 16); i++) {
            container = new Box(40, 40, Color.LIGHT_GRAY);
            element = new Box(30, 30, Color.GRAY);
            if (inventory.chosen == i) element.setColor(Color.SLATE);
            element.add(new Image(new TextureRegionDrawable(inventory.items.get(i).model.sheet[0])));
            container.add(element);
            if (i % 4 == 3) inventoryTable.content.add(container).row();
            else inventoryTable.content.add(container);
        }
    }
}
