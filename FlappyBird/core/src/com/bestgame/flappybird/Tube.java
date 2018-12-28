package com.bestgame.flappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

/**
 *Represents the pair of TOP and BOTTOM tubes. Contains the textures, positions
 *and all information necessary for a pair of tubes.
 * @author mehai
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int SPACING = 100;
    private static final int MINVALUE = 100;
    private static final int DISTANCE = 200;
    
    private Texture topTube, bottomTube;
    private Vector3 topPosition, bottomPosition;
    private Rectangle topBounds, bottomBounds;

    Random rand;
    
    public Tube(int x, Texture topTube, Texture bottomTube) {
        //define textures
        this.topTube = topTube;
        this.bottomTube = bottomTube;
        //define a Random object
        rand = new Random();
        //define positions
        topPosition = new Vector3(x + DISTANCE, rand.nextInt(FLUCTUATION) + SPACING + MINVALUE, 0);
        bottomPosition = new Vector3(x + DISTANCE, topPosition.y - SPACING - bottomTube.getHeight(), 0);
        //define bounds
        topBounds = new Rectangle(topPosition.x, topPosition.y, topTube.getWidth(), topTube.getHeight());
        bottomBounds = new Rectangle(bottomPosition.x, bottomPosition.y, bottomTube.getWidth(), bottomTube.getHeight());
        
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector3 getTopPosition() {
        return topPosition;
    }


    public Vector3 getBottomPosition() {
        return bottomPosition;
    }
    
    
    public Rectangle getTopBounds() {
        return topBounds;
    }

    public Rectangle getBottomBounds() {
        return bottomBounds;
    }
    
    /**
     * Repositions current tube to the give x coordinate.
     * It doesn't use the exact same pair of tubes, calling the
     * Random object method again for a different fluctuation of
     * tubes.
     * @param x position where to move this tube
     */
    public void reposition(float x){
        topPosition.set(x + DISTANCE, rand.nextInt(FLUCTUATION) + SPACING + MINVALUE, 0);
        bottomPosition.set(x + DISTANCE, topPosition.y - SPACING - bottomTube.getHeight(), 0);
        //reposition bounds
        topBounds.setPosition(topPosition.x, topPosition.y);
        bottomBounds.setPosition(bottomPosition.x, bottomPosition.y);
    }
}
