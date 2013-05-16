package airf.pathing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccelerationProfileTest
{
    AccelerationProfile p;
    @Before
    public void setUp() throws Exception
    {
        p = new AccelerationProfile();
    }

    @Test
    public void testDefaults()
    {
        assertEquals(0, p.getAcceleration(0.5f), 0.01f);
        assertEquals(1.0, p.getEndP(0.5f), 0.01f);
    }
    
    @Test
    public void testEndPoint()
    {   
        assertEquals(0, p.getAcceleration(1f), 0.01f);
        assertEquals(1.0, p.getEndP(1f), 0.01f);        
    }
    
    @Test
    public void testStartPoint()
    {
        assertEquals(0, p.getAcceleration(0), 0.01f);
        assertEquals(1.0, p.getEndP(0), 0.01f);        
    }

    @Test
    public void testAddDivider()
    {
        p.addDivider(0.5f, 1);
        
        assertEquals(0, p.getAcceleration(0), 0.01f);
        assertEquals(1, p.getAcceleration(0.5f), 0.01f);
        assertEquals(1, p.getAcceleration(1.0f), 0.01f);
        
        assertEquals(0.5, p.getEndP(0), 0.01f);
        assertEquals(1, p.getEndP(0.5f), 0.01f);
        assertEquals(1, p.getEndP(1), 0.01f);
    }
    
    @Test
    public void testAddTwoDividersSequentially()
    {
        p.addDivider(0.5f, 1);
        p.addDivider(0.75f, 2);

        assertEquals(0, p.getAcceleration(0), 0.01f);
        assertEquals(1, p.getAcceleration(0.5f), 0.01f);
        assertEquals(2, p.getAcceleration(1.0f), 0.01f);
        
        assertEquals(0.5f, p.getEndP(0), 0.01f);
        assertEquals(0.75f, p.getEndP(0.5f), 0.01f);
        assertEquals(1, p.getEndP(1), 0.01f);
    }
    
    @Test
    public void testAddTwoDividersNonSequentially()
    {
        p.addDivider(0.75f, 2);
        p.addDivider(0.5f, 1);

        assertEquals(0, p.getAcceleration(0), 0.01f);
        assertEquals(1, p.getAcceleration(0.5f), 0.01f);
        assertEquals(2, p.getAcceleration(1.0f), 0.01f);
        
        assertEquals(0.5f, p.getEndP(0), 0.01f);
        assertEquals(0.75f, p.getEndP(0.5f), 0.01f);
        assertEquals(1, p.getEndP(1), 0.01f);
    }
    
    @Test
    public void testOverwriteSection()
    {
        p.addDivider(0.5f, 1);
        p.addDivider(0.5f, 2);
        p.addDivider(0f, 3);
        
        assertEquals(2, p.getAcceleration(0.5f), 0.01f);
        assertEquals(3, p.getAcceleration(0f), 0.01f);
    }
}
