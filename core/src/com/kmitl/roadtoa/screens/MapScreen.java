package com.kmitl.roadtoa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.kmitl.roadtoa.RoadToA;
import com.kmitl.roadtoa.entities.Player;

public class MapScreen implements Screen{
	private RoadToA game;
	private TiledMap map;
	private TiledMapTileLayer collisionLayer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	private TextureAtlas playerAtlas;
	private Animation still, left, right, up, down;
	private Texture black1, black2, black3, black4, black5, black6;
	private Texture color1, color2, color3, color4, color5, color6;
	private Texture archIcon, itIcon, engiIcon, libIcon, sciIcon, prathepIcon, edIcon, cant;
	private Music music;
	private BitmapFont font;
	
	//Constructor
	public MapScreen(RoadToA game){
		this.game = game;
		init();
		player.setPosition(235,57);
	}
	public MapScreen(RoadToA game, float x, float y){
		this.game = game;
		init();
		player.setPosition(x,y);
	}
	
	private void init(){
		//map load
		map = new TmxMapLoader().load("mainmap/map.tmx");
		collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
				
		//initialize music
		music = Gdx.audio.newMusic(Gdx.files.internal("mainmap/mainmap.mp3"));
		music.setLooping(true);
		music.play();
		
		//player animation
		playerAtlas = new TextureAtlas("mainmap/smart.pack");
		still = new Animation(1/2f, playerAtlas.findRegions("still"));
		left = new Animation(1 / 6f, playerAtlas.findRegions("left"));
		right = new Animation(1 / 6f, playerAtlas.findRegions("right"));
		up = new Animation(1 / 6f, playerAtlas.findRegions("up"));
		down = new Animation(1 / 6f, playerAtlas.findRegions("down"));
		still.setPlayMode(Animation.PlayMode.LOOP);
		left.setPlayMode(Animation.PlayMode.LOOP);
		right.setPlayMode(Animation.PlayMode.LOOP);
		up.setPlayMode(Animation.PlayMode.LOOP);
		down.setPlayMode(Animation.PlayMode.LOOP);
		player = new Player(still, left, right, up, down, collisionLayer);
		
		black1 = new Texture("head/jcho2.png");
		black2 = new Texture("head/jkang2.png");
		black3 = new Texture("head/jna2.png");
		black4 = new Texture("head/jThanisa2.png");
		black5 = new Texture("head/jnol2.png");
		black6 = new Texture("head/jnob2.png");
		
		color1 = new Texture("head/jcho.png");
		color2 = new Texture("head/jkang.png");
		color3 = new Texture("head/jna.png");
		color4 = new Texture("head/jThanisa.png");
		color5 = new Texture("head/jnol.png");
		color6 = new Texture("head/jnob.png");
		
		archIcon = new Texture("mainmap/ARCH.png");
		itIcon = new Texture("mainmap/itforge.png");
		engiIcon = new Texture("mainmap/en.png");
		libIcon = new Texture("mainmap/book.png");
		sciIcon = new Texture("mainmap/ato.png");
		prathepIcon = new Texture("mainmap/Pen.png");
		edIcon = new Texture("mainmap/kuru.png");
		cant = new Texture("mainmap/cant.png");
		
		Gdx.input.setInputProcessor(player);
		
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		
	}
	
	@Override
	public void show() {
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();

		renderer.getBatch().begin();
		headRenderer(RoadToA.Architecture, color1, black1, 310);
		headRenderer(RoadToA.Engineer, color2, black2, 260);
		headRenderer(RoadToA.Library, color3, black3, 210);
		headRenderer(RoadToA.Science, color4, black4, 160);
		headRenderer(RoadToA.PrathepBuilding, color5, black5, 110);
		headRenderer(RoadToA.ED, color6, black6, 60);
		player.draw(renderer.getBatch());
		renderer.getBatch().end();

		warp();
		
		//cheat
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			RoadToA.Architecture = 1;
			RoadToA.Engineer = 1;
			RoadToA.Library = 1;
			RoadToA.Science = 1;
			RoadToA.PrathepBuilding = 1;
			RoadToA.ED = 1;
			RoadToA.SCORE = 6;
		}
	}
	
	private void headRenderer(int faculty, Texture color, Texture black, int x){
		if (faculty == 1) {
			renderer.getBatch().draw(color, player.getX() - x, player.getY() + 240, 50, 50);
		}
		else{
			renderer.getBatch().draw(black, player.getX() - x, player.getY() + 240, 50, 50);
		}
	}
	
	private void warp(){
		if ((int) (player.getX()) >= 495 && (int) (player.getX()) <= 535 && (int) (player.getY()) >= 872 && RoadToA.Architecture == 0) {
			renderer.getBatch().begin();
			renderer.getBatch().draw(archIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "Architecture"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 1870 && (int) (player.getX()) <= 1893 && (int) (player.getY()) >= 790){
			renderer.getBatch().begin();
			if (RoadToA.SCORE < 6) {
				renderer.getBatch().draw(cant, player.getX() - 5, player.getY() + 120, 50, 50);
				font.draw(renderer.getBatch(), "Can't get in, attack other first", player.getX() - 50, player.getY());
			}
			else{
				renderer.getBatch().draw(itIcon, player.getX() - 5, player.getY() + 120, 50, 50);
				font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			}
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && RoadToA.SCORE >= 6) {
				game.setScreen(new LoadingScreen(game, "IT"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 875 && (int) (player.getX()) <= 896 && (int) (player.getY()) >= 889 && RoadToA.Engineer == 0){
			renderer.getBatch().begin();
			renderer.getBatch().draw(engiIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "Engineer"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 850 && (int) (player.getX()) <= 870 && (int) (player.getY()) >= 290 && (int) (player.getY()) <= 300&& RoadToA.Library == 0){
			renderer.getBatch().begin();
			renderer.getBatch().draw(libIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "Library"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 1410 && (int) (player.getX()) <= 1450 && (int) (player.getY()) >= 390	&& (int) (player.getY()) <= 400&& RoadToA.Science == 0){
			renderer.getBatch().begin();
			renderer.getBatch().draw(sciIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(),  "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "Science"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 1325 && (int) (player.getX()) <= 1350 && (int)(player.getY()) >= 965 && RoadToA.PrathepBuilding == 0){
			renderer.getBatch().begin();
			renderer.getBatch().draw(prathepIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "PrathepBuilding"));
				music.stop();
			}
		}else if((int) (player.getX()) >= 1780 && (int) (player.getX()) <= 1810 && (int) (player.getY()) >= 360	&& (int) (player.getY()) <= 370&& RoadToA.ED == 0){
			renderer.getBatch().begin();
			renderer.getBatch().draw(edIcon, player.getX() - 5, player.getY() + 120, 50, 50);
			font.draw(renderer.getBatch(), "Press Spacebar for Battle", player.getX() - 50, player.getY());
			renderer.getBatch().end();
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(new LoadingScreen(game, "Education"));
				music.stop();
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/1.2f;
		camera.viewportHeight = height/1.2f;
		camera.update();		
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
		map.dispose();
		renderer.dispose();
		playerAtlas.dispose();
		music.dispose();
		font.dispose();
		black1.dispose();
		black2.dispose();
		black3.dispose();
		black4.dispose();
		black5.dispose();
		black6.dispose();
		color1.dispose();
		color2.dispose();
		color3.dispose();
		color4.dispose();
		color5.dispose();
		color6.dispose();
		archIcon.dispose();
		itIcon.dispose();
		engiIcon.dispose();
		libIcon.dispose();
		sciIcon.dispose();
		prathepIcon.dispose();
		edIcon.dispose();
	}
}
