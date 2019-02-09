package com.best.game2048;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;

import java.util.Random;

public class PlayScreen implements Screen {

    BitmapFont font;
    private final game2048 game;
    private Texture bgTexture;
    private OrthographicCamera camera;
    private Board gameBoard;

    public PlayScreen(game2048 game){

        font = new BitmapFont();
        this.game = game;
        camera = new OrthographicCamera(480, 800);
        camera.setToOrtho(false, 480, 800);

        bgTexture = new Texture("background1_dick-01.jpg");
        gameBoard = new Board();
    }

    @Override
    public void show() {
    }

    public void handleInput(){

        //Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));

//        int x = 0, y = 0;
//
//        while(Gdx.input.isTouched()) {
//
//            x = Gdx.input.getDeltaX();
//            y = Gdx.input.getDeltaY();
//        }
//
//        if (x != 0 || y != 0) {
//
//            if ((x > y) && (x > 0)) {
//
//                gameBoard.gameActionRight();
//            }
//
//            if ((x > y) && (x < 0)) {
//
//                gameBoard.gameActionUp();
//            }
//
//            if ((x < y) && (y > 0)) {
//
//                gameBoard.gameActionDown();
//            }
//
//            if ((x < y) && (y < 0)) {
//
//                gameBoard.gameActionLeft();
//            }
//        }

        if(Gdx.input.justTouched()) { //Test

            gameBoard.gameActionRight();
        }
    }

    public void update() {

        handleInput();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bgTexture, 0, 0, 720, 1280);
        game.batch.draw(gameBoard.boardTexture,45,260,630,630);
        //font.draw(game.batch, "Hello", 0,0);

        for (int i = 0; i < 4; ++i) {

            for (int j = 0; j < 4; ++j) {

                if (gameBoard.gameBoard[i][j] != null) {


                    game.batch.draw(gameBoard.gameBoard[i][j].getTexture(), gameBoard.gameBoard[i][j].getLocationX(),
                            gameBoard.gameBoard[i][j].getLocationY(),140,140);
                }
            }
        }

        ///Test de aliniere lmao ma cac pe mine de ras AHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHA

        /*game.batch.draw(gameBoard.gameBoard[0][0].getTexture(), gameBoard.gameBoard[0][0].getLocationX(),
                    gameBoard.gameBoard[0][0].getLocationY(),140,140);
        game.batch.draw(gameBoard.gameBoard[1][1].getTexture(), gameBoard.gameBoard[1][1].getLocationX(),
                gameBoard.gameBoard[1][1].getLocationY(),140,140);
        game.batch.draw(gameBoard.gameBoard[2][2].getTexture(), gameBoard.gameBoard[2][2].getLocationX(),
                gameBoard.gameBoard[2][2].getLocationY(),140,140);
        game.batch.draw(gameBoard.gameBoard[3][3].getTexture(), gameBoard.gameBoard[3][3].getLocationX(),
                gameBoard.gameBoard[3][3].getLocationY(),140,140);*/

        game.batch.end();

        update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
