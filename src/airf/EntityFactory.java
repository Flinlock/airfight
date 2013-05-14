package airf;

import airf.component.Heading;
import airf.component.Position;
import airf.component.Sprite;
import airf.component.Velocity;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{  
    enum JetType
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
    
    public static Entity createJet(World w, float x, float y, float vx, float vy, float h, JetType t)
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
        v.x = 1*vx;
        v.y = 1*vy;
        e.addComponent(v);

        return e;
    }
}
