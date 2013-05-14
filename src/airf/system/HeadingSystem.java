package airf.system;

import airf.component.Heading;
import airf.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Calculates the heading of a moving object s.t. 0 degrees is 
 * on screen north and 90 degrees is on screen east.
 * 
 * @author Michael McCarron
 *
 */
public class HeadingSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Heading> hm;

    @SuppressWarnings("unchecked")
    public HeadingSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Heading.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Heading h = hm.get(e); 
        
        if(p.x == p.lx)
        {
            if(p.y > p.ly)
                h.h = 180f;
            else if(p.y < p.ly)
                h.h = 0f;
        }
        
        h.h = (float)(180*Math.atan2(p.y - p.ly, p.x - p.lx)/Math.PI);
        
        h.h += 90;
    }
}
