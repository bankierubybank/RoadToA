package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Sprites.Tree;

public class B2WorldCreator {
	
	//constructor
	public B2WorldCreator(World world, TiledMap map){		
		//create objects
		for(MapObject object : map.getLayers().get("trees").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new Tree(world, map, rect);
		}
	}

}
