package airf.system;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import util.bezier.BezierCurve;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.pathing.AccelerationProfile;
import airf.pathing.Course;
import airf.pathing.PathDefinition;

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
    Velocity v;
    Position pos;

    @Before
    public void setUp() throws Exception
    {
        world = new World();
        world.setSystem(new PathSystem());        
        world.initialize();
        
        e = world.createEntity();
        p = new Path();
        e.addComponent(p);
        v = new Velocity();
        e.addComponent(v);
        pos = new Position();
        e.addComponent(pos);
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
        
        crs = new Course(path, profile);
        p.course = crs;
        p.p = 0;
        p.v = 0.1f;
        p.x = 0;
        p.y = 0;
    }
    
    @Test
    public void testStraightPathExecution()
    {
        world.setDelta(1500);
        world.process();
        
        assertEquals(0, pos.x, 0.01f);
        assertEquals(150, pos.y, 0.01f);
        assertEquals(0.1f, v.y, 0.01f);
        assertEquals(0f, v.x, 0.01f);
    }
    
    @Test
    public void testRotatedStraightPathExecution()
    {
        path.setRotation((float)Math.PI / 2);
        world.setDelta(1500);
        world.process();
        
        assertEquals(150, pos.x, 0.01f);
        assertEquals(0, pos.y, 0.01f);
        assertEquals(0.1f, v.x, 0.01f);
        assertEquals(0f, v.y, 0.01f);
    }

}
