package com.kmitl.roadtoa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.kmitl.roadtoa.RoadToA;

public class LoadingScreen implements Screen {
	
	private RoadToA game;
	private ShapeRenderer shaperenderer;
	private float progress;
	private String mapname;
	private BitmapFont font;
	private SpriteBatch batch;
	private AssetManager assets;
	private OrthographicCamera camera;
	
	public LoadingScreen(RoadToA game, String m){
		this.game = game;
		this.mapname = m;
		this.shaperenderer = new ShapeRenderer();
		
		font = new BitmapFont();
		batch = new SpriteBatch();
		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, RoadToA.WIDTH, RoadToA.HEIGHT);
		this.progress = 0f;

	}

	@Override
	public void show() {
	}
	
	private void update(float delta){
		int enemyatk = (int)(Math.random()*(70-50))+50;
		progress = MathUtils.lerp(progress, assets.getProgress(), .1f);
		if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Main")){
			game.setScreen(new MapScreen(game));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Architecture")){
			game.setScreen(new BattleScreen(game, "faculty/tapad.png", 135, enemyatk, 495, 874, 1, 0));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("IT")){
			game.setScreen(new BattleScreen(game, "faculty/it.png", 135, enemyatk+10, 1865, 780, 2, 1));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Engineer")){
			game.setScreen(new BattleScreen(game, "faculty/vidva.png", 135, enemyatk, 875, 889, 3, 0));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Library")){
			game.setScreen(new BattleScreen(game, "faculty/horsamud.png", 135, enemyatk, 850, 290, 4, 0));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Science")){
			game.setScreen(new BattleScreen(game, "faculty/vidya.png", 135, enemyatk, 1450, 390, 5, 0));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("PrathepBuilding")){
			game.setScreen(new BattleScreen(game, "faculty/prainw.png", 135, enemyatk, 1349, 965, 6, 0));
		}else if(assets.update() && progress >= assets.getProgress() - .001f && mapname.equals("Education")){
			game.setScreen(new BattleScreen(game, "faculty/curu.png", 135, enemyatk, 1780, 360, 7, 0));
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(Color.BLACK);
		shaperenderer.rect(32, camera.viewportHeight / 2, (Gdx.graphics.getWidth() - 50), 50);
		
		shaperenderer.setColor(Color.BLUE);
		shaperenderer.rect(32, camera.viewportHeight / 2, (Gdx.graphics.getWidth() - 50) * progress, 50);
		shaperenderer.end();
		
		batch.begin();
		font.draw(batch, "Screen: LOADING!", 20, 20);
		batch.end();
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
		shaperenderer.dispose();
		font.dispose();
		batch.dispose();
		assets.dispose();
	}
	
}