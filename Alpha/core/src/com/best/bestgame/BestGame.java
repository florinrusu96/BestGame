package com.best.bestgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.best.bestgame.menus.EndGameScreen;
import com.best.bestgame.menus.MainMenuScreen;

public class BestGame extends Game {
	public SpriteBatch batch;
        public int score;
        public int lifePoints;
        public Screen lastScreen;
        
        public static final int MENU_WIDTH = 480;
        public static final int MENU_HEIGHT = 800;
        public BitmapFont font;
	
	@Override
	public void create () {
                font = new BitmapFont();
                lifePoints = 3;
		batch = new SpriteBatch();
                lastScreen = null;
                this.setScreen(new EndGameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
                getScreen().dispose();
	}
}
