package airf.system;

import java.awt.geom.Point2D;

import airf.component.Path;
import airf.component.PathQueue;
import airf.pathing.Course;
import airf.pathing.CourseFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class DiscreteTimeSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<PathQueue> pqm;
    @Mapper ComponentMapper<Path> pm;
    
    int period;

    @SuppressWarnings("unchecked")
    public DiscreteTimeSystem(int period)
    {
        super(Aspect.getAspectForAll(PathQueue.class, Path.class));
        this.period = period;
    }

    @Override
    protected void process(Entity e)
    {        
        PathQueue pq = pqm.get(e);
        
        if(++pq.count == period)
        {
            pq.count = 0;
            Path p = pm.get(e);
            
            if(!pq.course.isEmpty())
            {
                p.course = pq.course.remove(0);
                p.x = pq.startX.remove(0);
                p.y = pq.startY.remove(0);
                p.p = 0;
            }
            else
            {
                Point2D.Float pnt = p.course.getPoint(1.0f);
                float h = p.course.getEndHeading();
                p.course = CourseFactory.createCourseStraight(h);
                p.x = pnt.x;
                p.y = pnt.y;
                p.p = 0;
            }
        }
            
    }

}
