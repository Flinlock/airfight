package airf.pathing;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;


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
    public void testTurns()
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
    
    @Test
    public void testAcceleration()
    {
        ManeuverFactory mf = new ManeuverFactory(5000);
        
        Maneuver mAcc = mf.createCourseAccel(0);
        Maneuver mSlow = mf.createCourseStraight(0, false);
        Maneuver mFast = mf.createCourseStraight(0, true);
        
        float lenAcc = mAcc.getCourse().getLength();
        float lenSlow = mSlow.getCourse().getLength();
        float lenFast = mFast.getCourse().getLength();
        
        float vFast = lenFast / 5000f;
        float vSlow = lenSlow / 5000f;
        float acc = (vFast - vSlow) / 5000f;
        float accN = acc / lenAcc;
        float vSlowN = vSlow / lenAcc;
        
        // Acceleration maneuver must ensure these two properties
        // vSlow + 5000f*acc = vFast
        // acc = (vFast - vSlow) / 5000f

        // This constrains the length of the maneuver to be
        // lenAcc = vSlow*5000f + acc*0.5f*5000*5000
        assertEquals(vSlow*5000f + acc*0.5f*5000f*5000f, lenAcc, 0.01f);
        
        // We must travel the whole length of the maneuver by the period
        assertEquals(1f, mAcc.getCourse().calculateP(5000), 0.01f);
        // but no additional
        assertTrue(mAcc.getCourse().calculateP(4999) < 1.0f);
    }
    
    @Test
    public void testDeceleration()
    {
        ManeuverFactory mf = new ManeuverFactory(5000);
        
        Maneuver mDecel = mf.createCourseDecel(0);
        Maneuver mSlow = mf.createCourseStraight(0, false);
        Maneuver mFast = mf.createCourseStraight(0, true);
        
        float lenDecel = mDecel.getCourse().getLength();
        float lenSlow = mSlow.getCourse().getLength();
        float lenFast = mFast.getCourse().getLength();
        
        float vFast = lenFast / 5000f;
        float vSlow = lenSlow / 5000f;
        float acc = (vFast - vSlow) / 5000f;
        
        // Acceleration maneuver must ensure these two properties
        // vFast + 5000f*acc = vSlow
        // acc = (vSlow - vFast) / 5000f

        // This constrains the length of the maneuver to be
        // lenDecel = vFast*5000f + acc*0.5f*5000*5000
        assertEquals(vSlow*5000f + acc*0.5f*5000f*5000f, lenDecel, 0.01f);
        
        // We must travel the whole length of the maneuver by the period
        assertEquals(1f, mDecel.getCourse().calculateP(5000), 0.01f);
        // but no additional
        assertTrue(mDecel.getCourse().calculateP(4999) < 1.0f);        
    }
    
}
