package com.best.bestgame.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.best.bestgame.BestGame;
import static com.best.bestgame.BestGame.MENU_HEIGHT;
import static com.best.bestgame.BestGame.MENU_WIDTH;

/**
 *  Main menu screen.
 * @author mehai
 */
public class MainMenuScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table table;
    private Texture textureBestem;
    private Texture textureBest;
    
    public MainMenuScreen(final BestGame game) {
        //SETUP STAGE
        this.game = game;
        stage = new Stage(new StretchViewport(MENU_WIDTH, MENU_HEIGHT), game.batch);
        stage.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               //CHANGE SCREEN -> NEW GAME SCREEN
               stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                   @Override
                   public void run() {
                       game.setScreen(new InterGameScreen(game));
                       dispose();
                   }
               })));
               return true;
           }
        });
        
        //SETUP TABLE
        table = new Table();
        table.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        table.align(Align.top);
        
        //LOGO BESTEM - title
        textureBestem = new Texture("menus/BESTEM_logo_alb.png");
        Image logo = new Image(textureBestem);
        table.add(logo).width(textureBestem.getWidth() / 2).height(textureBestem.getHeight() / 2);
        table.getCell(logo).padBottom(MENU_HEIGHT / 5).padTop(MENU_HEIGHT / 6);
        table.row();
        
        //PLAY LABEL
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("menus/fonts/verdana25.fnt"));
        labelStyle.fontColor = Color.WHITE;
                
        Label pressLabel = new Label("Tap anywhere to start", labelStyle);
        table.add(pressLabel);
        table.getCell(pressLabel).padBottom(MENU_HEIGHT / 8);
        table.row();
        
        //LOGO BEST
        textureBest = new Texture("menus/BEST_Bucharest.png");
        Image logoBest = new Image(textureBest);
        table.add(logoBest).width(textureBest.getWidth() / 5).height(textureBest.getHeight() / 5);
        
        //ADD TO STAGE
        table.debug();
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
        textureBestem.dispose();
        textureBest.dispose();
        stage.dispose();
    }

}
