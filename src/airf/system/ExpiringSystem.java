package airf.system;

import airf.component.Expires;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class ExpiringSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Expires> em;

    @SuppressWarnings("unchecked")
    public ExpiringSystem()
    {
        super(Aspect.getAspectForAll(Expires.class));
    }

    @Override
    protected void process(Entity e)
    {
        Expires ex = em.get(e);
        if(ex.lifeTime-- <= 0)
            e.deleteFromWorld();
    }

}
