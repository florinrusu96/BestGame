package com.best.bestgame;

/**
 *  This Timer class is intended to create a Timer that runs 
 * simultaneously with a game and end that game instance when it runs out.
 * @author mehai
 */
public class Timer {
    
    private float target;
    private final float quarterTarget;
    
    /**
     * 
     * @param target time in seconds 
     * @param path path to Texture
     */
    public Timer(float target){
        this.target = target;
        quarterTarget = target / 4;
    }
    
    /**
     * It substracts delta from target and returns false if the target is negative
     * (there's no more time left aka timer reached 0) and true if it updated succesfully
     * @param delta delta-time
     * @return boolean weather there's time left or not
     */
    public boolean update(float delta){
        if(target > 0){
            target -= delta;
        }
        return target > 0;
    }
    
    /**
     * 
     * @return seconds remaining
     */
    public int getSeconds(){
        return Math.round(target);
    }
}
