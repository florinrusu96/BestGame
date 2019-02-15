package com.mygdx.game.Block_Bunny;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.Contents;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyInput;
import com.mygdx.game.MyInputProcessor;

public class Game extends ApplicationAdapter {

    public static final String TITLE = "Block Bunny";
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;

    public static Contents assets;

    public static final float TIMESTEP = 1 / 60f;
    private float delta;

    private SpriteBatch sb;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private GameStateManager gsm;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new MyInputProcessor());

        assets = new Contents();

        assets.loadTexture("bunny", "images/bunny.png");
        assets.loadTexture("crystal", "images/crystal.png");
        assets.loadTexture("HUD", "images/hud.png");
        assets.loadTexture("bgs", "images/bgs.png");

        assets.loadSound("changeblock", "sfx/changeblock.wav");
        assets.loadSound("crystal", "sfx/crystal.wav");
        assets.loadSound("jump", "sfx/jump.wav");

        assets.loadMusic("music", "music/bbsong.ogg");

        sb = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, WIDTH, HEIGHT);

        gsm = new GameStateManager(this);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        delta += Gdx.graphics.getDeltaTime();
        while (delta >= TIMESTEP) {
            delta -= TIMESTEP;
            gsm.update(TIMESTEP);
            gsm.render();
            MyInput.update();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        gsm.setState(GameStateManager.PLAY);
    }

    @Override
    public void dispose() {
    }

    public SpriteBatch getSpriteBatch() { return sb; }
    public OrthographicCamera getCamera() { return camera; }
    public OrthographicCamera getHudCamera() { return hudCamera; }

}
