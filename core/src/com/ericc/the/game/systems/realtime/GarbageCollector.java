package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.ericc.the.game.MainGame;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DeadTag;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.ui.UiSkin;
import com.ericc.the.game.ui.screens.GameScreen;
import com.ericc.the.game.ui.screens.MainMenu;

public class GarbageCollector extends IteratingSystem {
    public GarbageCollector(int priority) {
        super(Family.all(DeadTag.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RenderableComponent renderable = Mappers.renderable.get(entity);
        DeadTag tag = Mappers.dead.get(entity);
        if (renderable == null || !renderable.visible || tag.time > 5.0f) {
            if (Mappers.player.has(entity)) {
                Dialog dialog = new Dialog("You lost!", UiSkin.get(), "dialog") {
                    public void result(Object obj) {
                        MainGame.game.setScreen(new MainMenu());
                    }
                };
                dialog.text("Start a new game");
                dialog.button("OK", true);
                dialog.key(Input.Keys.ENTER, true);
                dialog.show(GameScreen.gameScreen.overlay.getStage());
                GameScreen.gameScreen.gameEngine.stopCompletely();
            } else if (Mappers.boss.has(entity)) {
                Dialog dialog = new Dialog("You won!", UiSkin.get(), "dialog") {
                    public void result(Object obj) {
                        MainGame.game.setScreen(new MainMenu());
                    }
                };
                dialog.text("Start a new game");
                dialog.button("OK", true);
                dialog.key(Input.Keys.ENTER, true);
                dialog.show(GameScreen.gameScreen.overlay.getStage());
                GameScreen.gameScreen.gameEngine.stopCompletely();
            } else {
                getEngine().removeEntity(entity);
            }
        } else {
            tag.time += deltaTime;
        }
    }
}
