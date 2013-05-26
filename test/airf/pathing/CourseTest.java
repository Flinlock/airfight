package airf.pathing;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import util.bezier.BezierCurve;

public class CourseTest
{
    Course cSimple;
    Course cSimpleAcc;
    Course cComplex;
    Course cComplexConstant;
    float lComplex;

    @Before
    public void setUp() throws Exception
    {
        BezierCurve c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(1, 1);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(1, 1);
        c.calculateLength(0.01f);
        
        PathDefinition path = new PathDefinition(c);        
        
        AccelerationProfile profile = new AccelerationProfile();
                
        cSimple = new Course(path, profile);  // a straight line path from (0,0) to (1,1) with no acceleration

        ///////////////////////////////////
        
        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(1, 1);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(1, 1);
        c.calculateLength(0.01f);
        
        path = new PathDefinition(c);        
        
        profile = new AccelerationProfile();
        profile.addDivider(0, 1);
                
        cSimpleAcc = new Course(path, profile);  // a straight line path from (0,0) to (1,1) with acceleration of 1
        
        ///////////////////////////////////
        
        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(10, 15);
        c.setControlPointOne(-5, 10);
        c.setControlPointTwo(15, -1);
        c.calculateLength(0.01f);
        
        path = new PathDefinition(c);        
        lComplex = path.getLength();
        
        profile = new AccelerationProfile();
        profile.addDivider(0, 1f);
        profile.addDivider(0.33f, -1f);
        profile.addDivider(0.66f, 0);

        cComplex = new Course(path, profile);  // a curvy path with zero, positive, and negative acceleration

        ///////////////////////////////////

        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(10, 15);
        c.setControlPointOne(-5, 10);
        c.setControlPointTwo(15, -1);
        c.calculateLength(0.01f);

        path = new PathDefinition(c);        
        lComplex = path.getLength();

        profile = new AccelerationProfile();

        cComplexConstant = new Course(path, profile);  // a curvy path with zero acceleration
    }

    @Test
    public void testTravelSimple()
    {
        float v = (float)Math.sqrt(2);
        CourseUpdate update = cSimple.calculateUpdate(0, v, 0.5f);
        
        assertEquals(0.5f, update.pNew, 0.01f);
        assertEquals(v, update.vNew, 0.01f);
        assertEquals(0.5f, update.pDelta, 0.01f);
        assertEquals(0, update.vDelta, 0.01f);
        assertEquals(0, update.dT, 0.01f);
    }
    
    @Test
    public void testTravelWithAcceleration()
    {
        float L = (float)Math.sqrt(2);
        CourseUpdate update = cSimpleAcc.calculateUpdate(0, 1, 0.5f);
        
        assertEquals((0 + 1*0.5f + 1/2f*0.5f*0.5f)/L, update.pNew, 0.01f);
        assertEquals(1 + 1*0.5f, update.vNew, 0.01f);
        assertEquals(0, update.pNew - update.pDelta, 0.01f);
        assertEquals(1, update.vNew - update.vDelta, 0.01f);
    }
    
    @Test
    public void testTravelOffSimple()
    {
        float v = (float)Math.sqrt(2);
        CourseUpdate update = cSimple.calculateUpdate(0, v, 1.5f);
        
        assertEquals(1.0f, update.pNew, 0.01f);
        assertEquals(v, update.vNew, 0.01f);
        assertEquals(1.0f, update.pDelta, 0.01f);
        assertEquals(0, update.vDelta, 0.01f);
        assertEquals(0.5f, update.dT, 0.01f);
    }
    
    @Test
    public void testTravelComplex()
    {
        CourseUpdate update = cComplex.calculateUpdate(0, 500, 1f);
        
        assertEquals(500, update.vNew, 0.01f);
        assertEquals(1.0f, update.pNew, 0.01f);
    }
    
    @Test
    public void testConstantVelocity()
    {
        CourseUpdate u1 = cComplexConstant.calculateUpdate(0, 50, 0.25f);
        CourseUpdate u2 = cComplexConstant.calculateUpdate(0.5f, 50, 0.2f);
                        
        float v1 = u1.pNew * lComplex / 0.25f;
        float v2 = (u2.pNew - 0.5f) * lComplex / 0.2f;
        
        assertEquals(v1,v2,0.01f);
    }
    
    @Test
    public void testConstantVelocityLinearEstimate()
    {
        CourseUpdate u1 = cComplexConstant.calculateUpdate(0, 50, 0.003f);
        CourseUpdate u2 = cComplexConstant.calculateUpdate(0.5f, 50, 0.003f);
        
        Point2D.Float pS, pE;
        
        pS = cComplexConstant.getPoint(0);
        pE = cComplexConstant.getPoint(u1.pNew);
        float v1 = (float)Math.sqrt(Math.pow(pE.x - pS.x, 2) + 
                             Math.pow(pE.y - pS.y, 2));
        
        pS = cComplexConstant.getPoint(0.5f);
        pE = cComplexConstant.getPoint(u2.pNew);
        float v2 = (float)Math.sqrt(Math.pow(pE.x - pS.x, 2) + 
                Math.pow(pE.y - pS.y, 2));
        
        assertEquals(v1,v2,0.01f);
    }
    
    @Test
    public void testGetEndHeading()
    {
        assertEquals(Math.PI / 4 + Math.PI, cSimple.getEndHeading(), 0.01f);
    }
}
