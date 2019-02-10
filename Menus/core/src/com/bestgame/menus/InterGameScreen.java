package com.bestgame.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author mehai
 */
public class InterGameScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table livesTable;
    private Table table;
    
    private float scaleWidth;
    private float scaleHeight;
    
    public InterGameScreen(final BestGameMenus game) {
        //SETUP MAIN OBJECTS
        this.game = game;
        
        stage = new Stage(new ScreenViewport(), game.batch);
        ((OrthographicCamera)stage.getCamera()).setToOrtho(false);
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        
        scaleWidth =  stage.getWidth() / 480;
        scaleHeight =  stage.getHeight() / 800;
        
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //CHANGE SCREEN -> NEW GAME
                ((Game)Gdx.app.getApplicationListener()).setScreen(new EndGameScreen(game));
            }
        });
        
        //SETUP TABLES
        table = new Table();
        table.setBounds(0, 0, stage.getWidth(), stage.getHeight()/2);
        table.align(Align.center);
        
        
        livesTable = new Table();
        livesTable.setBounds(0, stage.getHeight() / 2, stage.getWidth(), stage.getHeight() / 2);
        livesTable.align(Align.center);
        
        //DRAW LIVES IMAGES
        Texture texture = new Texture("robot.png");
        for(int i = 0; i < game.lifePoints; i++){
            Image robotLife = new Image(texture);
            livesTable.add(robotLife).width(robotLife.getWidth()*(scaleWidth/4)).height(robotLife.getHeight()*(scaleHeight/4));
            livesTable.getCell(robotLife).padTop(stage.getHeight() / 5);    
        }
        
        //SCORE
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = new Color(0.196f, 0.075f, 0.145f, 1);
        
        Label scoreLabel = new Label("SCORE: " + game.score, labelStyle);
        scoreLabel.setFontScale(2 * scaleWidth);
        table.add(scoreLabel);
        table.getCell(scoreLabel).padBottom(stage.getHeight() / 10);
        table.row();
        
        //Label Play info     
        Label pressLabel = new Label("Press anywhere to continue...", labelStyle);
        pressLabel.setFontScale(2 * scaleWidth);
        table.add(pressLabel);
        
        //table.debug();
        //livesTable.debug();
        
        stage.addActor(table);
        stage.addActor(livesTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
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
        stage.dispose();
    }

}
