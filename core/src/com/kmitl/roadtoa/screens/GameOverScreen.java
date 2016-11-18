package com.kmitl.roadtoa.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kmitl.roadtoa.RoadToA;

public class GameOverScreen implements Screen{
	
	private SpriteBatch batch;
	private Texture backButtonActive, backButtonInActive, logo;
	private RoadToA game;
	private Stage stage;
	private Image splashImg;
	private static final int BACK_BUTTON_WIDTH = 150;
	private static final int BACK_BUTTON_HEIGHT = 75;
	private static final int BACK_BUTTON_Y = 150;
	private Sound sound;
	
	public GameOverScreen(RoadToA game){
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		backButtonActive = new Texture("mainmenu/backactivebutton.png");
		backButtonInActive = new Texture("mainmenu/backinactivebutton.png");
		Gdx.input.setInputProcessor(stage);
		
		logo = new Texture(Gdx.files.internal("gameover/gameover.png"));
		splashImg = new Image(logo);
		splashImg.setSize(500, 140);
		splashImg.setOrigin(stage.getWidth() / 2, stage.getHeight() / 2);
		stage.addActor(splashImg);
		sound = Gdx.audio.newSound(Gdx.files.internal("gameover/gameover.mp3"));
	}
	@Override
	public void show() {
		sound.play();
		splashImg.setPosition(150, stage.getHeight() / 2);
		splashImg.addAction(sequence(alpha(0f), parallel(moveBy(15, 0, 2f),fadeIn(2f)),	delay(1f)));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    stage.act(delta);
		stage.draw();
	    batch.begin();
	    batch.draw(backButtonInActive,330,BACK_BUTTON_Y,BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
		if (Gdx.input.getX() < 330 + BACK_BUTTON_WIDTH && Gdx.input.getX() > 330
				&& RoadToA.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& RoadToA.HEIGHT-Gdx.input.getY() > BACK_BUTTON_Y){
			batch.draw(backButtonActive,330,BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
	    	if(Gdx.input.isTouched()){
	    		game.setScreen(new MainMenuScreen(game));
	    	}
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
		stage.dispose();
		batch.dispose();
		backButtonActive.dispose();
		backButtonInActive.dispose();
		sound.dispose();
		logo.dispose();
	}
	
}
