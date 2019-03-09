package com.ericc.the.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderableComponent implements Component {
	public Sprite sprite;

	public RenderableComponent(Sprite sprite) {
		this.sprite = sprite;
		this.sprite.setSize(1, 1);
		this.sprite.setOrigin(0, 0);
	}
}
