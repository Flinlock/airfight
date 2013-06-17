package airf.pathing;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import airf.jetstates.Maneuver;

public class ManeuverFactoryTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testLengths()
    {
        ManeuverFactory mf = new ManeuverFactory(5000);
        Maneuver m1 = mf.createCourseStraight(0, false);
        Point2D.Float p = m1.getCourse().getPoint(1.0f);
        float l = m1.getCourse().getLength();
    }

    @Test
    public void testLefts()
    {
        ManeuverFactory mf = new ManeuverFactory(5000);
        
        Maneuver m = mf.createCourseHardL(0, false);
        Point2D.Float pnt = m.getCourse().getPoint(1f);
        
        assertEquals(1, pnt.y / pnt.x, 0.01f);
        
        m = mf.createCourseHardR(0, false);
        pnt = m.getCourse().getPoint(1f);
        
        assertEquals(-1, pnt.y / pnt.x, 0.01f);
        
        m = mf.createCourseSoftL(0, false);
        pnt = m.getCourse().getPoint(1f);
        
        assertTrue(pnt.y / pnt.x > 0);
        
        m = mf.createCourseSoftR(0, false);
        pnt = m.getCourse().getPoint(1f);
        
        assertTrue(pnt.y / pnt.x < 0);
    }
}
