package com.bestgame.flappybird;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author mehai
 */
public class PlayScreen implements Screen{

    public final static int V_WIDTH = 240;
    public final static int V_HEIGHT = 400;
    private final static int TUBE_SPACING = 140;
    private final static int TUBE_COUNT = 3;
    
    private final FlappyBird game;
    private OrthographicCamera camera;
    
    private Texture bg; 
    private Bird bird;
    private Array<Tube> tubes;
    private int tubeIndex = 1;
    
    public PlayScreen(FlappyBird game){
        this.game = game;
        //set camera to area desired to see
        camera = new OrthographicCamera(480, 800);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        //set background texture
        bg = new Texture("bg.png");
        //create Sprites
        bird = new Bird(10, 300);
        tubes = new Array();
        for(int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(tubeIndex * TUBE_SPACING));
            tubeIndex++;
        }
    }
    
    @Override
    public void show() {
    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }
    
    public void update() {
        handleInput();
        //update bird
        bird.update(Gdx.graphics.getDeltaTime());
        camera.position.x += bird.getMovement(Gdx.graphics.getDeltaTime());
        camera.update();
        for(int i = 0; i < TUBE_COUNT; i++){
            Tube tube = tubes.get(i);
            if(tube.getTopPosition().x + tube.TUBE_WIDTH < camera.position.x - (camera.viewportWidth / 2)){
                tube.reposition(tubeIndex * TUBE_SPACING);
                tubeIndex++;
            }
        }
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg, camera.position.x - camera.viewportWidth / 2, 0);
        game.batch.draw(bird.getTexture(), bird.getX(), bird.getY());
        for(int i = 0; i < TUBE_COUNT; i++){
            Tube tube = tubes.get(i);
            game.batch.draw(tube.getBottomTube(), tube.getBottomPosition().x, tube.getBottomPosition().y);
            game.batch.draw(tube.getTopTube(), tube.getTopPosition().x, tube.getTopPosition().y);
        }
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
        bg.dispose();
        bird.dispose();
        for(int i = 0; i < TUBE_COUNT; i++){
            tubes.get(i).dispose();
        }
    }

}
