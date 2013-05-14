package airf.system;

import java.awt.geom.Point2D;

import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class PathSystem extends EntityProcessingSystem
{   
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;
    @Mapper ComponentMapper<Path> pathm; 
    
    @SuppressWarnings("unchecked")
    public PathSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Velocity.class, Path.class));        
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Velocity v = vm.get(e);
        Path path = pathm.get(e);
        
        p.lx = p.x;
        p.ly = p.y;
        
        path.p += path.path.getProgressDelta(path.v, world.delta/1000f);
        Point2D.Float pnt = path.path.getPoint(path.p);
        p.x = pnt.x;
        p.y = pnt.y;
        
        v.x = (p.x-p.lx)/world.delta;
        v.y = (p.y-p.ly)/world.delta;
        
        if(path.path.isPathComplete(path.p))
        {
            e.removeComponent(path);
            world.changedEntity(e);
        }
    }
    
}
