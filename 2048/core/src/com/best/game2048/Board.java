package com.best.game2048;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public class Board extends Tile {

    public Tile[][] gameBoard;
    public Texture boardTexture;
    private Random rand = new Random();
    public int score = 0;

    Random rnd;

    public Board() {                        //creates board with two randomly placed tiles

        gameBoard = new Tile[4][4];
        boardTexture = new Texture ("Table1.png");

        for (int i = 0; i < 4; ++i) {

            for (int j = 0; j < 4; ++j) {

                gameBoard[i][j] = null;
            }
        }

        int x1 = rand.nextInt(3);
        int y1 = rand.nextInt(3);
        int x2 = rand.nextInt(3);
        int y2;

        do {

            y2 = rand.nextInt(3);
        } while(y2 == y1);

        gameBoard[x1][y1] = new Tile(2,x1,y1);
        gameBoard[x2][y2] = new Tile(4,x2,y2);
        //gameBoard[3][2] = new Tile(2,3,2);
    }

    public void gameActionRight() {

        for (int j = 0; j < 4; ++j) {

            for (int i = 3; i > 0; --i) {

                if (gameBoard[i][j] != null) {

                    loop:

                    for (int k = i - 1; k >= 0; --k) {

                        if (gameBoard[k][j] != null && (gameBoard[i][j].getTileValue() == gameBoard[k][j].getTileValue())) {

                            gameBoard[i][j] = new Tile(gameBoard[i][j], gameBoard[k][j]);
                            gameBoard[k][j] = null;
                            score += gameBoard[i][j].getTileValue();

                            break loop;
                        }

                        else if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() != gameBoard[i][k].getTileValue())) {

                            break loop;
                        }
                    }
                }
            }
        }

        for (int j = 0; j < 4; ++j) {

            for (int i = 3; i > 0; --i) {

                if (gameBoard[i][j] == null) {

                    for(int k = i - 1; k >= 0; --k) {

                        if (gameBoard[k][j] != null) {

                            gameBoard[i][j] = new Tile(gameBoard[k][j]);
                            gameBoard[i][j].setX(i);
                            gameBoard[k][j] = null;
                        }
                    }
                }
            }
        }

        int x, y, z;

        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);

        } while(gameBoard[x][y] != null);

        z = rand.nextInt(3);

        if (z == 0) {

            z = 4;
        }

        else {

            z = 2;
        }

        gameBoard[x][y] = new Tile(z,x,y);
    }

    public void gameActionUp() { //aici ascultam manele here i was listening manele

        for (int i = 0; i < 4; ++i) {

            for (int j = 3; j >= 1; --j) {

                if (gameBoard[i][j] != null) {

                    loop:

                    for (int k = j - 1; k >= 0; --k) {

                        if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() == gameBoard[i][k].getTileValue())) {

                            gameBoard[i][j] = new Tile(gameBoard[i][j], gameBoard[i][k]);
                            gameBoard[i][k] = null;
                            score += gameBoard[i][j].getTileValue();

                            break loop;
                        }

                        else if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() != gameBoard[i][k].getTileValue())) {

                            break loop;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; ++i ) {

            for (int j = 3; j >= 1; --j) {

                if (gameBoard[i][j] == null) {

                    for(int k = j - 1; k >= 0; --k) {

                        if (gameBoard[i][k] != null) {

                            gameBoard[i][j] = new Tile(gameBoard[i][k]);
                            gameBoard[i][j].setY(j);
                            gameBoard[i][k] = null;
                        }
                    }
                }
            }
        }

        int x, y, z;

        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);

        } while(gameBoard[x][y] != null);

        z = rand.nextInt(3);

        if (z == 0) {

            z = 4;
        }

        else {

            z = 2;
        }

        gameBoard[x][y] = new Tile(z,x,y);
    }

    public void gameActionDown() {

        for (int i = 0; i < 4; ++i) {

            for (int j = 0; j < 3; ++j) {

                if (gameBoard[i][j] != null) {

                    loop:

                    for (int k = j + 1; k < 4; ++k) {

                        if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() == gameBoard[i][k].getTileValue())) {

                            gameBoard[i][j] = new Tile(gameBoard[i][j], gameBoard[i][k]);
                            gameBoard[i][k] = null;
                            score += gameBoard[i][j].getTileValue();

                            break loop;
                        }

                        else if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() != gameBoard[i][k].getTileValue())) {

                            break loop;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; ++i ) {

            for (int j = 0; j < 3; ++j) {

                /*if ((gameBoard[i][j] == null) && (gameBoard[i][j + 1] != null)) {

                    gameBoard[i][j] = new Tile(gameBoard[i][j + 1]);
                    gameBoard[i][j].setY(gameBoard[i][j + 1].getY() - 1);
                    gameBoard[i][j + 1] = null;
                }*/

                if (gameBoard[i][j] == null) {

                    for(int k = j + 1; k < 4; ++k) {

                        if (gameBoard[i][k] != null) {

                            gameBoard[i][j] = new Tile(gameBoard[i][k]);
                            gameBoard[i][j].setY(j);
                            gameBoard[i][k] = null;
                        }
                    }
                }
            }
        }

        int x, y, z;

        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);

        } while(gameBoard[x][y] != null);

        z = rand.nextInt(3);

        if (z == 0) {

            z = 4;
        }

        else {

            z = 2;
        }

        gameBoard[x][y] = new Tile(z,x,y);

    }

    public void gameActionLeft() {

        for (int j = 0; j < 4; ++j) {

            for (int i = 0; i < 3; ++i) {

                if (gameBoard[i][j] != null) {

                    loop:

                    for (int k = i + 1; k < 4; ++k) {

                        if (gameBoard[k][j] != null && (gameBoard[i][j].getTileValue() == gameBoard[k][j].getTileValue())) {

                            gameBoard[i][j] = new Tile(gameBoard[i][j], gameBoard[k][j]);
                            gameBoard[k][j] = null;
                            score += gameBoard[i][j].getTileValue();

                            break loop;
                        }

                        else if (gameBoard[i][k] != null && (gameBoard[i][j].getTileValue() != gameBoard[i][k].getTileValue())) {

                            break loop;
                        }
                    }
                }

            }
        }

        for (int j = 0; j < 4; ++j) {

            for (int i = 0; i < 3; ++i) {

//                if ((gameBoard[i][j] == null) && (gameBoard[i + 1][j] != null)){
//
//                    gameBoard[i][j] = new Tile(gameBoard[i + 1][j]);
//                    gameBoard[i][j].setX(gameBoard[i + 1][j].getX() - 1);
//                    gameBoard[i + 1][j] = null;
//                }

                if(gameBoard[i][j] == null) {

                    for(int k = i + 1; k < 4; ++k) {

                        if (gameBoard[k][j] != null) {

                            gameBoard[i][j] = new Tile(gameBoard[k][j]);
                            gameBoard[i][j].setX(i);
                            gameBoard[k][j] = null;
                        }
                    }
                }
            }
        }

        int x, y, z;

        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);

        } while(gameBoard[x][y] != null);

        z = rand.nextInt(3);

        if (z == 0) {

            z = 4;
        }

        else {

            z = 2;
        }

        gameBoard[x][y] = new Tile(z,x,y);
    }
}
