package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game{
	
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 320;
	public static final float PPM = 100;
	
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void render(){
		super.render();
	}
}
