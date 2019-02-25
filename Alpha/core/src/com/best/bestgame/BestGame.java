package com.best.bestgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.best.bestgame.menus.MainMenuScreen;
import com.best.bestgame.score.ScoreListener;

import java.util.ArrayList;
import java.util.List;

public class BestGame extends Game {
	public SpriteBatch batch;
        public int score;
        public int lifePoints;
        public Screen lastScreen;
        
        public static final int MENU_WIDTH = 480;
        public static final int MENU_HEIGHT = 800;
        public BitmapFont font;

	private GlyphLayout glyphLayoutHelper;

	private List<ScoreListener> scoreListenerList;

	public BestGame() {
		scoreListenerList = new ArrayList<ScoreListener>();
	}
	
	@Override
	public void create () {
				setupFont();
                lifePoints = 3;
		batch = new SpriteBatch();
                lastScreen = null;
                this.setScreen(new MainMenuScreen(this));
		glyphLayoutHelper = new GlyphLayout();
	}

	private void setupFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pacman/dameron.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		parameter.color = new Color(255 /255f, 255 / 255f, 1, 1);
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
                getScreen().dispose();
	}


	/**
	 * Deseneaza score si timerul. Presupune ca in batch se deseneaza deja (a fost apelata metoda de start)
	 */
	public void drawScoreAndTimerInfo(Camera camera, int score, int remainingSeconds) {
		String timerValue = "" + remainingSeconds;
		glyphLayoutHelper.setText(font, timerValue);
		int timerStartPoint = (int) glyphLayoutHelper.width;

		font.draw(batch, "Score: " + score, camera.position.x - camera.viewportWidth / 2 + 20, camera.viewportHeight - 20);
		font.draw(batch, timerValue, camera.position.x + camera.viewportWidth / 2 - timerStartPoint - 20, camera.viewportHeight - 20);

	}

	public void broadcastScore(int score) {
		for (ScoreListener listener : scoreListenerList) {
			listener.scoreChanged(score);
		}
	}

	public void registerScoreListener(ScoreListener listener) {
		scoreListenerList.add(listener);
	}

	public void unregisterScoreListener(ScoreListener listener) {
		scoreListenerList.remove(listener);
	}
}
