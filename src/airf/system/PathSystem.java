package airf.system;

import java.awt.geom.Point2D;

import airf.component.Path;
import airf.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class PathSystem extends EntityProcessingSystem
{   
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Path> pathm; 
    
    @SuppressWarnings("unchecked")
    public PathSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Path.class));        
    }

    @Override
    protected void process(Entity e)
    {
        Position pos = pm.get(e);
        Path path = pathm.get(e);
        
        pos.lx = pos.x;
        pos.ly = pos.y;
        
        path.totalTime += world.delta;
        float p = path.course.getCourse().calculateP(path.totalTime);
        
        Point2D.Float pnt = path.course.getCourse().getPoint(p);
        pos.x = pnt.x + path.x;
        pos.y = pnt.y + path.y;
                        
        // Assume timeslots are set up so that this never occurs
        if(p > 1.0f)
            throw new IllegalStateException();
    }
    
}
