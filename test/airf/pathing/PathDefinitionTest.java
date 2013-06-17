package airf.pathing;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import util.bezier.BezierCurve;

public class PathDefinitionTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void test()
    {
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 150);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation((float)Math.PI / 2);

        Point2D.Float pnt = path.getPoint(1.0f);
        
        assertEquals(-150, pnt.x, 0.01f);
        assertEquals(0, pnt.y, 0.01f);
        
        assertEquals(Math.PI, path.getHeading(0.5f), 0.01f);
    }
    
    @Test
    public void testHeadingOnFortyFiveLine()
    {
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(150, 150);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(150, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        
        assertEquals(45f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
    }
    
    @Test
    public void testHeadingOnFortyFiveLineRotated()
    {
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(150, 150);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(150, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation((float)Math.PI / 2);
        
        assertEquals(135f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
    }
    
    @Test
    public void testHeadingOnOnAxisStraightLine()
    {
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 150);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(0);        
        assertEquals(90f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
        path.setRotation((float)Math.PI / 2);
        assertEquals(180f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
        path.setRotation((float)Math.PI / 2 * 2);
        assertEquals(270f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
        path.setRotation((float)Math.PI / 2 * 3);
        assertEquals(360f / 180f * Math.PI, path.getHeading(0.5f), 0.1f);
    }
    
    @Test
    public void testHeadingOnCurvedLineEndPoints()
    {
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(150, 150);
        c.setControlPointOne(150, 0);
        c.setControlPointTwo(150, 0);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(0);        
        assertEquals(90f / 180f * Math.PI, path.getHeading(1f), 0.1f);
        path.setRotation((float)Math.PI / 2);
        assertEquals(90f / 180f * Math.PI, path.getHeading(0f), 0.1f);        
    }

}
