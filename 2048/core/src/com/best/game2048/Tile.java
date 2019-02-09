package com.best.game2048;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class Tile {

    private Texture tileTexture;
    private int tileValue;
    private int x;
    private int y;


    public Tile () {

        tileTexture = new Texture("Tile1.png");
    }

    public Tile (Tile obj1, Tile obj2) { //ctor din 2 alte Tile-uri. pastreaza pozitia primului obiect

        this.x = obj1.x;
        this.y = obj1.y;
        this.tileValue = obj1.tileValue + obj2.tileValue;

        switch (this.tileValue) {

            case 2:

                this.tileTexture = new Texture("Tile2.png");
                break;

            case 4:

                this.tileTexture = new Texture("Tile4.png");
                break;

            case 8:

                this.tileTexture = new Texture("Tile8.png");
                break;

            case 16:

                this.tileTexture = new Texture("Tile16.png");
                break;

            case 32:

                this.tileTexture = new Texture("Tile32.png");
                break;

            case 64:

                this.tileTexture = new Texture("Tile64.png");
                break;

            case 128:

                this.tileTexture = new Texture("Tile128.png");
                break;

            case 256:

                this.tileTexture = new Texture("Tile256.png");
                break;

            case 512:

                this.tileTexture = new Texture("Tile512.png");
                break;

            case 1024:

                this.tileTexture = new Texture("Tile1024.png");
                break;

            case 2048:

                this.tileTexture = new Texture("Tile2048.png");
                break;
        }
    }

    public Tile (Tile obj) { //ctor prin copiere. aproape

        this.x = obj.x;
        this.y = obj.y;
        this.tileValue = obj.tileValue;
        switch (this.tileValue) {

            case 2:

                this.tileTexture = new Texture("Tile2.png");
                break;

            case 4:

                this.tileTexture = new Texture("Tile4.png");
                break;

            case 8:

                this.tileTexture = new Texture("Tile8.png");
                break;

            case 16:

                this.tileTexture = new Texture("Tile16.png");
                break;

            case 32:

                this.tileTexture = new Texture("Tile32.png");
                break;

            case 64:

                this.tileTexture = new Texture("Tile64.png");
                break;

            case 128:

                this.tileTexture = new Texture("Tile128.png");
                break;

            case 256:

                this.tileTexture = new Texture("Tile256.png");
                break;

            case 512:

                this.tileTexture = new Texture("Tile512.png");
                break;

            case 1024:

                this.tileTexture = new Texture("Tile1024.png");
                break;

            case 2048:

                this.tileTexture = new Texture("Tile2048.png");
                break;
        }
    }

    public Tile (int val, int x, int y) { //ctor prin valori

        this.tileValue = val;
        this.x = x;
        this.y = y;
        switch (this.tileValue) {

            case 2:

                this.tileTexture = new Texture("Tile2.png");
                break;

            case 4:

                this.tileTexture = new Texture("Tile4.png");
                break;

            case 8:

                this.tileTexture = new Texture("Tile8.png");
                break;

            case 16:

                this.tileTexture = new Texture("Tile16.png");
                break;

            case 32:

                this.tileTexture = new Texture("Tile32.png");
                break;

            case 64:

                this.tileTexture = new Texture("Tile64.png");
                break;

            case 128:

                this.tileTexture = new Texture("Tile128.png");
                break;

            case 256:

                this.tileTexture = new Texture("Tile256.png");
                break;

            case 512:

                this.tileTexture = new Texture("Tile512.png");
                break;

            case 1024:

                this.tileTexture = new Texture("Tile1024.png");
                break;

            case 2048:

                this.tileTexture = new Texture("Tile2048.png");
                break;
        }
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getLocationX() {

        return (45 + 14 + this.x * (140 + 14));
    }

    public int getLocationY() {

        return (260 + 14 + this.y * (140 + 14));
    }

    public int getTileValue() {

        return tileValue;
    }

    public Texture getTexture() {

        return this.tileTexture;
    }
}
