package com.bestgame.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.bestgame.menus.BestGameMenus.MENU_HEIGHT;
import static com.bestgame.menus.BestGameMenus.MENU_WIDTH;

/**
 *
 * @author mehai
 */
public class InterGameScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table livesTable;
    private Table table;
    private Texture texture;
    
    public InterGameScreen(final BestGameMenus game) {
        //SETUP STAGE
        this.game = game;
        stage = new Stage(new StretchViewport(MENU_WIDTH, MENU_HEIGHT), game.batch);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //CHANGE SCREEN -> NEW GAME
                ((Game)Gdx.app.getApplicationListener()).setScreen(new EndGameScreen(game));
            }
        });
        
        //SETUP TABLES
        table = new Table();
        table.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT/2);
        table.align(Align.center);
        
        livesTable = new Table();
        livesTable.setBounds(0, MENU_HEIGHT / 2, MENU_WIDTH, MENU_HEIGHT / 2);
        livesTable.align(Align.center);
        
        //DRAW LIVES IMAGES
        texture = new Texture("robot.png");
        for(int i = 0; i < game.lifePoints; i++){
            Image robotLife = new Image(texture);
            livesTable.add(robotLife).width(robotLife.getWidth()/4).height(robotLife.getHeight()/4);
            livesTable.getCell(robotLife).padTop(MENU_HEIGHT / 5);    
        }
        
        //SCORE
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = new Color(0.196f, 0.075f, 0.145f, 1);
        
        Label scoreLabel = new Label("SCORE: " + game.score, labelStyle);
        scoreLabel.setFontScale(2);
        table.add(scoreLabel);
        table.getCell(scoreLabel).padBottom(MENU_HEIGHT / 10);
        table.row();
        
        //CONTINUE LABEL 
        Label pressLabel = new Label("Press anywhere to continue...", labelStyle);
        pressLabel.setFontScale(2);
        table.add(pressLabel);
        
        //ADD EVERYTHING TO THE STAGE
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
        texture.dispose();
        stage.dispose();
    }

}
