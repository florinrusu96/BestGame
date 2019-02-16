package com.best.bestgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.best.bestgame.BestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 480;
                config.height = 800;
                config.title = "BESTgame";
		new LwjglApplication(new BestGame(), config);
	}
}
