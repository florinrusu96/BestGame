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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.bestgame.menus.BestGameMenus.MENU_HEIGHT;
import static com.bestgame.menus.BestGameMenus.MENU_WIDTH;
import java.util.Random;

/**
 *  End Game Screen.
 * @author mehai
 */
public class EndGameScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table table;
    private Table btnTable;
    private Skin skin;
    private Texture texture;
    
    final private String[] messages = {
        "WOW! You are so good at it!\n",
        "Amazing skills!\n", 
        "What a player!\n", 
        "Can you code as well as you play this?\n", 
        "The world needs people like YOU!\n"};
    private String registerMessage = "Register for BESTEM!";
    
    public EndGameScreen(final BestGameMenus game) {
        //SETUP STAGE AND SKIN
        this.game = game;
        stage = new Stage(new StretchViewport(MENU_WIDTH, MENU_HEIGHT), game.batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        
        //SETUP TABLE
        table = new Table();
        table.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        table.align(Align.top);
        
        //SCORE LABEL AND LABEL STYLE
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("fonts/verdana32.fnt"));
        labelStyle.fontColor = Color.WHITE;
        
        Label scoreLabel = new Label("SCORE: " + game.score, labelStyle);
        table.add(scoreLabel);
        table.getCell(scoreLabel).padTop(MENU_HEIGHT / 5).padBottom(MENU_HEIGHT / 16);
        table.row();
        
        //MESSAGE LABEL
        labelStyle.font = new BitmapFont(Gdx.files.internal("fonts/verdana22.fnt"));
        Label msg = new Label(messages[new Random().nextInt(5)] + "Come register for BESTEM!", labelStyle);
        msg.setAlignment(Align.center);
        table.add(msg);
        table.getCell(msg).padBottom(MENU_HEIGHT / 8);
        table.row();
        
        //MIDDLE SCREEN IMAGE
        texture = new Texture("logov2.png");
        Image endGameImg = new Image(texture);
        table.add(endGameImg).width(MENU_WIDTH / 2).height(MENU_WIDTH * 6 / 18);
        table.getCell(endGameImg).padBottom(MENU_HEIGHT / 4);
        table.row();
        
        //BUTTONS TABLE AND BUTTONS
        btnTable = new Table(skin);
        btnTable.setBounds(MENU_WIDTH / 10, MENU_HEIGHT / 12, MENU_WIDTH * 4/5, MENU_HEIGHT / 16);
        btnTable.align(Align.center);
        
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.setColor(0.082f, 0.118f, 0.247f,1);
        backBtn.getLabel().setStyle(labelStyle);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //CHANGE SCREEN -> MAIN MENU SCREEN
                game.setScreen(new MainMenuScreen(game));
            }
        });
        btnTable.add(backBtn);
        btnTable.getCell(backBtn).align(Align.left);
        btnTable.getCell(backBtn).height(MENU_HEIGHT/16).width(MENU_WIDTH/4);
        btnTable.getCell(backBtn).spaceRight(MENU_WIDTH / 3);
        
        TextButton playBtn = new TextButton("Play again", skin);
        playBtn.setColor(0.082f, 0.118f, 0.247f,1);
        playBtn.getLabel().setStyle(labelStyle);
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //CHANGE SCREEN -> NEW GAME SCREEN
                game.setScreen(new InterGameScreen(game));
            }
        });
        btnTable.add(playBtn);
        btnTable.getCell(playBtn).align(Align.right);
        btnTable.getCell(playBtn).height(MENU_HEIGHT/16).width(MENU_WIDTH/4);
        
        //ADD TO STAGE
        //table.debug();
        //btnTable.debug();
        stage.addActor(table);
        stage.addActor(btnTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(0.5f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.196f, 0.075f, 0.145f, 1);
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
        skin.dispose();
        texture.dispose();
    }

}
