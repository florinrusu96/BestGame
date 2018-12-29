package com.bestgame.maze;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 *The actual Screen of the game where everything happens.
 * @author mehai
 */
public class PlayScreen implements Screen{

    private Texture bg;
    private Texture maze;
    
    public PlayScreen(){
        
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        bg.dispose();
        maze.dispose();
    }

}
