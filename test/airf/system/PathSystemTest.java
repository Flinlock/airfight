package airf.system;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import util.bezier.BezierCurve;
import airf.component.Heading;
import airf.component.Path;
import airf.component.Position;
import airf.pathing.AccelerationProfile;
import airf.pathing.Course;
import airf.pathing.Maneuver;
import airf.pathing.PathDefinition;
import airf.pathing.Maneuver.AccType;

import com.artemis.Entity;
import com.artemis.World;

public class PathSystemTest
{
    World world;
    Entity e;
    BezierCurve c;
    PathDefinition path;
    AccelerationProfile profile;
    Course crs;
    Path p;
    Position pos;
    Heading h;

    @Before
    public void setUp() throws Exception
    {
        world = new World();
        world.setSystem(new PathSystem());        
        world.initialize();
        
        e = world.createEntity();
        p = new Path();
        e.addComponent(p);
        pos = new Position();
        e.addComponent(pos);
        h =  new Heading();
        e.addComponent(h);
        e.addToWorld();
        
        c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 150);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 150);
        c.calculateLength(0.01f);

        path = new PathDefinition(c);
        path.setRotation(0);
        
        profile = new AccelerationProfile();
        
        crs = new Course(0.1f, path, profile);
        p.course = new Maneuver(crs, AccType.NONE);
        p.x = 0;
        p.y = 0;
        p.totalTime = 0;
    }
    
    @Test
    public void testStraightPathExecution()
    {
        world.setDelta(10);
        world.process();
        
        assertEquals(0, pos.x, 0.01f);
        assertEquals(150, pos.y, 0.01f);
        assertEquals(0, h.h, 0.01f);
    }
    
    @Test
    public void testRotatedStraightPathExecution()
    {
        path.setRotation((float)Math.PI / 2);
        world.setDelta(10);
        world.process();
        
        assertEquals(-150, pos.x, 0.01f);
        assertEquals(0, pos.y, 0.01f);
        assertEquals(Math.PI / 2, h.h, 0.01f);
    }
    
    @Test
    public void testStraightAcceleratedPathExecution()
    {
        profile.addDivider(0, 0.1f);
        
        world.setDelta(1);
        world.process();
        
        assertEquals(0, pos.x, 0.01f);
        // d = d_0 + v*t + 1/2*a*t^2
        assertEquals((0.1f*1 + 0.5f*0.1f*1)*150, pos.y, 0.01f);
    }

}
