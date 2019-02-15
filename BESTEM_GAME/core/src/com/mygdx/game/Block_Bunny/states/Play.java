package com.mygdx.game.Block_Bunny.states;

import static com.mygdx.game.handlers.B2DVARS.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Game;
import com.mygdx.game.entities.Crystal;
import com.mygdx.game.entities.HUD;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.B2DVARS;
import com.mygdx.game.handlers.Background;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.MyInput;

public class Play extends GameState {

    private boolean debug = false;

    private BitmapFont font = new BitmapFont();

    private World world;
    private Box2DDebugRenderer b2ddr;
    private OrthographicCamera b2dCamera;

    private TiledMap tiledMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private MyContactListener cl;

    private Player player;
    private HUD hud;

    private Array<Crystal> crystals;

    private Music music;

    private Background[] backgrounds;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0f, -9.81f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, Game.WIDTH / PPM, Game.HEIGHT / PPM);
        b2ddr = new Box2DDebugRenderer();

        createPlayer();
        createTiledMap("maps/level2.tmx");
        createCrystals();

        music = Game.assets.getMusic("music");
        music.setLooping(true);
        music.play();

        Texture bgs = Game.assets.getTexture("bgs");
        TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 240);
        TextureRegion clouds = new TextureRegion(bgs, 0, 240, 320, 240);
        TextureRegion mountains = new TextureRegion(bgs, 0, 480, 320, 240);
        backgrounds = new Background[3];
        backgrounds[0] = new Background(sky, camera, 0f);
        backgrounds[1] = new Background(clouds, camera, 0.1f);
        backgrounds[2] = new Background(mountains, camera, 0.2f);
    }

    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set(0 / PPM, 200 / PPM);
        bdef.type = BodyType.DynamicBody;
        bdef.linearVelocity.set(1f, 0f);
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(13 / PPM, 13 / PPM);

        fdef.shape = shape;
        fdef.friction = 0f;
        fdef.filter.categoryBits = B2DVARS.BIT_PLAYER;
        fdef.filter.maskBits = B2DVARS.BIT_RED | B2DVARS.BIT_CRYSTAL;
        body.createFixture(fdef).setUserData("player");

        {
            // foot
            shape.setAsBox(11 / PPM, 3 / PPM, new Vector2(0, -15 / PPM), 0);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.maskBits = B2DVARS.BIT_RED;
            body.createFixture(fdef).setUserData("foot");
        }

        player = new Player(body);
        hud = new HUD(player);
    }

    private void createTiledMap(String path) {
        tiledMap = new TmxMapLoader().load(path);
        tmr = new OrthogonalTiledMapRenderer(tiledMap);
        tileSize = (int) tiledMap.getProperties().get("tilewidth", Integer.class);

        TiledMapTileLayer tileLayer;

        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("red");
        createTileLayer(tileLayer, B2DVARS.BIT_RED);

        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("green");
        createTileLayer(tileLayer, B2DVARS.BIT_GREEN);

        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("blue");
        createTileLayer(tileLayer, B2DVARS.BIT_BLUE);
    }

    private void createTileLayer(TiledMapTileLayer tileLayer, short bits) {
        for (int y = 0; y < tileLayer.getHeight(); y++) {
            for (int x = 0; x < tileLayer.getWidth(); x++) {
                Cell cell = tileLayer.getCell(x, y);
                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                BodyDef bdef = new BodyDef();
                FixtureDef fdef = new FixtureDef();

                bdef.position.set((x + 0.5f) * tileSize / PPM, (y + 0.5f) * tileSize / PPM);
                bdef.type = BodyType.StaticBody;

                ChainShape cs = new ChainShape();
                Vector2[] vertices = new Vector2[3];
                vertices[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
                vertices[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
                vertices[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
                cs.createChain(vertices);

                fdef.shape = cs;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = B2DVARS.BIT_PLAYER;

                world.createBody(bdef).createFixture(fdef);
            }
        }
    }

    private void createCrystals() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        crystals = new Array<Crystal>();

        MapLayer layer = tiledMap.getLayers().get("crystals");

        for (MapObject object : layer.getObjects()) {
            bdef.type = BodyType.StaticBody;

            if (object instanceof EllipseMapObject) {
                Ellipse e = ((EllipseMapObject)object).getEllipse();
                float x = e.x;
                float y = e.y;
                bdef.position.set(x / PPM, y / PPM);
            }

            CircleShape circle = new CircleShape();
            circle.setRadius(8 / PPM);

            fdef.shape = circle;
            fdef.filter.categoryBits = B2DVARS.BIT_CRYSTAL;
            fdef.filter.maskBits = B2DVARS.BIT_PLAYER;
            fdef.isSensor = true;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("crystal");

            Crystal c = new Crystal(body);
            body.setUserData(c);
            crystals.add(c);

            player.setTotalCrystals(crystals.size);

            circle.dispose();
        }
    }

    @Override
    public void handleInput() {
        if (MyInput.isPressed(MyInput.BUTTONZ)) playerJump();

        if (MyInput.isPressed(MyInput.BUTTONX)) changeBlocks();

        if (MyInput.isPressed()) {
            if (MyInput.x < Gdx.graphics.getWidth() / 2) changeBlocks();
            else playerJump();
        }
    }

    private void playerJump() {
        if (cl.footTouched()) {
            player.getBody().applyForceToCenter(0, 280, true);
            Game.assets.getSound("jump").play();
        }
    }

    private void changeBlocks() {
        Filter filter = player.getBody().getFixtureList().get(1).getFilterData();
        short maskBits = filter.maskBits;

        if (maskBits == B2DVARS.BIT_RED) maskBits = B2DVARS.BIT_GREEN;
        else if (maskBits == B2DVARS.BIT_GREEN) maskBits = B2DVARS.BIT_BLUE;
        else if (maskBits == B2DVARS.BIT_BLUE) maskBits = B2DVARS.BIT_RED;

        filter.maskBits = maskBits;
        player.getBody().getFixtureList().get(1).setFilterData(filter);

        maskBits |= B2DVARS.BIT_CRYSTAL;
        filter.maskBits = maskBits;
        player.getBody().getFixtureList().get(0).setFilterData(filter);

        Game.assets.getSound("changeblock").play();
    }

    @Override
    public void update(float ts) {
        handleInput();
        world.step(ts, 6, 2);

        for (Background b : backgrounds) {
            b.update(ts);
        }

        Array<Body> bodiesToRemove = cl.getBodiesToRemove();
        for (int i = 0; i < bodiesToRemove.size; i++) {
            Body body = bodiesToRemove.get(i);
            crystals.removeValue((Crystal) body.getUserData(), true);
            world.destroyBody(body);
            player.collectCrystal();
            Game.assets.getSound("crystal").play();
        }
        bodiesToRemove.clear();

        if (player.getPosition().y < 0) gsm.setState(GameStateManager.PLAY);
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getPosition().x * PPM + Game.WIDTH / 4, Game.HEIGHT / 2, 0);
        camera.update();

        sb.setProjectionMatrix(hudCamera.combined);
        for (Background b : backgrounds) {
            b.render(sb);
        }

        sb.setProjectionMatrix(camera.combined);
        player.render(sb);

        for (int i = 0; i < crystals.size; i++) {
            crystals.get(i).render(sb);
        }

        sb.setProjectionMatrix(hudCamera.combined);
        hud.render(sb);

        tmr.setView(camera);
        tmr.render();

        if (debug) {
            b2dCamera.position.set(player.getPosition().x + Game.WIDTH / PPM / 4, Game.HEIGHT / PPM / 2, 0);
            b2dCamera.update();
            b2ddr.render(world, b2dCamera.combined);
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        b2ddr.dispose();
        tiledMap.dispose();
        tmr.dispose();
        crystals.clear();
        font.dispose();
    }
}

