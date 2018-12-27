package com.bestgame.flappybird;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


/**
 *Represents the bird object, its texture, position and methods that make it
 *a dynamic sprite.
 * @author mehai
 */
public class Bird {
    private static final int GRAVITY = -10;
    private static final int MOVEMENT = 100;
    
    private Texture texture;
    private Vector3 position;
    
    public Bird(int x, int y){
        texture = new Texture("bird.png");
        //define position
        position = new Vector3(x, y, 0);
    }
    
    public void update(float delta){
        position.add(0, GRAVITY, 0);
    }
    
    public void dispose(){
        texture.dispose();
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
}
