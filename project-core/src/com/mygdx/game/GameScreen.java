package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen{
	
	final MyGame game;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Viewport gamePort;
	private Hud hud;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Character player;

	
	//Constructor
	public GameScreen(final MyGame game){
		this.game = game;
		
		maploader = new TmxMapLoader();
		map = maploader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
		
		camera = new OrthographicCamera();
		gamePort = new FitViewport(MyGame.V_HEIGHT / MyGame.PPM, MyGame.V_WIDTH / MyGame.PPM, camera);
		
		camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
		
		hud = new Hud(game.batch);
		
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object : map.getLayers().get("obj").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / MyGame.PPM, rect.getHeight() / 2 / MyGame.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		player = new Character(world);
	}
	
	public void handleInput(float dt){
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
	}
	
	public void update(float dt){
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		camera.position.x = player.b2body.getPosition().x;
		//camera.position.y = player.b2body.getPosition().y;
		
		camera.update();
		renderer.setView(camera);
	}

	@Override
	public void render (float delta) {
		update(delta);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/*game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();*/
		
		
		renderer.render();
		
		b2dr.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		map.dispose();
		renderer.dispose();
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
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
}
