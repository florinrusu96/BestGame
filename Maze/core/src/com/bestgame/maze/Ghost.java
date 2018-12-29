package com.bestgame.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *Describes everything about the main character.
 * @author mehai
 */
public class Ghost {
    
    private static final int START_X = 80;
    private static final int START_Y = 100;
    private static final int SIZE = 10;    
    
    private Texture texture;
    private Vector3 position;
    private Rectangle bounds;
    
    public Ghost(){
        //initialize texture and initial position
        texture = new Texture("mazeghost.png");
        position = new Vector3(START_X, START_Y, 0);
        //initialize rectangle representing bounds
        bounds = new Rectangle(START_X, START_Y, SIZE, SIZE);
    }

    /**
     * Updates ghost position using distance formula. The ghost follows the
     * point where the player touches and it goes there less and less faster.
     * Further testing is needed for android version.
     * @param delta 
     */
    public void update(float delta){
        if(Gdx.input.isTouched()){
            //get coordinates
            int x = Gdx.input.getX();
            //y is given on a reversed basis by Gfx.input.getY()
            //that's why we substract GAME_SIZE.
            //The number in pharanteses represent the screen error we have
            //because of the screen size (it represents the top black bar).
            int y = Maze.GAME_SIZE + ((Maze.S_HEIGHT - Maze.S_WIDTH) / 2) - Gdx.input.getY();
            //formulas for distance
            float xDistance = x - position.x;
            float yDistance = y - position.y;
            float distance = (float)Math.sqrt(xDistance*xDistance + yDistance*yDistance);
            //if the error is at most 10 pixels
            if(distance > 10){
                //update position
                position.x += xDistance * delta;
                position.y += yDistance * delta;
            }
            //update bounds
            bounds.setPosition(position.x, position.y);
        }
    }
    
    public Texture getTexture() {
        return texture;
    }

    public Vector3 getPosition() {
        return position;
    }
    
    public float getX() {
        return position.x;
    }
    
    public float getY() {
        return position.y;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    
    public void dispose(){
        texture.dispose();
    }
}
