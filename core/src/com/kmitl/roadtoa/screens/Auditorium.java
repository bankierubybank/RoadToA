package com.kmitl.roadtoa.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kmitl.roadtoa.RoadToA;

public class Auditorium implements Screen {
	
	private OrthographicCamera camera;
	private Image cur_center, cur_left, cur_right, cur_all, cur_l, cur_r, h1, h2, h3, h4, h5, h6;
	private Stage stage;
	private Texture curtain_A, curtain_C, curtain_L, curtain_R, curtain_l, curtain_r, head1, head2, head3, head4, head5, head6;
	private Texture exitButtonActive, exitButtonInActive;
	private SpriteBatch batch;
	private int EXIT_BUTTON_Y = 100, EXIT_BUTTON_WIDTH= 150,EXIT_BUTTON_HEIGHT=75;
	private Music music;
	
	public Auditorium(){
		camera = new OrthographicCamera();
		this.stage = new Stage(new StretchViewport(800, 600, camera));
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(stage);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("end.mp3"));
		music.setLooping(true);
		music.play();
		
		exitButtonActive = new Texture("mainmenu/exitactivebutton.png");
		exitButtonInActive = new Texture("mainmenu/exitinactivebutton.png");
		
		curtain_C = new Texture(Gdx.files.internal("curtain/audiT.png"));
		curtain_L = new Texture(Gdx.files.internal("curtain/audileft.png"));
		curtain_R = new Texture(Gdx.files.internal("curtain/audiright.png"));
		curtain_A = new Texture(Gdx.files.internal("curtain/audi.png"));
		curtain_l = new Texture(Gdx.files.internal("curtain/audiL.png"));
		curtain_r = new Texture(Gdx.files.internal("curtain/audiR.png"));
		head1 = new Texture(Gdx.files.internal("head/jcho.png"));
		head2 = new Texture(Gdx.files.internal("head/jkang.png"));
		head3 = new Texture(Gdx.files.internal("head/jna.png"));
		head4 = new Texture(Gdx.files.internal("head/jnob.png"));
		head5 = new Texture(Gdx.files.internal("head/jnol.png"));
		head6 = new Texture(Gdx.files.internal("head/jThanisa.png"));
		
		cur_all = new Image(curtain_A);
		cur_left = new Image(curtain_L);
		cur_right = new Image(curtain_R);
		cur_center = new Image(curtain_C);
		cur_l = new Image(curtain_l);
		cur_r = new Image(curtain_r);
		h1 = new Image(head1);
		h2 = new Image(head2);
		h3 = new Image(head3);
		h4 = new Image(head4);
		h5 = new Image(head5);
		h6 = new Image(head6);
		
		cur_all.setSize(800, 600);
		cur_left.setSize(200, 330);
		cur_right.setSize(200, 330);
		cur_center.setSize(800, 600);
		cur_l.setSize(400, 330);
		cur_r.setSize(400, 330);
		h1.setSize(130, 130);
		h2.setSize(130, 130);
		h3.setSize(130, 130);
		h4.setSize(130, 130);
		h5.setSize(130, 130);
		h6.setSize(130, 130);
		
		stage.addActor(cur_all);
		stage.addActor(h1);
		stage.addActor(h2);
		stage.addActor(h3);
		stage.addActor(h4);
		stage.addActor(h5);
		stage.addActor(h6);
		stage.addActor(cur_l);
		stage.addActor(cur_r);
		stage.addActor(cur_left);
		stage.addActor(cur_right);
		stage.addActor(cur_center);
		
		cur_all.setPosition(0, 0);
		h1.setPosition(65, stage.getHeight() / 2 + 79);
		h2.setPosition(170, stage.getHeight() / 2 + 79);
		h3.setPosition(280, stage.getHeight() / 2 + 79);
		h4.setPosition(390, stage.getHeight() / 2 + 79);
		h5.setPosition(490, stage.getHeight() / 2 + 79);
		h6.setPosition(600, stage.getHeight() / 2 + 79);
		cur_l.setPosition(39, stage.getHeight() / 2 - 20);
		cur_r.setPosition(stage.getWidth() - 450, stage.getHeight() / 2 - 20);
		cur_left.setPosition(0, stage.getHeight() / 2 -30);
		cur_right.setPosition(stage.getWidth() - 200, stage.getHeight() / 2 -30);
		cur_center.setPosition(0, 0);
		
		cur_l.addAction(parallel(moveBy(-270, 0, 1.5f),fadeOut(1.5f)));
		cur_r.addAction(parallel(moveBy(270, 0, 1.5f),fadeOut(1.5f)));
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
		
		batch.begin();
		int x = 325;
		batch.draw(exitButtonInActive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& RoadToA.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& RoadToA.HEIGHT-Gdx.input.getY() > EXIT_BUTTON_Y){
			batch.draw(exitButtonActive,x,EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
	    	if(Gdx.input.isTouched()){
	    		music.stop();
	    		Gdx.app.exit();
	    	}
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
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
		music.dispose();
		curtain_A.dispose();
		curtain_C.dispose();
		curtain_L.dispose();
		curtain_R.dispose();
		curtain_l.dispose();
		curtain_r.dispose();
		head1.dispose();
		head2.dispose();
		head3.dispose();
		head4.dispose();
		head5.dispose();
		head6.dispose();
		exitButtonActive.dispose();
		exitButtonInActive.dispose();
		batch.dispose();
	}

}
