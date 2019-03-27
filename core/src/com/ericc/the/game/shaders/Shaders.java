package com.ericc.the.game.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
    public static ShaderProgram hsl = new ShaderProgram(Gdx.files.internal("shaders/hsl.vert"), Gdx.files.internal("shaders/hsl.frag"));
}
