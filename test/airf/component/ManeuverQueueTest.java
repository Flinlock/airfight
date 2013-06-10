package airf.component;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import airf.jetstates.Maneuver;
import airf.pathing.ManeuverFactory;

public class ManeuverQueueTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testOneQueue()
    {
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(90, false));
        
        assertEquals(0,ManeuverQueue.getFinalHeading(mq),0.02f);
    }
    
    @Test
    public void testDoubleQueue()
    {        
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(90, false));
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(0, false));
        
        assertEquals((-90f+360f)/360f * 2 * Math.PI,ManeuverQueue.getFinalHeading(mq),0.02f);        
    }
    
    @Test
    public void testTripleQueue()
    {     
        ManeuverQueue mq = new ManeuverQueue();
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(90, false));
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(0, false));
        ManeuverQueue.addManeuver(mq, ManeuverFactory.createCourseHardL(270, false));
        
        assertEquals((180f)/360f * 2 * Math.PI,ManeuverQueue.getFinalHeading(mq),0.02f);        
    }

}
