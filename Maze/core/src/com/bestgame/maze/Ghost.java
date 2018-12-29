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
    private static final int MOVEMENT = 100;
    
    
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

    public void update(float delta){
        if(Gdx.input.isTouched()){
            //get coordinates
            int x = Gdx.input.getX();
            //y is given on a reversed basis by Gfx.input.getY()
            //that's why we substract 480 (the height)
            //The other number represents the top bar error we need to
            //consider for input
            int y = 480 + ((800 - 480) / 2) - Gdx.input.getY();
            
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
