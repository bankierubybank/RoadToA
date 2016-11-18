package com.kmitl.roadtoa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kmitl.roadtoa.screens.IntroScreen;

public class RoadToA extends Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static int SCORE = 0, Architecture = 0, IT = 0, Engineer = 0, Library = 0, Science = 0, PrathepBuilding = 0, ED = 0;
	public OrthographicCamera camera;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
