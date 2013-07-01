package airf;

import airf.component.FiringEnvelope;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.component.Position;
import airf.component.Radar;
import airf.component.Select;
import airf.component.Sprite;
import airf.pathing.Maneuver;
import airf.states.JetState;
import airf.states.damage.NoDamageState;
import airf.states.selectable.Unselected;

import com.artemis.ComponentType;
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
    
    public static Entity createJet(World w, float x, float y, boolean fast, JetType t, Maneuver m, JetState state, int queueSize)
    {
        Entity e = w.createEntity();

        Position p = new Position();
        p.x = x;
        p.y = y;
        e.addComponent(p);

        Sprite sprite = new Sprite();
        sprite.name = t.toString();
        sprite.scaleX = 0.4f;
        sprite.scaleY = 0.4f;
        sprite.rot = 0;
        e.addComponent(sprite);
        
        Heading heading = new Heading();
        e.addComponent(heading);
                
        ManeuverQueue pq = new ManeuverQueue(queueSize);
        e.addComponent(pq);
        
        Path pth = new Path();
        pth.course = m;
        pth.x = x;
        pth.y = y;
        pth.totalTime = 0;
        pth.h = 0;
        e.addComponent(pth);
        
        Jet j = new Jet();
        j.state = state;
        j.fast = false;
        j.stateDamage = new NoDamageState();
        e.addComponent(j);
        
        Radar r = new Radar();
        e.addComponent(r);
        
        FiringEnvelope fe = new FiringEnvelope();
        fe.angleLeft = (float)Math.PI/4;
        fe.angleRight = -(float)Math.PI/4f + (float)Math.PI*2f;
        fe.rangeLongSquared = 500 * 500;
        e.addComponent(fe);
        
        Select s = new Select();
        Entity eHl = w.createEntity();
        eHl.addToWorld();
        s.state = new Unselected(e, ComponentType.getTypeFor(Sprite.class), 
                ComponentType.getTypeFor(Position.class), eHl);
        e.addComponent(s);

        return e;
    }


}
