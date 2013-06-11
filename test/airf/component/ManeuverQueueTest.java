package airf.component;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import airf.Constants;
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
        
        assertEquals(0,ManeuverQueue.getFinalHeading(mq),0.02f);
    }
    
    @Test
    public void testDoubleQueue()
    {        
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(90, false));
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(0, false));
        
        assertEquals((-90f+360f)/360f * 2 * Math.PI,ManeuverQueue.getFinalHeading(mq),0.02f);        
    }
    
    @Test
    public void testTripleQueue()
    {     
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(90, false));
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(0, false));
        ManeuverQueue.addManeuver(mq, mf.createCourseHardL(270, false));
        
        assertEquals((180f)/360f * 2 * Math.PI,ManeuverQueue.getFinalHeading(mq),0.02f);        
    }
    
}
