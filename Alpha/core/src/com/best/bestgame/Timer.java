package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *  This Timer class is intended to create a Timer that runs 
 * simultaneously with a game and end that game instance when it runs out.
 * @author mehai
 */
public class Timer {
    
    private float target;
    private final float quarterTarget;
    private Image img;
    
    /**
     * 
     * @param target time in seconds 
     * @param path path to Texture
     */
    public Timer(float target, String path){
        this.target = target;
        quarterTarget = target / 4;
        img = new Image(new Texture(Gdx.files.internal(path)));
    }
    
    /**
     * It substracts delta from target and returns false if the target is negative
     * (there's no more time left aka timer reached 0) and true if it updated succesfully
     * @param delta delta-time
     * @return boolean weather there's time left or not
     */
    public boolean update(float delta){
        target -= delta;
        return target > 0;
    }
    
    /**
     * 
     * @return seconds remaining
     */
    public int getSeconds(){
        return Math.round(target);
    }
    
    public Image getImage(){
        return img;
    }
    
    public void setImage(Image img){
        this.img = img;
    }
    
    public void dispose(){
    }
}
