package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.handlers.B2DVARS;

public class B2DSprite {

	protected Body body;
	protected Animation animation;
	protected float elapsedTime;
	protected float width, height;

	public B2DSprite(Body body) {
		this.body = body;
	}

	public void setAnimation(TextureRegion[] sprites, float delay) {
		animation = new Animation(delay, sprites);
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();
		sb.draw((Texture) animation.getKeyFrame(elapsedTime, true),(Float)(body.getPosition().x * B2DVARS.PPM - width / 2), (Float) (body.getPosition().y * B2DVARS.PPM - height / 2));
		sb.end();
	}

	public Body getBody() { return body; }
	public Vector2 getPosition() { return body.getPosition(); }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
}