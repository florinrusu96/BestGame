package com.best.bestgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import java.util.Random;
import com.best.bestgame.flappybird.PlayScreen;
import com.best.bestgame.menus.InterGameScreen;

/**
 *  Singleton-Factory class designed to return a new Random Game Screen
 * @author mehai
 */
public class Factory {
    private static Factory factory = null;
    private Random random;
    private final BestGame game;
    
    private Factory(final BestGame game){ 
        random = new Random();
        this.game = game;
    }
    
    public Factory getInstance(BestGame game){
        if(factory == null){
            factory = new Factory(game);
        }
        return factory;
    }
    
    public Screen factory(){
        int index = random.nextInt(2);
        switch(index){
            case 0:
                return new PlayScreen(game);
            case 1:
                return new com.best.bestgame.maze.PlayScreen(game);
            default:
                return new InterGameScreen(game);
        }
    }
    
}
