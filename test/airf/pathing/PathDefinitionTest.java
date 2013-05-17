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
        
        assertEquals(150, pnt.x, 0.01f);
        assertEquals(0, pnt.y, 0.01f);
    }

}
