package com.bestgame.maze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bestgame.maze.Maze;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Maze";
                config.width = 480;
                config.height = 800;
		new LwjglApplication(new Maze(), config);
	}
}
