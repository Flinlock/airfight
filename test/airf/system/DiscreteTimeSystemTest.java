package airf.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.jetstates.Maneuver;
import airf.pathing.ManeuverFactory;

import com.artemis.Entity;
import com.artemis.World;

public class DiscreteTimeSystemTest
{
    Maneuver c0,c1,c2;
    World w;
    Entity e;
    ManeuverQueue q;
    Path p;
    ManeuverFactory mf;

    @Before
    public void setUp() throws Exception
    {
        mf = new ManeuverFactory(5000);
        c0 = mf.createCourseAccel(0);
        c1 = mf.createCourseHardL(0,false);
        c2 = mf.createCourseHardR(0,false); 
        

        w = new World();
        e = w.createEntity();
        
        q = new ManeuverQueue();
        q.count = 0;
        
        ManeuverQueue.addManeuver(q, c1);
        ManeuverQueue.addManeuver(q, c2);
                
        e.addComponent(q);
        
        p = new Path();
        p.course = c0.getCourse();
        p.p = 0;
        p.v = 0.01f;
        p.x = 0;
        p.y = 0;
        
        e.addComponent(p);
        
        e.addToWorld();
    }

    @Test
    public void testCountFollowPeriod()
    {
        DiscreteTimeSystem sys = new DiscreteTimeSystem(5,mf);
        w.setSystem(sys);
        w.initialize();
        w.setDelta(10);
        
        for(int i = 0; i < 5; i++)
        {
            assertEquals(i, q.count);
            w.process();
        }
        
        assertEquals(0, q.count);
        w.process();
        assertEquals(1, q.count);
    }
    
    @Test
    public void testPathChangeOnPeriodEnd()
    {
        DiscreteTimeSystem sys = new DiscreteTimeSystem(10,mf);
        w.setSystem(sys);
        w.initialize();
        w.setDelta(10);
        
        for(int i = 0; i < 10; i++)
        {
            assertTrue(c0.getCourse() == p.course);
            w.process();
        }
        
        assertTrue(c1.getCourse() == p.course);
        assertEquals(1, q.maneuvers.size());
    }
    
    @Test
    public void testEmptyQueueDefaultsToStraightPath()
    {
        DiscreteTimeSystem sys = new DiscreteTimeSystem(3,mf);
        w.setSystem(sys);
        w.initialize();
        w.setDelta(10);
        
        q.maneuvers.clear();
        
        for(int i = 0; i < 3; i++)
        {
            assertTrue(c0.getCourse() == p.course);
            w.process();
        }
        
        assertEquals("Straight Flight", p.course.toString());
        // TODO: Test parameters of the straight flight course.
    }
}
