package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game{

	@Override
	public void create() {
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void render(){
		super.render();
	}
}
