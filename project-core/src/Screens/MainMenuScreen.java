package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGame;

public class MainMenuScreen implements Screen {
	
	final MyGame game;
	
	private OrthographicCamera camera;
	
	private Stage stage;
	private Skin skin;

	//Constructor
	public MainMenuScreen(final MyGame game){
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 320);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		TextButton buttonStart = new TextButton("START", skin);
		buttonStart.setWidth(100);
		buttonStart.setHeight(50);
		buttonStart.setPosition(320 / 2 - 100 / 2, 320 - 50 * 2);
		buttonStart.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				super.clicked(event, x, y);
				game.setScreen(new GameScreen(game));
			}
		});
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(buttonStart);
	}
	
	@Override
	public void render(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void show() {
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

	}
}
