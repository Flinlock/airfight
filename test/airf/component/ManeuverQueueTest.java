package airf.component;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import airf.jetstates.Maneuver;
import airf.pathing.ManeuverFactory;

public class ManeuverQueueTest
{
    ManeuverFactory mf;

    @Before
    public void setUp() throws Exception
    {
        mf = new ManeuverFactory(5000);
    }

    @Test
    public void testOneQueue()
    {
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(90, false));
        
        assertEquals(Math.PI,ManeuverQueue.getFinalHeading(mq),0.02f);
    }
    
    @Test
    public void testDoubleQueue()
    {        
        ManeuverQueue mq = new ManeuverQueue();
        
        Maneuver m = mf.createCourseHardL(90, false);
        ManeuverQueue.addManeuver(mq, m);
        
        float h = m.getCourse().getHeading(1);
        
        m = mf.createCourseHardL(h/(float)Math.PI * 180f, false);
        ManeuverQueue.addManeuver(mq, m);
        
        h = m.getCourse().getHeading(1);
        
        assertEquals(h, ManeuverQueue.getFinalHeading(mq),0.02f);      
        assertEquals(270f/180f * Math.PI, ManeuverQueue.getFinalHeading(mq), 0.02f);
    }
    
    @Test
    public void testTripleQueue()
    {     
        ManeuverQueue mq = new ManeuverQueue();
        
        Maneuver m = mf.createCourseHardL(90, false);
        ManeuverQueue.addManeuver(mq, m);
        float h = m.getCourse().getHeading(1);
        
        m = mf.createCourseHardL(h/(float)Math.PI * 180f, false);
        ManeuverQueue.addManeuver(mq, m);
        h = m.getCourse().getHeading(1);
        
        m = mf.createCourseHardL(h/(float)Math.PI * 180f, false);
        ManeuverQueue.addManeuver(mq, m);
        h = m.getCourse().getHeading(1);
        
        assertEquals(h, ManeuverQueue.getFinalHeading(mq), 0.02f);
        assertEquals(2 * Math.PI, h, 0.02f);        
    }
    
}
