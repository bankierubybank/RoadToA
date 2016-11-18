package com.kmitl.roadtoa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kmitl.roadtoa.RoadToA;

public class MainMenuScreen implements Screen {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 75;
	private static final int PLAY_BUTTON_Y = 400;
	private static final int CREDIT_BUTTON_Y = 250;
	private static final int EXIT_BUTTON_Y = 100;

	private RoadToA game;
	private SpriteBatch batch;
	private Texture playButtonActive, playButtonInActive;
	private Texture creditButtonActive, creditButtonInActive;
	private Texture exitButtonActive, exitButtonInActive;
	private Texture background;
	private Music music;
	
	//Constructor
	public MainMenuScreen(RoadToA game) {
		this.game = game;
		batch = new SpriteBatch();
		background = new Texture("mainmenu/menuImg.png");
		playButtonActive = new Texture("mainmenu/playactivebutton.png");
		playButtonInActive = new Texture("mainmenu/playinactivebutton.png");
		creditButtonActive = new Texture("mainmenu/creditactive.png");
		creditButtonInActive = new Texture("mainmenu/creditInactive.png");
		exitButtonActive = new Texture("mainmenu/exitactivebutton.png");
		exitButtonInActive = new Texture("mainmenu/exitinactivebutton.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("mainmenu/mainmenu.mp3"));
		music.setLooping(true);
		music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw background and buttons
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		buttonCreator(BUTTON_WIDTH, BUTTON_HEIGHT, PLAY_BUTTON_Y, playButtonActive, playButtonInActive, "play");
		buttonCreator(BUTTON_WIDTH, BUTTON_HEIGHT, CREDIT_BUTTON_Y, creditButtonActive, creditButtonInActive, "credit");
		buttonCreator(BUTTON_WIDTH, BUTTON_HEIGHT, EXIT_BUTTON_Y, exitButtonActive, exitButtonInActive, "exit");
		batch.end();
	}

	public void buttonCreator(int btnWidth, int btnHeight, int btnY, Texture btnActive, Texture btnInactive, String a) {
		int x = 50;
		if (Gdx.input.getX() < x + btnWidth && Gdx.input.getX() > x	&& RoadToA.HEIGHT - Gdx.input.getY() < btnY + btnHeight && RoadToA.HEIGHT - Gdx.input.getY() > btnY) {
			batch.draw(btnActive, x, btnY, btnWidth, btnHeight);
			if (Gdx.input.isTouched()) {
				buttonAction(a);
			}
		} 
		else {
			batch.draw(btnInactive, x, btnY, btnWidth, btnHeight);
		}
	}

	public void buttonAction(String a) {
		if (a.equals("play")) {
			music.stop();
			game.setScreen(new LoadingScreen(game, "Main"));
		}
		if (a.equals("credit")) {
			music.stop();
			game.setScreen(new CreditScreen(game));
		}
		if (a.equals("exit")) {
			Gdx.app.exit();
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
		batch.dispose();
		music.stop();
		music.dispose();
		playButtonActive.dispose();
		playButtonInActive.dispose();
		creditButtonActive.dispose();
		creditButtonInActive.dispose();
		exitButtonActive.dispose();
		exitButtonInActive.dispose();
		background.dispose();
	}

	@Override
	public void show() {
	}

}
