package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Hud;
import com.mygdx.game.MyGame;

import Sprites.Character;
import Tools.B2WorldCreator;


public class GameScreen implements Screen{
	
	final MyGame game;
	
	//Tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2D variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera camera;
	private Viewport gamePort;
	private Hud hud;
	
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
		
		//create Box2D world
		world = new World(new Vector2(0, 0), false);
		//debug lines of world
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator(world, map);
		
		
		player = new Character(world);
	}
	
	public void handleInput(float dt){
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player.b2body.applyLinearImpulse(new Vector2(0, 1), player.b2body.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			player.b2body.applyLinearImpulse(new Vector2(0, -1), player.b2body.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		
	}
	
	public void update(float dt){
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		//camera follow player
		camera.position.x = player.b2body.getPosition().x;
		camera.position.y = player.b2body.getPosition().y;
		
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
		world.dispose();
		b2dr.dispose();
		hud.dispose();
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
