package airf.system;

import airf.component.PinTo;
import airf.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class PinToSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<PinTo> ptm;

    @SuppressWarnings("unchecked")
    public PinToSystem()
    {
        super(Aspect.getAspectForAll(PinTo.class));
    }

    @Override
    protected void process(Entity e)
    {
        PinTo pt = ptm.get(e);
        Position p = pm.get(e);
        
        Position tP = pm.get(pt.target);
        
        p.x = tP.x;
        p.y = tP.y;
        
        p.lx = tP.lx;
        p.ly = tP.ly;
    }

}
