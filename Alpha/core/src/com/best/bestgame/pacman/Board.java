package com.best.bestgame.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.best.bestgame.BestGame;
import com.best.bestgame.menus.EndGameScreen;
import com.best.bestgame.menus.InterGameScreen;

import com.best.bestgame.Timer;

public class Board implements Screen {

    private final BestGame game;
    private final OrthographicCamera camera;

    private final Color dotColor = new Color(192 / 255f, 192 / 255f, 0, 1);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = BLOCK_SIZE * N_BLOCKS;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
    private final int MAX_GHOSTS = 12;

    private final double ASPECT_RATIO = Gdx.graphics.getWidth() / SCREEN_SIZE;

    private final int PACMAN_SPEED = 6;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 6;
    private int pacsLeft, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private Texture ghost;
    private Texture pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Texture pacman3up, pacman3down, pacman3left, pacman3right;
    private Texture pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    private ShapeRenderer mazeShapeRenderer;

    private final short levelData[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;

    private final int startX, startY;
    BitmapFont font;

    public Board(final BestGame game) {
        this.game = game;
        this.timer = new Timer(30);
        
        game.batch.getProjectionMatrix().setToOrtho2D(0, 0,
                Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        screenData = new short[levelData.length];
        loadImages();
        initGame();
        startX = 0;
        startY = SCREEN_SIZE / 2;
        inGame = true;

        setupFont();
    }

    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pacman/dameron.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = new Color(96 /255f, 128 / 255f, 1, 1);
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }

    public void handleInput() {

    }

    private void initVariables() {
        mazeColor = new Color(5 / 255f, 100 / 255f, 5 / 255f, 1);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame() {

        if (dying) {

            death();

        } else {

            movePacman();
            drawPacman();
            moveGhosts();
            checkMaze();
        }
    }

    private void showIntroScreen() {
//
//        g2d.setColor(new Color(0, 32 / 255f, 48 / 255f, 1));
//        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
//        g2d.setColor(Color.WHITE);
//        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
//
//        String s = "Press s to start.";
//        Font small = new Font("Helvetica", Font.BOLD, 14);
//
//        g2d.setColor(Color.white);
//        g2d.setFont(small);
//        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore() {
        game.drawScoreAndTimerInfo(camera, score, timer.getSeconds());

        int i;
        for (i = 0; i < pacsLeft; i++) {
            game.batch.draw(pacman3left,
                    (int) (ASPECT_RATIO * (i * pacman3left.getWidth() + 8)),
                    (int) (Gdx.graphics.getHeight() - ASPECT_RATIO * (pacman3left.getHeight() + 15)),
                    (int) (pacman3left.getWidth() * ASPECT_RATIO),
                    (int) (pacman3left.getHeight() * ASPECT_RATIO));
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

        pacsLeft--;
        score -= 10;
        if (pacsLeft == 0) {
            /*CHANGE SCREEN HERE  -> GAME LOST
            inGame = false;
            initLevel();*/
            game.lastScreen = this;
            game.lifePoints--;
            game.score += this.score;
            this.dispose();
            if(game.lifePoints != 0){
                game.setScreen(new InterGameScreen(game));
            }else{
                game.setScreen(new EndGameScreen(game));
            }
        }

        continueLevel();
    }

    private void moveGhosts() {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void drawGhost(int x, int y) {

        game.batch.draw(ghost, (int) (ASPECT_RATIO * (startX + x)),
                (int) (ASPECT_RATIO * (startY + y)),
                (int) (ASPECT_RATIO * ghost.getWidth()),
                (int) (ASPECT_RATIO * ghost.getHeight()));
    }

    private void movePacman() {

        int pos;
        short ch;

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    private void drawPacman() {

        if (view_dx == -1) {
            drawPacnanLeft();
        } else if (view_dx == 1) {
            drawPacmanRight();
        } else if (view_dy == -1) {
            drawPacmanUp();
        } else {
            drawPacmanDown();
        }
    }

    private void drawPacmanUp() {
        Texture texture;
        switch (pacmanAnimPos) {
            case 1:
                texture = pacman2up;
                break;
            case 2:
                texture = pacman3up;
                break;
            case 3:
                texture = pacman4up;
                break;
            default:
                texture = pacman1;
                break;
        }
        game.batch.draw(texture, (int) (ASPECT_RATIO * (startX + pacman_x + 1)),
                (int) (ASPECT_RATIO * (startY + pacman_y + 1)),
                (int) (texture.getWidth() * ASPECT_RATIO),
                (int) (texture.getHeight() * ASPECT_RATIO));
    }

    private void drawPacmanDown() {
        Texture texture;
        switch (pacmanAnimPos) {
            case 1:
                texture = pacman2down;
                break;
            case 2:
                texture = pacman3down;
                break;
            case 3:
                texture = pacman4down;
                break;
            default:
                texture = pacman1;
                break;
        }
        game.batch.draw(texture, (int) (ASPECT_RATIO * (startX + pacman_x + 1)),
                (int) (ASPECT_RATIO * (startY + pacman_y + 1)),
                (int) (texture.getWidth() * ASPECT_RATIO),
                (int) (texture.getHeight() * ASPECT_RATIO));
    }

    private void drawPacnanLeft() {
        Texture texture;
        switch (pacmanAnimPos) {
            case 1:
                texture = pacman2left;
                break;
            case 2:
                texture = pacman3left;
                break;
            case 3:
                texture = pacman4left;
                break;
            default:
                texture = pacman1;
                break;
        }
        game.batch.draw(texture, (int) (ASPECT_RATIO * (startX + pacman_x + 1)),
                (int) (ASPECT_RATIO * (startY + pacman_y + 1)),
                (int) (texture.getWidth() * ASPECT_RATIO),
                (int) (texture.getHeight() * ASPECT_RATIO));
    }

    private void drawPacmanRight() {
        Texture texture;
        switch (pacmanAnimPos) {
            case 1:
                texture = pacman2right;
                break;
            case 2:
                texture = pacman3right;
                break;
            case 3:
                texture = pacman4right;
                break;
            default:
                texture = pacman1;
                break;
        }
        game.batch.draw(texture, (int) (ASPECT_RATIO * (startX + pacman_x + 1)),
                (int) (ASPECT_RATIO * (startY + pacman_y + 1)),
                (int) (texture.getWidth() * ASPECT_RATIO),
                (int) (texture.getHeight() * ASPECT_RATIO));
    }

    private void drawMaze() {
        if(mazeShapeRenderer != null) {
            mazeShapeRenderer.dispose();
        }
        mazeShapeRenderer = new ShapeRenderer();
        mazeShapeRenderer.setProjectionMatrix(camera.combined);
        mazeShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        mazeShapeRenderer.setColor(Color.BLUE);
        short i = 0;
        int x, y;

        mazeShapeRenderer.rect(0, 0, 10, 10);
        for (y = startY; y < startY + SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = startX; x < startX + SCREEN_SIZE; x += BLOCK_SIZE) {
                mazeShapeRenderer.setColor(mazeColor);

                if ((screenData[i] & 1) != 0) {
                    mazeShapeRenderer.line((int) (ASPECT_RATIO * x),
                            (int) (ASPECT_RATIO * y),
                            (int) (ASPECT_RATIO * x),
                            (int) (ASPECT_RATIO * (y + BLOCK_SIZE - 1)));
                }

                if ((screenData[i] & 2) != 0) {
                    mazeShapeRenderer.line((int) (ASPECT_RATIO * x),
                            (int) (ASPECT_RATIO * y),
                            (int) (ASPECT_RATIO * (x + BLOCK_SIZE - 1)),
                            (int) (ASPECT_RATIO * y));
                }

                if ((screenData[i] & 4) != 0) {
                    mazeShapeRenderer.line((int) (ASPECT_RATIO * (x + BLOCK_SIZE - 1)),
                            (int) (ASPECT_RATIO * y),
                            (int) (ASPECT_RATIO * (x + BLOCK_SIZE - 1)),
                            (int) (ASPECT_RATIO * (y + BLOCK_SIZE - 1)));
                }

                if ((screenData[i] & 8) != 0) {
                    mazeShapeRenderer.line((int) (ASPECT_RATIO * x),
                            (int) (ASPECT_RATIO * (y + BLOCK_SIZE - 1)),
                            (int) (ASPECT_RATIO * (x + BLOCK_SIZE - 1)),
                            (int) (ASPECT_RATIO * (y + BLOCK_SIZE - 1)));
                }

                if ((screenData[i] & 16) != 0) {
                    mazeShapeRenderer.setColor(dotColor);
                    mazeShapeRenderer.rect((int) (ASPECT_RATIO * (x + 11)),
                            (int) (ASPECT_RATIO * (y + 11)),
                            (int) (ASPECT_RATIO * 2),
                            (int) (ASPECT_RATIO * 2));
                }

                i++;
            }
        }
        mazeShapeRenderer.end();
    }

    private void initGame() {

        initVariables();
        pacsLeft = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
        inGame = true;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < N_GHOSTS; i++) {
            int randomGhostPos;
            do {
                randomGhostPos = (int) (Math.random() * N_BLOCKS * N_BLOCKS);
            } while ((screenData[randomGhostPos] & 16) == 0 && randomGhostPos != pacman_x * N_BLOCKS + pacman_y);

            ghost_y[i] = (randomGhostPos / N_BLOCKS) * BLOCK_SIZE;
            ghost_x[i] = (randomGhostPos % N_BLOCKS) * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
    }

    private void loadImages() {
        ghost = new Texture("pacman/images/Ghost.png");
        pacman1 = new Texture("pacman/images/PacMan.png");
        pacman2up = new Texture("pacman/images/PacMan.png");
        pacman3up = new Texture("pacman/images/PacMan.png");
        pacman4up = new Texture("pacman/images/PacMan.png");
        pacman2down = new Texture("pacman/images/PacMan.png");
        pacman3down = new Texture("pacman/images/PacMan.png");
        pacman4down = new Texture("pacman/images/PacMan.png");
        pacman2left = new Texture("pacman/images/PacMan.png");
        pacman3left = new Texture("pacman/images/PacMan.png");
        pacman4left = new Texture("pacman/images/PacMan.png");
        pacman2right = new Texture("pacman/images/PacMan.png");
        pacman3right = new Texture("pacman/images/PacMan.png");
        pacman4right = new Texture("pacman/images/PacMan.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //CHANGE SCREEN HERE -> GAME WON
        if(!timer.update(delta)){
            game.score += score;
            game.lastScreen = this;
            this.dispose();
            game.setScreen(new InterGameScreen(game));
        }
        
        checkInput();

        drawMaze();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        drawPacman();
        drawScore();
        doAnim();

        playGame();
        game.batch.end();

    }

    private void checkInput() {
        boolean swipeLeft = false, swipeRight = false, swipeUp = false, swipeDown = false;

        if (Gdx.input.isTouched()) {
            int xswipe = Gdx.input.getDeltaX();
            int yswpie = Gdx.input.getDeltaY();
            boolean upDown = Math.abs(yswpie) > Math.abs(xswipe);
            if (Math.abs(yswpie) > 30 || Math.abs(xswipe) > 30) {
                System.out.println(xswipe + " " + yswpie);
            }
            if (upDown && Math.abs(yswpie) > 30) {
                if (yswpie > 0) {
                    swipeDown = true;
                } else {
                    swipeUp = true;
                }
            } else if (Math.abs(xswipe) > 30){
                if (xswipe < 0) {
                    swipeLeft = true;
                } else {
                    swipeRight = true;
                }
            }
        }

        if (inGame) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || swipeLeft) {
                req_dx = -1;
                req_dy = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || swipeRight) {
                req_dx = 1;
                req_dy = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || swipeDown) {
                req_dx = 0;
                req_dy = -1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || swipeUp) {
                req_dx = 0;
                req_dy = 1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                inGame = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//                if (timer.isRunning()) {
//                    timer.stop();
//                } else {
//                    timer.start();
//                }
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isTouched()) {
                inGame = true;
                initGame();
            }
        }
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
        font.dispose();
        //game.batch.dispose();
        
    }

//    class TAdapter extends KeyAdapter {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//
//            int key = e.getKeyCode();
//
//            if (inGame) {
//                if (key == KeyEvent.VK_LEFT) {
//                    req_dx = -1;
//                    req_dy = 0;
//                } else if (key == KeyEvent.VK_RIGHT) {
//                    req_dx = 1;
//                    req_dy = 0;
//                } else if (key == KeyEvent.VK_UP) {
//                    req_dx = 0;
//                    req_dy = -1;
//                } else if (key == KeyEvent.VK_DOWN) {
//                    req_dx = 0;
//                    req_dy = 1;
//                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
//                    inGame = false;
//                } else if (key == KeyEvent.VK_PAUSE) {
//                    if (timer.isRunning()) {
//                        timer.stop();
//                    } else {
//                        timer.start();
//                    }
//                }
//            } else {
//                if (key == 's' || key == 'S') {
//                    inGame = true;
//                    initGame();
//                }
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//
//            int key = e.getKeyCode();
//
//            if (key == Event.LEFT || key == Event.RIGHT
//                    || key == Event.UP || key == Event.DOWN) {
//                req_dx = 0;
//                req_dy = 0;
//            }
//        }
//    }
}