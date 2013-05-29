package airf.system;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*; 

import airf.component.Path;
import airf.component.PathQueue;
import airf.pathing.Course;
import airf.pathing.CourseFactory;

import com.artemis.Entity;
import com.artemis.World;

public class DiscreteTimeSystemTest
{
    Course c0,c1,c2;
    World w;
    Entity e;
    PathQueue q;
    Path p;

    @Before
    public void setUp() throws Exception
    {
        c0 = CourseFactory.createCourseAccel(0);
        c1 = CourseFactory.createCourseHardL(0);
        c2 = CourseFactory.createCourseHardR(0); 
        

        w = new World();
        e = w.createEntity();
        
        q = new PathQueue();
        q.count = 0;
        
        q.course.add(c1);
        q.startX.add(0f);
        q.startY.add(0f);
        
        q.course.add(c2);
        q.startX.add(0f);
        q.startY.add(0f);
                
        e.addComponent(q);
        
        p = new Path();
        p.course = c0;
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
        DiscreteTimeSystem sys = new DiscreteTimeSystem(5);
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
        DiscreteTimeSystem sys = new DiscreteTimeSystem(10);
        w.setSystem(sys);
        w.initialize();
        w.setDelta(10);
        
        for(int i = 0; i < 10; i++)
        {
            assertTrue(c0 == p.course);
            w.process();
        }
        
        assertTrue(c1 == p.course);
        assertEquals(1, q.course.size());
    }
    
    @Test
    public void testEmptyQueueDefaultsToStraightPath()
    {
        DiscreteTimeSystem sys = new DiscreteTimeSystem(3);
        w.setSystem(sys);
        w.initialize();
        w.setDelta(10);
        
        q.course.clear();
        
        for(int i = 0; i < 3; i++)
        {
            assertTrue(c0 == p.course);
            w.process();
        }
        
        assertEquals("Straight Flight", p.course.toString());
        // TODO: Test parameters of the straight flight course.
    }
}
