package airf.system;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.jetstates.Maneuver;
import airf.pathing.ManeuverFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class DiscreteTimeSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<ManeuverQueue> pqm;
    @Mapper ComponentMapper<Path> pm;
        
    ManeuverFactory mf;    
    int period;

    @SuppressWarnings("unchecked")
    public DiscreteTimeSystem(int period, ManeuverFactory mf)
    {
        super(Aspect.getAspectForAll(ManeuverQueue.class, Path.class));
        this.period = period;
        this.mf = mf;
    }
        
    @Override
    protected void process(Entity e)
    {        
        ManeuverQueue pq = pqm.get(e);
        
        if(++pq.count == period)
        {
            pq.count = 0;
            Path p = pm.get(e);
            
            pq.finishedManeuver = p.course;
            
            if(!pq.maneuvers.isEmpty())
            {
                Point2D.Float pnt = p.course.getCourse().getPoint(1f);
                Maneuver man = pq.maneuvers.remove(0);
                float h = p.course.getCourse().getEndHeading();
                h = (float)(h/Math.PI*180);
                p.course = man;
                p.x += pnt.x;
                p.y += pnt.y;
                p.h = h;
                p.totalTime = 0;
            }
            else
            {
                Point2D.Float pnt = p.course.getCourse().getPoint(1f);
                float h = p.course.getCourse().getEndHeading();
                h = (float)(h/Math.PI*180);
                p.course = null;
                p.x += pnt.x;
                p.y += pnt.y;
                p.h = h;
                p.totalTime = 0;
            }
        }
        else
            pq.finishedManeuver = null;
            
    }

}
