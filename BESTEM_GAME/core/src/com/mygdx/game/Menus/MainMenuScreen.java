package com.mygdx.game.Menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *  Main menu screen.
 * @author mehai
 */
public class MainMenuScreen implements Screen {

    final private Game game;
    private Stage stage;
    private Table table;

    public MainMenuScreen(final BestGameMenus game) {
        //SETUP MAIN OBJECTS
        this.game = game;
        stage = new Stage(new ScreenViewport(), game.batch);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new EndGameScreen(game));
            }
        });
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.align(Align.top);

        //LOGO BESTEM - title
        Texture texture = new Texture("logov2.png");
        Image logo = new Image(texture);
        table.add(logo).width(Gdx.graphics.getWidth() / 2).height(Gdx.graphics.getWidth() * 6 / 18);
        table.getCell(logo).padBottom(Gdx.graphics.getHeight() / 3).padTop(Gdx.graphics.getHeight() / 4);

        table.row();

        //Label Play info
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = new Color(0.196f, 0.075f, 0.145f, 1);

        Label pressLabel = new Label("Press anywhere to start ...", labelStyle);
        pressLabel.setFontScale(2 * Gdx.graphics.getWidth() / 480);
        table.add(pressLabel);
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
        stage.dispose();
    }

}
