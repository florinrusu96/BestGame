package com.bestgame.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
    final private String[] messages = {
        "WOW! You are so good at it!\n",
        "Amazing skills!\n", 
        "What a player!\n", 
        "Can you code as well as you play this game?\n", 
        "The world needs people like YOU!\n"};
    private String registerMessage = "Register for BESTEM!";
    
    public EndGameScreen(final BestGameMenus game) {
        this.game = game;
        stage = new Stage(new ScreenViewport(), game.batch);
        
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.align(Align.top);
        
        //Score Label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = new Color(0.196f, 0.075f, 0.145f, 1);
        Label scoreLabel = new Label("SCORE: " + game.score, labelStyle);
        scoreLabel.setFontScale(3);
        table.add(scoreLabel);
        table.getCell(scoreLabel).padTop(Gdx.graphics.getHeight() / 5).padBottom(50);
        table.row();
        //Message Label
        Label msg = new Label(messages[new Random().nextInt(5)] + "Come register for BESTEM!", labelStyle);
        msg.setFontScale(1.5f);
        msg.setAlignment(Align.center);
        table.add(msg);
        table.getCell(msg).padBottom(20f);
        table.row();
        
        //Middle Screen Image
        Texture texture = new Texture("logov2.png");
        Image endGameImg = new Image(texture);
        table.add(endGameImg).width(Gdx.graphics.getWidth() / 2).height(Gdx.graphics.getWidth() * 6 / 18);
        table.getCell(endGameImg).padBottom(Gdx.graphics.getHeight() / 4);
        table.row();
        
        //Buttons
        
        btnTable = new Table(skin);
        btnTable.setBounds(10, 40, Gdx.graphics.getWidth() - 20, 50);
        btnTable.align(Align.center);
        
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });
        btnTable.add(backBtn);
        btnTable.getCell(backBtn).align(Align.left);
        btnTable.getCell(backBtn).height(50).width(120);
        btnTable.getCell(backBtn).spaceRight(Gdx.graphics.getWidth() / 3);
        
        TextButton playBtn = new TextButton("Play again", skin);
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });
        btnTable.add(playBtn);
        btnTable.getCell(playBtn).align(Align.right);
        btnTable.getCell(playBtn).height(50).width(120);

        
        
        //table.debug();
        //btnTable.debug();
        
        stage.addActor(table);
        stage.addActor(btnTable);
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
        skin.dispose();
    }

}
