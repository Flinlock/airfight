package airf.system;

import java.awt.geom.Point2D;

import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.pathing.CourseUpdate;

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
        
        CourseUpdate update = path.course.calculateUpdate(path.p, path.v, world.delta);
        path.p += update.pDelta;
        path.v += update.vDelta;
        
        
        Point2D.Float pnt = path.course.getPoint(path.p);
        p.x = pnt.x + path.x;
        p.y = pnt.y + path.y;
                
        v.x = (p.x-p.lx)/(world.delta-update.dT);
        v.y = (p.y-p.ly)/(world.delta-update.dT);
        
        if(path.p >= 1.0f)
        {
            p.x += v.x*update.dT;
            p.y += v.y*update.dT;
            
            v.x = (p.x-p.lx)/(world.delta);
            v.y = (p.y-p.ly)/(world.delta);
            
            e.removeComponent(path);
            world.changedEntity(e);
        }
    }
    
}
