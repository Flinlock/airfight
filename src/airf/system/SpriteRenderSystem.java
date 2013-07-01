package airf.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import airf.component.Position;
import airf.component.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class SpriteRenderSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Sprite> sm;
    
    private Map<String, Image> sprites;
    private List<Entity> sortedEntities;
    private int height;
    
    
    @SuppressWarnings("unchecked")
    public SpriteRenderSystem(int height)
    {
        super(Aspect.getAspectForAll(Position.class, Sprite.class));
        
        sortedEntities = new ArrayList<>();
        sprites = new HashMap<>();
        this.height = height;
    }
    
    @Override
    protected void initialize()
    {
        try
        {
            sprites.put("jet_black", new Image("res/images/jet_black.png"));
            sprites.put("jet_white", new Image("res/images/jet_white.png"));
            sprites.put("selection_highlight", new Image("res/images/selection.png"));
            sprites.put("gun_fire", new Image("res/images/gun_fire.png"));
        }
        catch(SlickException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities)
    {
        for(int i = 0; sortedEntities.size() > i; i++)
        {
            assert(entities.contains(sortedEntities.get(i)));
            process(sortedEntities.get(i));
        }
    }
    
    protected void process(Entity e)
    {
            Position p = pm.getSafe(e);
            Sprite s = sm.get(e);
                       
            if(s == null)
                System.out.println("whahdf");
            
            Image img = sprites.get(s.name.toLowerCase());
            
            if(img == null)
                throw new IllegalArgumentException("Invalid sprite name!");
            
            float posX = p.x - (img.getWidth() / 2 * s.scaleX);
            float posY = height - p.y - (img.getHeight() / 2 * s.scaleY);

            // Convert s.rot to degrees, offset by 90 degrees, then switch rotation direction
            img.setRotation((float)( 360f - (s.rot / Math.PI * 180 + 90f) ));
            img.draw(posX, posY, s.scaleX);
    }
    
    @Override
    protected void inserted(Entity e)
    {
        sortedEntities.add(e);
    }
    
    @Override
    protected void removed(Entity e)
    {
        sortedEntities.remove(e);
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
