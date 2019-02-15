package com.mygdx.game.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.Z) MyInput.setKey(MyInput.BUTTONZ, true);
		if (keycode == Keys.X) MyInput.setKey(MyInput.BUTTONX, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.Z) MyInput.setKey(MyInput.BUTTONZ, false);
		if (keycode == Keys.X) MyInput.setKey(MyInput.BUTTONX, false);
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		MyInput.x = screenX;
		MyInput.y = screenY;
		MyInput.down = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		MyInput.x = screenX;
		MyInput.y = screenY;
		MyInput.down = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		MyInput.x = screenX;
		MyInput.y = screenY;
		MyInput.down = true;
		return true;
	}
}
