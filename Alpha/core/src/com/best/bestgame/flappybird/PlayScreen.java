package com.best.bestgame.flappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.best.bestgame.BestGame;
import com.best.bestgame.Timer;
import com.best.bestgame.menus.EndGameScreen;
import com.best.bestgame.menus.InterGameScreen;

/**
 *The actual screen for Flappy Bird where all elements exist 
 *and interact.
 * @author mehai
 */
public class PlayScreen implements Screen{

    public final static int V_WIDTH = 480;
    public final static int V_HEIGHT = 800;
    private final static int TUBE_SPACING = 180;
    private final static int TUBE_COUNT = 4;
    
    private final BestGame game;
    private final OrthographicCamera camera;
    
    protected Texture bg; 
    protected Texture ground;
    protected Texture topTube, bottomTube;
    private Vector3 groundPos1, groundPos2;
    private Rectangle groundBounds1, groundBounds2;
    private Bird bird;
    private Array<Tube> tubes;
    private int tubeIndex = 1;
    private int score;
    
    private Timer timer;

    /**
     * Defines the initial screen of the game and initializes all components
     * @param game the actual game that delegates to this screen
     */
    public PlayScreen(final BestGame game){
        this.game = game;
        timer = new Timer(20);
        //set camera to area desired to see
        camera = new OrthographicCamera(480, 800);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        //set background texture
        bg = new Texture("flappybird/flappy-bg.png");
        //create Sprites
        bird = new Bird(10, 600);
        topTube = new Texture("flappybird/toptubev2.png");
        bottomTube = new Texture("flappybird/bottomtubev2.png");
        tubes = new Array();
        for(int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(tubeIndex * TUBE_SPACING, topTube, bottomTube));
            tubeIndex++;
        }
        //create ground
        ground = new Texture("flappybird/groundv2.png");
        groundPos1 = new Vector3(camera.position.x - camera.viewportWidth / 2, 0, 0);
        groundPos2 = new Vector3(groundPos1.x + ground.getWidth(), 0, 0);
        groundBounds1 = new Rectangle(groundPos1.x, groundPos1.y, ground.getWidth(), ground.getHeight());
        groundBounds2 = new Rectangle(groundPos2.x, groundPos2.y, ground.getWidth(), ground.getHeight());
    }
    
    @Override
    public void show() {
    }
    
    /**
     * handles the possible received input.
     */
    public void handleInput(){
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }
    
    /**
     * updates camera, component position and behavior.
     * @param delta deltatime
     */
    public void update(float delta) {
        handleInput();
        //update bird and camera
        bird.update(delta);
        camera.position.x += bird.getMovement(delta);
        camera.update();
        //reposition tubes if necesarry and check for collisions
        for(int i = 0; i < TUBE_COUNT; i++){
            Tube tube = tubes.get(i);
            if(tube.getTopPosition().x + Tube.TUBE_WIDTH < camera.position.x - (camera.viewportWidth / 2)){
                tube.reposition(tubeIndex * TUBE_SPACING);
                tubeIndex++;
                score += (int)(Math.random() * 4 + 4) ;
            }
            /*SCREEN CHANGING HERE*/
            if(bird.collides(tube)){
                game.lastScreen = this;
                game.lifePoints--;
                game.score += score;
                game.getScreen().dispose();
                if(game.lifePoints != 0){
                    game.setScreen(new InterGameScreen(game, score));
                }else{
                    game.setScreen(new EndGameScreen(game));
                }
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
        /*SCREEN CHANGING HERE*/
        if(bird.collides(groundBounds1) || bird.collides(groundBounds2)){
            game.lastScreen = this;
            game.lifePoints--;
            game.score += score;
            game.getScreen().dispose();
            if(game.lifePoints != 0){
                game.setScreen(new InterGameScreen(game, score));
            }else{
                game.setScreen(new EndGameScreen(game));
            }
        }
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // CHANGE SCREEN HERE -> TIME IS UP
        if(!timer.update(delta)){
            game.score += score;
            game.lastScreen = this;
            dispose();
            game.setScreen(new InterGameScreen(game, score));
            return;
        }
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg, camera.position.x - camera.viewportWidth / 2, 92);
        game.batch.draw(bird.getTexture(), bird.getX(), bird.getY());
        for(int i = 0; i < TUBE_COUNT; i++){
            Tube tube = tubes.get(i);
            game.batch.draw(tube.getBottomTube(), tube.getBottomPosition().x, tube.getBottomPosition().y);
            game.batch.draw(tube.getTopTube(), tube.getTopPosition().x, tube.getTopPosition().y);
        }
        game.batch.draw(ground, groundPos1.x, groundPos1.y);
        game.batch.draw(ground, groundPos2.x, groundPos2.y);

        game.drawScoreAndTimerInfo(camera, score, timer.getSeconds());
        game.batch.end();
        
        update(delta);
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
        topTube.dispose();
        bottomTube.dispose();
        ground.dispose();
    }

}
