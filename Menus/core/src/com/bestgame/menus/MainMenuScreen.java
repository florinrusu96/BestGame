package com.bestgame.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.bestgame.menus.BestGameMenus.MENU_HEIGHT;
import static com.bestgame.menus.BestGameMenus.MENU_WIDTH;

/**
 *  Main menu screen.
 * @author mehai
 */
public class MainMenuScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table table;
    private Texture texture;
    
    public MainMenuScreen(final BestGameMenus game) {
        //SETUP STAGE
        this.game = game;
        stage = new Stage(new StretchViewport(MENU_WIDTH, MENU_HEIGHT), game.batch);
        stage.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               //CHANGE SCREEN -> NEW GAME SCREEN
               game.setScreen(new EndGameScreen(game));
               return true;
           }
        });
        
        //SETUP TABLE
        table = new Table();
        table.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        table.align(Align.top);
        
        //LOGO BESTEM - title
        texture = new Texture("logov2.png");
        Image logo = new Image(texture);
        table.add(logo).width(MENU_WIDTH / 2).height(MENU_WIDTH * 6 / 18);
        table.getCell(logo).padBottom(MENU_HEIGHT / 3).padTop(MENU_HEIGHT / 4);
        table.row();
        
        //PLAY LABEL
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("fonts/verdana25.fnt"));
        labelStyle.fontColor = new Color(0.196f, 0.075f, 0.145f, 1);
                
        Label pressLabel = new Label("Press anywhere to start", labelStyle);
        table.add(pressLabel);
        
        //ADD TO STAGE
        stage.addActor(table);
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
