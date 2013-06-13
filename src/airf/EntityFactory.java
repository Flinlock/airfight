package airf;

import airf.component.Heading;
import airf.component.Jet;
import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.component.Position;
import airf.component.Sprite;
import airf.jetstates.JetState;
import airf.pathing.Course;

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
    
    public static Entity createJet(World w, float x, float y, boolean fast, float vel, float h, JetType t, Course course, JetState state)
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
                
        ManeuverQueue pq = new ManeuverQueue();
        e.addComponent(pq);
        
        Path pth = new Path();
        pth.course = course;
        pth.x = x;
        pth.y = y;
        pth.totalTime = 0;
        e.addComponent(pth);
        
        Jet j = new Jet();
        j.state = state;
        j.fast = false;
        e.addComponent(j);

        return e;
    }


}
