package airf.system;

import airf.component.Heading;
import airf.component.Jet;
import airf.component.Path;
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
    @Mapper ComponentMapper<Jet> jm;
    @Mapper ComponentMapper<Path> pathm;
    
    @SuppressWarnings("unchecked")
    public JetSystem()
    {
        super(Aspect.getAspectForAll(Heading.class, Sprite.class, Jet.class));
    }

    @Override
    protected void process(Entity e)
    {
        Heading h = hm.get(e);
        Sprite s = sm.get(e);
        Jet j = jm.get(e);
        
        s.rot = h.h+90;
        
        j.state = j.state.update(e);
        if(j.state.changed())
            world.changedEntity(e);
    }

    public Path getPath(Entity e)
    {
        return pathm.get(e);
    }

}
