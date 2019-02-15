package com.mygdx.game.Block_Bunny.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;
import com.mygdx.game.handlers.GameStateManager;

public abstract class GameState {

    protected Game game;
    protected GameStateManager gsm;
    protected SpriteBatch sb;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCamera;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        camera = game.getCamera();
        hudCamera = game.getHudCamera();
    }

    public abstract void handleInput();
    public abstract void update(float ts);
    public abstract void render();
    public abstract void dispose();
}
