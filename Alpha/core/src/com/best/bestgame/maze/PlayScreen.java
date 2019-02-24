package com.best.bestgame.maze;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.best.bestgame.BestGame;
import com.best.bestgame.Timer;
import com.best.bestgame.menus.EndGameScreen;
import com.best.bestgame.menus.InterGameScreen;

/**
 *The actual Screen of the game where everything happens.
 * @author mehai
 */
public class PlayScreen implements Screen{

    private static final int BOUNDS_COUNT = 16;

    private BestGame game;

    private Texture bg;
    private Texture maze;
    private Array<Rectangle> mazeBounds;
    private Rectangle finishLine1, finishLine2;
    private Ghost ghost;

    private OrthographicCamera cam;

    private Timer timer;
    
    /**
     * Setup of the game components.
     * @param game
     */
    public PlayScreen(final BestGame game){
        this.game = game;
        timer = new Timer(60);
        //define camera and viewport
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 800);
        //initialize textures
        bg = new Texture("maze/mazebg-01.png");
        maze = new Texture("maze/maze-01.png");
        //initialize ghost
        ghost = new Ghost(cam);
        //initialize maze bounds and finish line
        initBounds();
    }

    @Override
    public void show() {
    }
    
    private void initBounds() {
        mazeBounds = new Array<Rectangle>();
        mazeBounds.add(new Rectangle(37.76f, 42.41f, 91.77f, 49f));
        mazeBounds.add(new Rectangle(37.76f, 42.41f, 49f, 198f));
        mazeBounds.add(new Rectangle(37.76f, 191.44f, 173.84f, 49f));
        
        mazeBounds.add(new Rectangle(161.61f, 42.41f, 50f, 219.95f));
        mazeBounds.add(new Rectangle(161.61f, 42.41f, 283.46f, 49f));
        mazeBounds.add(new Rectangle(396.07f, 42.41f, 49f, 551.46f));
        
        mazeBounds.add(new Rectangle(161.61f, 213.36f, 283.46f, 49f));
        mazeBounds.add(new Rectangle(161.61f, 289.56f, 283.46f, 49f));
        
        mazeBounds.add(new Rectangle(277.27f, 289.56f, 49f, 167.84f)); 
        mazeBounds.add(new Rectangle(161.61f, 408.4f, 164.66f, 49f));
        mazeBounds.add(new Rectangle(161.61f, 289.56f, 49f, 167.84f));
       
        mazeBounds.add(new Rectangle(160.61f, 544.86f, 284.46f, 49f));
        mazeBounds.add(new Rectangle(160.61f, 478.74f, 49f, 115.12f));
        mazeBounds.add(new Rectangle(38.92f, 478.74f, 170.69f, 49f));
       
        mazeBounds.add(new Rectangle(38.92f, 478.74f, 49f, 242f));
        mazeBounds.add(new Rectangle(38.92f, 617.9f, 406.15f, 49f));

        finishLine1 = new Rectangle(38.92f, 719f, 49f, 1f);
        finishLine2 = new Rectangle(443f, 617.9f, 1f, 49f );
    }

    /**
     * Actions performed after each frame: ghost movement and
     * collision detection.
     * @param delta
     */
    private void update(float delta){
        ghost.update(delta);
        //check collision with finish line
        /*GAME SCREEN CHANGES HERE*/
        if(ghost.getBounds().overlaps(finishLine1) || ghost.getBounds().overlaps(finishLine2)){
            game.lastScreen = this;
            game.score += 100;
            this.dispose();
            game.setScreen(new InterGameScreen(game));
            return;
        }
        //check collision with maze bounds
        boolean isInside = false;
        Rectangle ghostBounds = ghost.getBounds();
        for(int i = 0; i < BOUNDS_COUNT; i++){
            if(mazeBounds.get(i).contains(ghostBounds)){
                isInside = true;
                break;
            }
        }
        //game lost
        /*GAME SCREEN CHANGES HERE*/
        if(!isInside){
            game.lastScreen = this;
            game.score += 5;
            game.lifePoints--;
            this.dispose();
            if(game.lifePoints != 0){
                game.setScreen(new InterGameScreen(game));
            }else{
                game.setScreen(new EndGameScreen(game));            
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(!timer.update(delta)){
            game.lastScreen = this;
            game.score += 5;
            game.lifePoints--;
            this.dispose();
            if(game.lifePoints != 0){
                game.setScreen(new InterGameScreen(game));
            }else{
                game.setScreen(new EndGameScreen(game));
            }
        }
        
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.draw(maze, 0, 0);
        game.batch.draw(ghost.getTexture(), ghost.getX(), ghost.getY());
        game.drawScoreAndTimerInfo(cam, 5, timer.getSeconds());
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
        maze.dispose();
        ghost.dispose();
    }

}

