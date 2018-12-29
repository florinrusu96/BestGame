package com.bestgame.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *The actual Screen of the game where everything happens.
 * @author mehai
 */
public class PlayScreen implements Screen{

    private Maze game;
    
    private Texture bg;
    private Texture maze;
    private Array<Rectangle> mazeBounds;
    private Rectangle finishLine;
    private Ghost ghost;
    private OrthographicCamera cam;
    private Viewport viewPort;
    
    /**
     * Setup of the game components.
     * @param game 
     */
    public PlayScreen(Maze game){
        this.game = game;
        //define camera and viewport
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 480);
        viewPort = new FitViewport(480, 480, cam);
        //initialize textures
        bg = new Texture("mazebg.png");
        maze = new Texture("maze.png");
        //initialize ghost
        ghost = new Ghost();
        //initialize maze bounds and finish line
        //numbers were obtained when i created the maze
        mazeBounds = new Array<Rectangle>();
        mazeBounds.add(new Rectangle(70, 90, 340, 40));
        mazeBounds.add(new Rectangle(370, 130, 40, 130));
        mazeBounds.add(new Rectangle(100, 260, 310, 40));
        mazeBounds.add(new Rectangle(100, 300, 40, 110));
        mazeBounds.add(new Rectangle(140, 368, 134, 42));
        finishLine = new Rectangle(274, 366, 14, 46);    
    }
    
    @Override
    public void show() {
    }
    
    
    private void update(float delta){
        ghost.update(delta);
        //check collision with finish line
        if(ghost.getBounds().overlaps(finishLine)){
            System.out.println("GAME WON!");
            this.dispose();
            game.setScreen(new PlayScreen(game));
        }
        //check collision with maze bounds
        boolean overlaps = false;
        for(Rectangle bound : mazeBounds){
            if(ghost.getBounds().overlaps(bound)){
                overlaps = true;
            }
        }
        //game lost
        if(!overlaps){
            System.out.println("GAME LOST!");
            this.dispose();
            game.setScreen(new PlayScreen(game)); 
        }       
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        
        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.draw(maze, 0, 0);
        game.batch.draw(ghost.getTexture(), ghost.getX(), ghost.getY());
        game.batch.end();
        
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
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
