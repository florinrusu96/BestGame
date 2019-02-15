package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;
import com.mygdx.game.handlers.B2DVARS;

public class HUD {

	private Player player;
	
	private TextureRegion container;
	private TextureRegion[] blocks;
	private TextureRegion crystal;
	private TextureRegion[] font;
	
	public HUD(Player player) {
		this.player = player;
		
		Texture texture = Game.assets.getTexture("HUD");
		
		container = new TextureRegion(texture, 0, 0, 32, 32);
		
		blocks = new TextureRegion[3];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16);
		}
		
		crystal = new TextureRegion(texture, 80, 0, 16, 16);
		
		font = new TextureRegion[11];
		for (int i = 0; i < 6; i++) {
			font[i] = new TextureRegion(texture, 32 + i * 9, 16, 9, 9);
		}
		for (int i = 0; i < 5; i++) {
			font[i + 6] = new TextureRegion(texture, 32 + i * 9, 25, 9, 9);
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		
		sb.draw(container, 32, 200);
		
		short maskBits = player.getBody().getFixtureList().get(1).getFilterData().maskBits;
		if (maskBits == B2DVARS.BIT_RED) sb.draw(blocks[0], 40, 208);
		else if (maskBits == B2DVARS.BIT_GREEN) sb.draw(blocks[1], 40, 208);
		else if (maskBits == B2DVARS.BIT_BLUE) sb.draw(blocks[2], 40, 208);
		
		sb.draw(crystal, 100,  208);
		
		drawString(sb, player.getNumCrystals() + " / " + player.getTotalCrystals(), 132, 211);
		
		sb.end();
	}
	
	private void drawString(SpriteBatch sb, String s, float x, float y) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '/') c = 10;
			else if (c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(font[c], x + i * 9, y);
		}
	}
}




















