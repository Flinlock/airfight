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
    PathDefinition path1;
    AccelerationProfile profile1;
    PathDefinition path2;
    AccelerationProfile profile2;
    PathDefinition path3;
    AccelerationProfile profile3;
    PathDefinition path4;
    AccelerationProfile profile4;

    @Before
    public void setUp() throws Exception
    {
        BezierCurve c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(1, 1);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(1, 1);
        c.calculateLength(0.01f);
        
        path1 = new PathDefinition(c);        
        
        profile1 = new AccelerationProfile();

        ///////////////////////////////////
        
        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(1, 1);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(1, 1);
        c.calculateLength(0.01f);
        
        path2 = new PathDefinition(c);        
        
        profile2 = new AccelerationProfile();
        profile2.addDivider(0, 1);
                        
        ///////////////////////////////////
        
        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(10, 15);
        c.setControlPointOne(-5, 10);
        c.setControlPointTwo(15, -1);
        c.calculateLength(0.01f);
        
        path3 = new PathDefinition(c);        
        lComplex = path3.getLength();
        
        profile3 = new AccelerationProfile();
        profile3.addDivider(0, 1f);
        profile3.addDivider(0.33f, -1f);
        profile3.addDivider(0.66f, 0);


        ///////////////////////////////////

        c = new BezierCurve();
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(10, 15);
        c.setControlPointOne(-5, 10);
        c.setControlPointTwo(15, -1);
        c.calculateLength(0.01f);

        path4 = new PathDefinition(c);        
        lComplex = path4.getLength();

        profile4 = new AccelerationProfile();

    }

    @Test
    public void testTravelSimple()
    {
        float v = (float)Math.sqrt(2);
        cSimple = new Course(v, path1, profile1);  // a straight line path from (0,0) to (1,1) with no acceleration
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
        cSimpleAcc = new Course(1, path2, profile2);  // a straight line path from (0,0) to (1,1) with acceleration of 1
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
        cSimple = new Course(v, path1, profile1);  // a straight line path from (0,0) to (1,1) with no acceleration
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
        cComplex = new Course(500, path3, profile3);  // a curvy path with zero, positive, and negative acceleration
        CourseUpdate update = cComplex.calculateUpdate(0, 500, 1f);
        
        assertEquals(500, update.vNew, 0.01f);
        assertEquals(1.0f, update.pNew, 0.01f);
    }
    
    @Test
    public void testConstantVelocity()
    {
        cComplexConstant = new Course(50, path4, profile4);  // a curvy path with zero acceleration
        CourseUpdate u1 = cComplexConstant.calculateUpdate(0, 50, 0.25f);
        CourseUpdate u2 = cComplexConstant.calculateUpdate(0.5f, 50, 0.2f);
                        
        float v1 = u1.pNew * lComplex / 0.25f;
        float v2 = (u2.pNew - 0.5f) * lComplex / 0.2f;
        
        assertEquals(v1,v2,0.01f);
    }
    
    @Test
    public void testConstantVelocityLinearEstimate()
    {
        cComplexConstant = new Course(50, path4, profile4);  // a curvy path with zero acceleration
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
        cSimple = new Course(1, path1, profile1);  // a straight line path from (0,0) to (1,1) with no acceleration
        assertEquals(Math.PI / 4 + Math.PI, cSimple.getEndHeading(), 0.01f);
    }
}
