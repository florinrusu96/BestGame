package com.bestgame.flappybird;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 *The actual screen for Flappy Bird where everything happens 
 * and elements interact.
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
    private Texture ground;
    private Vector3 groundPos1, groundPos2;
    private Rectangle groundBounds1, groundBounds2;
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
        //create ground
        ground = new Texture("ground.png");
        groundPos1 = new Vector3(camera.position.x - camera.viewportWidth / 2, -50, 0);
        groundPos2 = new Vector3(groundPos1.x + ground.getWidth(), -50, 0);
        groundBounds1 = new Rectangle(groundPos1.x, groundPos1.y, ground.getWidth(), ground.getHeight());
        groundBounds2 = new Rectangle(groundPos2.x, groundPos2.y, ground.getWidth(), ground.getHeight());
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
        //update bird and camera
        bird.update(Gdx.graphics.getDeltaTime());
        camera.position.x += bird.getMovement(Gdx.graphics.getDeltaTime());
        camera.update();
        //reposition tubes if necesarry and check for collisions
        for(int i = 0; i < TUBE_COUNT; i++){
            Tube tube = tubes.get(i);
            if(tube.getTopPosition().x + tube.TUBE_WIDTH < camera.position.x - (camera.viewportWidth / 2)){
                tube.reposition(tubeIndex * TUBE_SPACING);
                tubeIndex++;
            }
            if(bird.collides(tube)){
                game.getScreen().dispose();
                game.setScreen(new PlayScreen(game));
            }
        }
        //reposition ground if necesarry
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.x = groundPos2.x + ground.getWidth();
            groundBounds1.setPosition(groundPos1.x, groundPos1.y);
        }
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.x = groundPos1.x + ground.getWidth();
            groundBounds2.setPosition(groundPos2.x, groundPos1.y);
        }
        //check collision with ground
        if(bird.collides(groundBounds1) || bird.collides(groundBounds2)){
            game.getScreen().dispose();
            game.setScreen(new PlayScreen(game));
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
        game.batch.draw(ground, groundPos1.x, groundPos1.y);
        game.batch.draw(ground, groundPos2.x, groundPos2.y);
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
        ground.dispose();
    }

}
