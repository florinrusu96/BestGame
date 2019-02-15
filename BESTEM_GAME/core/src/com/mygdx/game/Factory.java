package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import java.util.Random;

/**
 *  Singleton-Factory class designed to return a new Random Game Screen
 * @author mehai
 */
public class Factory {
    private static Factory factory = null;
    private Random random;
    private Game game;
    
    private Factory(Game game){ 
        random = new Random();
        this.game = game;
    }
    
    public Factory getInstance(Game game){
        if(factory == null){
            factory = new Factory(game);
        }
        return factory;
    }
    
    public Screen factory(){
        int index = random.nextInt(4);
        switch(index){
            case 0:
                return new flappybird.PlayScreen(game);
                break;
            case 1:
                return new maze.PlayScreen(game);
                break;
            case 2:
                return new pacman.PlayScreen(game);
                break;
            case 3:
                return new bunny.PlayScreen(game);
                break;
            default:
                return new InterGameScreen(game);
        }
    }
    
}
