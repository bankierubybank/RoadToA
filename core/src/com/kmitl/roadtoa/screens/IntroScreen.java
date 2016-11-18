package com.kmitl.roadtoa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kmitl.roadtoa.RoadToA;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen implements Screen {
	
	private RoadToA game;
	private Stage stage;
	private Image splashImg, text;
	private Texture logo, Text;
	private Music music;
	
	//Constructor
	public IntroScreen(RoadToA g){
		this.game = g;
		this.stage = new Stage(new StretchViewport(480, 320, game.camera));
		Gdx.input.setInputProcessor(stage);
		
		//create fade logo
		logo = new Texture(Gdx.files.internal("intro/introImg.png"));
		Text = new Texture(Gdx.files.internal("intro/Logo_roadtoA.png"));
		splashImg = new Image(logo);
		splashImg.setSize(400, 300);
		splashImg.setOrigin(stage.getWidth() / 2, stage.getHeight() / 2);
		text = new Image(Text);
		text.setSize(335, 100);
		stage.addActor(splashImg);
		stage.addActor(text);

		text.setPosition(stage.getWidth()/2-165, stage.getHeight()/2-155);
		text.addAction(sequence(alpha(0f), fadeIn(2f),delay(1f),
				fadeOut(2f)));
		splashImg.setPosition(stage.getWidth() / 2 - 215, stage.getHeight() / 2 - 135);
		splashImg.addAction(sequence(alpha(0f), 
				parallel(moveBy(15, 0, 2f),fadeIn(2f)),
				delay(1f),
				fadeOut(2f)
				));
		
		//initialize music
		music = Gdx.audio.newMusic(Gdx.files.internal("intro/introMusic.mp3"));
		music.play();
		music.setOnCompletionListener(new Music.OnCompletionListener() {
			//change screen to MainMenu when music end
			@Override
			public void onCompletion(Music music) {
				game.setScreen(new MainMenuScreen(game));
			}
		});
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
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
		logo.dispose();
		music.dispose();
	}

}
