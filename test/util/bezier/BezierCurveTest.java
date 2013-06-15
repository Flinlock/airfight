package util.bezier;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.ImmutableVector;

public class BezierCurveTest
{
    BezierCurve c;

    @Before
    public void setUp() throws Exception
    {
        c = new BezierCurve();
    }

    @Test
    public void testFlatnessOfHorizontalLine()
    {
        c.setAnchorStart(-4, 0);
        c.setAnchorEnd(4, 0);
        c.setControlPointOne(0.4f, 10f);
        c.setControlPointTwo(0.6f, 8f);
        
        assertEquals(10f, c.flattness(), 0.01f);
    }
    
    @Test
    public void testFlatnessOfVerticalLine()
    {
        c.setAnchorStart(0, -2);
        c.setAnchorEnd(0, 5);
        c.setControlPointOne(10f, 0.4f);
        c.setControlPointTwo(4f, 0.6f);
        
        assertEquals(10f, c.flattness(), 0.01f);        
    }
    
    @Test
    public void testFlatnessOfFortyFiveLine()
    {
        c.setAnchorStart(1, 0);
        c.setAnchorEnd(9, 8);
        c.setControlPointOne(1f, 8f);
        c.setControlPointTwo(0f, 9f);
        
        assertEquals(Math.sqrt(25+25), c.flattness(), 0.01f);        
    }
    
    @Test
    public void testTangentAngleOfStraightFortyFiveLine()
    {
        c.setAnchorStart(1, 0);
        c.setAnchorEnd(9, 8);
        c.setControlPointOne(1f, 0);
        c.setControlPointTwo(9f, 8f);
        c.calculateLength(0.01f);
        
        assertEquals(45f*Math.PI / 180f, c.getTangentAngle(0.1f), 0.01f);         
    }
    
    @Test
    public void testTangentAngleOfStraightOnXAxisLine()
    {
        c.setAnchorStart(1, 0);
        c.setAnchorEnd(9, 0);
        c.setControlPointOne(1f, 0);
        c.setControlPointTwo(9f, 0f);
        c.calculateLength(0.01f);
        
        assertEquals(0, c.getTangentAngle(0.1f), 0.01f);
        

        c.setAnchorStart(1, 0);
        c.setAnchorEnd(-9, 0);
        c.setControlPointOne(1f, 0);
        c.setControlPointTwo(-9f, 0f);
        c.calculateLength(0.01f);
        
        assertEquals(180f/180f*Math.PI, c.getTangentAngle(0.1f), 0.01f);
    }
    
    @Test
    public void testBezierSplit()
    {
        c.setAnchorEnd(4, 3);
        c.setControlPointOne(-2, 5);
        c.setControlPointTwo(6, 15);
        
        float t = 0.5f;
        
        float Ax = (1-t)*0f + t*(-2f);
        float Ay = (1-t)*0f + t*(5f);
        
        float Cx = (1-t)*6f + t*(4f);
        float Cy = (1-t)*15f + t*(3f);
        
        float Mx = (1-t)*(1-t)*0 + 2*(1-t)*t*(-2f) + t*t*6f;
        float My = (1-t)*(1-t)*0 + 2*(1-t)*t*(5f) + t*t*15f;
        
        float Nx = (1-t)*(1-t)*(-2f) + 2*(1-t)*t*(6f) + t*t*4f;
        float Ny = (1-t)*(1-t)*(5f) + 2*(1-t)*t*(15f) + t*t*3f;
        
        float Px = (1-t)*(1-t)*(1-t)*0 + 3*(1-t)*(1-t)*t*(-2f) + 3*(1-t)*t*t*6f + t*t*t*4;
        float Py = (1-t)*(1-t)*(1-t)*0 + 3*(1-t)*(1-t)*t*(5f) + 3*(1-t)*t*t*15f + t*t*t*3;
        
        List<BezierCurve> l = c.split(0.5f);
        
        assertEquals(Px, l.get(0).p2x, 0.01f);
        assertEquals(Py, l.get(0).p2y, 0.01f);

        assertEquals(Px, l.get(1).p1x, 0.01f);
        assertEquals(Py, l.get(1).p1y, 0.01f);
        
        assertEquals(Ax, l.get(0).c1x, 0.01f);
        assertEquals(Ay, l.get(0).c1y, 0.01f);
        
        assertEquals(Cx, l.get(1).c2x, 0.01f);
        assertEquals(Cy, l.get(1).c2y, 0.01f);
        
        assertEquals(Mx, l.get(0).c2x, 0.01f);
        assertEquals(My, l.get(0).c2y, 0.01f);
        
        assertEquals(Nx, l.get(1).c1x, 0.01f);
        assertEquals(Ny, l.get(1).c1y, 0.01f);
    }
    
    @Test
    public void testApprox()
    {
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(4, 0);
        c.setControlPointOne(2, 2);
        c.setControlPointTwo(-2, -2);
        
        LinearSpline ret = c.generateApproximation(0.9f);
        
        assertEquals(5, ret.getNumberOfSplines());
    }
    
    @Test
    public void testBezierEvaluationStartPoint()
    {
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(400, 400);
        c.setControlPointOne(200, 200);
        c.setControlPointTwo(200, 200);
        c.calculateLength(0.01f);
        
        ImmutableVector pnt = c.getPoint(0);
        assertEquals(0, pnt.x, 0.01f);
        assertEquals(0, pnt.y, 0.01f);
    }
    
    @Test
    public void testBezierEvaluationEndPoint()
    {
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(400, 400);
        c.setControlPointOne(200, 200);
        c.setControlPointTwo(200, 200);
        c.calculateLength(0.01f);
        
        ImmutableVector pnt = c.getPoint(1f);
        assertEquals(400, pnt.x, 0.01f);
        assertEquals(400, pnt.y, 0.01f);
    }
    
    @Test
    public void testBezierEvaluationMidPoint()
    {
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(400, 400);
        c.setControlPointOne(200, 200);
        c.setControlPointTwo(200, 200);
        c.calculateLength(0.01f);
        
        ImmutableVector pnt = c.getPoint(0.5f);
        assertEquals((0 + 3*200 + 3*200 + 400)/8, pnt.x, 0.01f);
        assertEquals((0 + 3*200 + 3*200 + 400)/8, pnt.y, 0.01f);
    }
}
