package com.kmitl.roadtoa.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kmitl.roadtoa.RoadToA;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.useGL30 = true;
		config.title = "RoadToA";
		config.width = 800;
		config.height = 600;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new RoadToA(), config);

	}
}