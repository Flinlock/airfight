package airf.system;

import java.awt.geom.Point2D;

import airf.component.Path;
import airf.component.ManeuverQueue;
import airf.pathing.Course;
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
            
            if(!pq.maneuvers.isEmpty())
            {
                Point2D.Float pnt = p.course.getPoint(1f);
                p.course = pq.maneuvers.remove(0).getCourse();
                p.x += pnt.x;
                p.y += pnt.y;
                p.p = 0;
            }
            else
            {
                Point2D.Float pnt = p.course.getPoint(1f);
                float h = p.course.getEndHeading();
                h = (float)(h/Math.PI*180);
                p.course = mf.createCourseStraight(h,false).getCourse();
                p.x += pnt.x;
                p.y += pnt.y;
                p.p = 0;

                System.out.println("Maneuver Queue Empty: Adding Straight Course!");
            }
        }
            
    }

}
