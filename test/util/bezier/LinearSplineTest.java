package util.bezier;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;


import util.ImmutableVector;

public class LinearSplineTest
{
    LinearSpline s;
    
    @Before
    public void setUp() throws Exception
    {
        s = new LinearSpline();
        s.addVertex(new Point2D.Float(0, 0));
        s.addVertex(new Point2D.Float(1, 1));
        s.addVertex(new Point2D.Float(2, 0));
    }

    @Test
    public void testGetPoint()
    {        
        ImmutableVector p = s.getPoint(0);
        assertEquals(0, p.x, 0.01f);
        assertEquals(0, p.y, 0.01f);
        
        p = s.getPoint(0.5f);
        assertEquals(1, p.x, 0.01f);
        assertEquals(1, p.y, 0.01f);
        
        p = s.getPoint(1.0f);
        assertEquals(2, p.x, 0.01f);
        assertEquals(0, p.y, 0.01f);
    }
    
    @Test
    public void testGetSlope()
    {
        assertEquals(1, s.getSlope(0), 0.01f);
        assertEquals(-1, s.getSlope(0.5f), 0.01f);
        assertEquals(-1, s.getSlope(1.0f), 0.01f);
    }

}
