package com.bestgame.maze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Maze extends Game {
	
        public static final int S_WIDTH = 480;
        public static final int S_HEIGHT = 800;
        public static final int GAME_SIZE = 480;
    
        SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                this.setScreen(new PlayScreen(this));
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
