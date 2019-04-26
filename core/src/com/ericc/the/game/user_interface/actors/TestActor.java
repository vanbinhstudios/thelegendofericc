package com.ericc.the.game.user_interface.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TestActor extends Actor {
    Sprite sprite;

    public TestActor(TextureRegion textureRegion, final String name) {
        sprite = new Sprite(textureRegion);

        setPos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.disabled);

        addListener (new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Touched actor", name);
                return true;
            }
        });
    }

    public void setPos (float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        sprite.draw(batch);
    }
}
