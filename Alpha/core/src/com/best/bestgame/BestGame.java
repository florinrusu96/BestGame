package com.best.bestgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.best.bestgame.menus.MainMenuScreen;

public class BestGame extends Game {
	public SpriteBatch batch;
        public int score;
        public int lifePoints;
        
        public static final int MENU_WIDTH = 480;
        public static final int MENU_HEIGHT = 800;
	
	@Override
	public void create () {
                lifePoints = 3;
		batch = new SpriteBatch();
                this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
