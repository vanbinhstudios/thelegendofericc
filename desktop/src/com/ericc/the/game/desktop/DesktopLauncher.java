package com.ericc.the.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.ericc.the.game.MainGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        TexturePacker.process(".", ".", "pack");
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setResizable(true);
        config.setWindowedMode(1024, 768);
        new Lwjgl3Application(new MainGame(), config);
    }
}
