package com.bestgame.flappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 *Represents the bird object, its texture, position and methods that make it
 *a dynamic sprite.
 * @author mehai
 */
public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    
    private Texture texture;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    
    public Bird(int x, int y){
        texture = new Texture("bird.png");
        //define position and velocity vectors
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0 ,0 ,0);
        //define bounds
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    public void update(float delta){
        if(position.y > 0){
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(delta);
        position.add(MOVEMENT*delta, velocity.y, 0);
        if(position.y < 0){
            position.y = 0;
        }
        bounds.setPosition(position.x, position.y);
        velocity.scl(1/delta);
        
    }

    /**
     * Simulates jump using the velocity vector.
     */
    public void jump(){
        velocity.y = 250;
    }
    
    /**
     * Checks if bird collides with given tube
     * @param tube tube to be checked for collision with bird
     * @return boolean value
     */
    public boolean collides(Tube tube){
        if(bounds.overlaps(tube.getBottomBounds()) || bounds.overlaps(tube.getTopBounds())){
            return true;
        }
        return false;
    }
    
    
    /**
     * Checks if bird collides with given ground Object
     * @param ground ground object to be checked for collision with bird
     * @return boolean value
     */
    public boolean collides(Rectangle ground){
        return bounds.overlaps(ground);
    }
    
    public float getX(){
        return position.x;
    }
    
    public float getY(){
        return position.y;
    }
    
    public Texture getTexture(){
        return texture;
    }
    
    /**
     * Returns movement executed on X axis by bird
     * @param delta deltatime
     * @return 
     */
    public float getMovement(float delta){
        return MOVEMENT * delta;
    }
    
    public void dispose(){
        texture.dispose();
    }
    
}
