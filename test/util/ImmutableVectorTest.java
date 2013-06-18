package util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImmutableVectorTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testRotationIsCounterClockwise()
    {
        ImmutableVector v = new ImmutableVector(1, 0);
        
        assertEquals(1.0f, v.rot((float)Math.PI/2).y, 0.01f);
        assertEquals(0f, v.rot((float)Math.PI/2).x, 0.01f);
    }

}
