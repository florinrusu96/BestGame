package com.bestgame.flappybird;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    
    private Texture topTube, bottomTube;
    private Vector3 topPosition, bottomPosition;
    Random rand;
    
    public Tube(int x) {
        //define textures
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        //define a Random object
        rand = new Random();
        //define positions
        topPosition = new Vector3(x, rand.nextInt(FLUCTUATION) + SPACING + MINVALUE, 0);
        bottomPosition = new Vector3(x, topPosition.y - SPACING - bottomTube.getHeight(), 0);
        
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
    
    public void reposition(float x){
        topPosition.set(x, rand.nextInt(FLUCTUATION) + SPACING + MINVALUE, 0);
        bottomPosition.set(x, topPosition.y - SPACING - bottomTube.getHeight(), 0);
    }
    
    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
