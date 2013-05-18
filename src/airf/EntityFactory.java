package airf;

import util.bezier.BezierCurve;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.Path;
import airf.component.Position;
import airf.component.Sprite;
import airf.component.Velocity;
import airf.jetstates.IdleState;
import airf.pathing.AccelerationProfile;
import airf.pathing.Course;
import airf.pathing.PathDefinition;
import airf.system.JetSystem;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{  
    public enum JetType
    {
        BLACK("jet_black"),
        WHITE("jet_white");
        
        private final String s;
        
        private JetType(String str)
        {
            s = str;
        }
        
        @Override
        public String toString()
        {
            return s;
        }
    }
    
    public static Entity createJet(World w, float x, float y, float vx, float vy, float h, JetType t, JetSystem sys)
    {
        Entity e = w.createEntity();

        Position p = new Position();
        p.x = x;
        p.y = y;
        e.addComponent(p);

        Sprite sprite = new Sprite();
        sprite.name = t.toString();
        sprite.scaleX = 0.5f;
        sprite.scaleY = 0.5f;
        sprite.rot = h;
        e.addComponent(sprite);
        
        Heading heading = new Heading();
        heading.h = h;
        e.addComponent(heading);

        Velocity v = new Velocity();
        v.x = vx;
        v.y = vy;
        e.addComponent(v);
        
        Jet j = new Jet();
        j.state = new IdleState(sys);
        j.fast = false;
        e.addComponent(j);

        return e;
    }

    public static Entity createTestJet(World w, float x, float y, float vx, float vy, float h, JetType t, JetSystem sys)
    {
        Entity e = w.createEntity();

        Position p = new Position();
        p.x = x;
        p.y = y;
        e.addComponent(p);

        Sprite sprite = new Sprite();
        sprite.name = t.toString();
        sprite.scaleX = 0.5f;
        sprite.scaleY = 0.5f;
        sprite.rot = h;
        e.addComponent(sprite);
        
        Heading heading = new Heading();
        heading.h = h;
        e.addComponent(heading);

        Velocity v = new Velocity();
        v.x = vx;
        v.y = vy;
        e.addComponent(v);
        
        Jet j = new Jet();
        j.state = new IdleState(sys);
        j.fast = false;
        e.addComponent(j);
        
        ///////
        
        BezierCurve curve = new BezierCurve();
        curve.setAnchorStart(0, 0);
        curve.setAnchorEnd(300, 300);
        curve.setControlPointOne(400, 0);
        curve.setControlPointTwo(400, 0);
        curve.calculateLength(0.01f);
        
        PathDefinition path = new PathDefinition(curve);
        AccelerationProfile profile = new AccelerationProfile();
        profile.addDivider(0.25f, 0.0001f);
        profile.addDivider(0.5f, -0.0001f);
        profile.addDivider(0.75f, 0);
        
        
        Path pathc = new Path();
        pathc.p = 0;
        pathc.v = (float)Math.sqrt(0.05f*0.05f + 0.05f*0.05f);
        pathc.x = 150;
        pathc.y = 150;
        pathc.course = new Course(path, profile);
        e.addComponent(pathc);
        
        return e;
    }
}
