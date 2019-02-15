package com.mygdx.game.Block_Bunny.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;

public class Background {

    private TextureRegion image;
    private OrthographicCamera camera;
    private float scale;

    private float x;
    private float y;
    private int drawX;
    private int drawY;

    private float dx;
    private float dy;

    public Background(TextureRegion image, OrthographicCamera camera, float scale) {
        this.image = image;
        this.camera = camera;
        this.scale = scale;
        drawX = Game.WIDTH / image.getRegionWidth() + 1;
        drawY = Game.HEIGHT / image.getRegionHeight() + 1;
    }

    public void setVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update(float ts) {
        x += (dx * scale) * ts;
        y += (dy * scale) * ts;
    }

    public void render(SpriteBatch sb) {
        float x = ((this.x + camera.viewportWidth / 2 - camera.position.x) * scale) % image.getRegionWidth();
        float y = ((this.y + camera.viewportHeight / 2 - camera.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        int colXOffset = x > 0 ? -1 : 0;
        int rowYOffset = y > 0 ? -1 : 0;

        for (int rowY = 0; rowY < drawY; rowY++) {
            for (int colX = 0; colX < drawX; colX++) {
                sb.draw(image, x + (colX + colXOffset) * image.getRegionWidth(), y + (rowY + rowYOffset) * image.getRegionHeight());
            }
        }

        sb.end();
    }
}

