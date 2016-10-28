package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable{
	public Stage stage;
	private Viewport viewport;
	
	private Integer worldTimer;
	
	Label countdownLabel;
	Label levelLabel;
	Label worldLabel;
	
	//constructor
	public Hud(SpriteBatch b){
		worldTimer = 300;
		
		viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, b);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(worldLabel).expandX().padTop(10);
		table.row();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expand();
		
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
