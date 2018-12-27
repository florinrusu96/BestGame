package com.bestgame.flappybird;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author mehai
 */
public class PlayScreen implements Screen{

    private final FlappyBird game;
    private OrthographicCamera camera;
    private Texture bg;
    
    public PlayScreen(FlappyBird game){
        this.game = game;
        
        camera = new OrthographicCamera(480, 800);
        camera.setToOrtho(false, 480, 800);
        
        bg = new Texture("bg.png");
        
    }
    
    @Override
    public void show() {
    }

    public void handleInput(){
        
    }
    
    public void update() {
        handleInput();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        game.batch.draw(bg, 0, 0, 480, 800);
        game.batch.end();
        
        update();
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
    }

}
