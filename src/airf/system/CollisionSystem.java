package airf.system;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import airf.component.BoundingBox;
import airf.component.Position;
import airf.component.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class CollisionSystem extends EntityProcessingSystem 
{
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<BoundingBox> bm;
	

	public CollisionSystem() 
	{
		super(Aspect.getAspectForAll(Position.class, BoundingBox.class));
		
	}

	@Override
	protected void process(Entity e) {
		Position p = pm.get(e);  //Used to place the BoundingBox in the center of the Sprite
		BoundingBox b = bm.get(e);
		
		/*
		 * Need to access the components of all other collision-valid entities to check for collisions
		 * How do I do this? A map of the entities of some sort?
		 * 
		 * 
		 */
		
		
		
		
		/*
		 * Old code stuff, probably don't need - these calculations can be simplified and happen inside EntityFactory
		 * 
		Sprite s = sm.get(e);
		Image img = sprites.get(s.name.toLowerCase());
		float top = p.y + img.getHeight() / 2 * s.scaleX;
		float bottom = p.y - img.getHeight() / 2 * s.scaleX;
		float left = p.x - img.getHeight() / 2 * s.scaleY;
		float right = p.x + img.getHeight() / 2 * s.scaleY;
		
		*/
		
	}

}
