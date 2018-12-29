package com.bestgame.maze;

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
    private static final int size = 10;
    
    
    private Texture texture;
    private Vector3 position;
    private Rectangle bounds;
    
    public Ghost(){
        //initialize texture and initial position
        texture = new Texture("mazeghost.png");
        position = new Vector3(START_X, START_Y, 0);
        //initialize rectangle representing bounds
        bounds = new Rectangle(START_X, START_Y, size, size);
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
