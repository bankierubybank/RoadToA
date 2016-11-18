package com.kmitl.roadtoa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kmitl.roadtoa.RoadToA;

public class CreditScreen implements Screen {
	private RoadToA game;
	private SpriteBatch batch;
	private BitmapFont font;
	private Music music;
	
	private Texture backButtonActive, backButtonInActive;
	
	private static final int BACK_BUTTON_WIDTH = 150;
	private static final int BACK_BUTTON_HEIGHT = 75;
	private static final int BACK_BUTTON_Y = 150;
	
	public CreditScreen(RoadToA g){
		this.game =  g;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		batch = new SpriteBatch();
		backButtonActive = new Texture("MainMenu/backactivebutton.png");
		backButtonInActive = new Texture("MainMenu/backinactivebutton.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("creditmusic.mp3"));
		music.setLooping(true);
		music.play();
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    batch.begin();
	    
	    font.draw(batch, "CREDIT", 375, 550);
		font.draw(batch, "Janejara   Soonklang    58070019", 300, 500);
		font.draw(batch, "Chatchai   Wongdetsakul 58070020", 300, 450);
		font.draw(batch, "Nattapon   Chitnarong   58070036", 300, 400);
		font.draw(batch, "Nathapath  Mangkorn     58070040", 300, 350);
		font.draw(batch, "Songsak    Thawaro      58070140", 300, 300);
		
		if (Gdx.input.getX() < RoadToA.WIDTH/2 - 75 + BACK_BUTTON_WIDTH && Gdx.input.getX() > RoadToA.WIDTH/2 - 75 && RoadToA.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& RoadToA.HEIGHT-Gdx.input.getY() > BACK_BUTTON_Y){
			batch.draw(backButtonActive, RoadToA.WIDTH/2 - 75,BACK_BUTTON_Y, 150, 75);
	    	if(Gdx.input.isTouched()){
	    		music.stop();
	    		game.setScreen(new MainMenuScreen(game));
	    	}
	    }
		else{
			batch.draw(backButtonInActive, RoadToA.WIDTH/2 - 75, BACK_BUTTON_Y, 150, 75);
		}
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
		batch.dispose();
		font.dispose();
		backButtonActive.dispose();
		backButtonInActive.dispose();
		music.dispose();
	}

}
