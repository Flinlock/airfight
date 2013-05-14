package airf.system;

import airf.component.Heading;
import airf.component.Position;
import airf.component.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class JetSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Heading> hm;
    @Mapper ComponentMapper<Sprite> sm;
    
    @SuppressWarnings("unchecked")
    public JetSystem()
    {
        super(Aspect.getAspectForAll(Heading.class, Sprite.class));
    }

    @Override
    protected void process(Entity e)
    {
        Heading h = hm.get(e);
        Sprite s = sm.get(e);
        
        s.rot = h.h;
    }

}
