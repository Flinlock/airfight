package airf.pathing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.bezier.BezierCurve;

public class CourseCalculatePTest
{
    PathDefinition path;
    AccelerationProfile prof;

    @Before
    public void setUp() throws Exception
    {
        BezierCurve c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(1, 1);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(1, 1);
        c.calculateLength(0.01f);
        
        path = new PathDefinition(c);
        prof = new AccelerationProfile();
    }
    
    @Test
    public void testHeading()
    {
        Course c = new Course(0.1f,  path, prof);
        
        assertEquals(45f / 180f * Math.PI, c.getHeading(0.5f), 0.01f);
    }

    @Test
    public void testCalculateP()
    {        
        Course course = new Course(0.1f, path, prof);
        
        assertEquals(0.1f, course.calculateP(1), 0.01f);
        assertEquals(0.5f, course.calculateP(5), 0.01f);
        assertEquals(1.0f, course.calculateP(10), 0.01f);
    }
    
    @Test
    public void testCalculateWithAccP()
    {
        prof.addDivider(0.5f, 0.2f);
        
        Course course = new Course(0.1f, path, prof);
        
        assertEquals(0.1f, course.calculateP(1), 0.01f);
        assertEquals(0.5f, course.calculateP(5), 0.01f);
        assertEquals(0.6f + 0.2f/2f, course.calculateP(6), 0.01f);
    }

}
