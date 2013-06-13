package airf.system;

import airf.component.Heading;
import airf.component.Jet;
import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.component.Position;
import airf.component.Sprite;

import com.artemis.Aspect;
import com.artemis.Component;
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
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<ManeuverQueue> pqm;
    
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
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type, Entity e)
    {
        if(type == Heading.class)
            return (T)hm.get(e);
        
        if(type == Jet.class)
            return (T)jm.get(e);
        
        if(type == Path.class)
            return (T)pathm.get(e);
        
        if(type == Position.class)
            return (T)pm.get(e);
                
        if(type == Heading.class)
            return (T)hm.get(e);
        
        if(type == ManeuverQueue.class)
            return (T)pqm.get(e);
        
        throw new IllegalArgumentException();
    }
}
