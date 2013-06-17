package airf.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import airf.Constants;
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
    
    
    @SuppressWarnings("unchecked")
    public SpriteRenderSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Sprite.class));
        
        sortedEntities = new ArrayList<>();
        sprites = new HashMap<>();
    }
    
    @Override
    protected void initialize()
    {
        try
        {
            sprites.put("jet_black", new Image("res/images/jet_black.png"));
            sprites.put("jet_white", new Image("res/images/jet_white.png"));
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
//        if(pm.has(e))
//        {
            Position p = pm.getSafe(e);
            Sprite s = sm.get(e);
                        
            Image img = sprites.get(s.name.toLowerCase());
            
            if(img == null)
                return;
            
            float posX = p.x - (img.getWidth() / 2 * s.scaleX);
            float posY = Constants.HEIGHT - p.y - (img.getHeight() / 2 * s.scaleY);

            img.setRotation((float)(s.rot / Math.PI * 180));
            img.draw(posX, posY, s.scaleX);
//        }
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
