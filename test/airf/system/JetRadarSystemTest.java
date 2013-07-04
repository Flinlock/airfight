package airf.system;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import airf.component.FiringEnvelope;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.Position;
import airf.component.Radar;

import com.artemis.Entity;
import com.artemis.World;

public class JetRadarSystemTest
{
    World w;
    Entity eAttacker;
    Position pAttacker;
    Heading hAttacker;
    Radar rAttacker;
    FiringEnvelope feAttacker;
    
    Entity eTarget;
    Position pTarget;

    @Before
    public void setUp() throws Exception
    {
        w = new World();

        w.setSystem(new JetRadarSystem());
        
        eAttacker = w.createEntity();
        eTarget = w.createEntity();        
        
        Jet j = new Jet();
        pAttacker = new Position();
        hAttacker = new Heading();
        rAttacker = new Radar();
        feAttacker = new FiringEnvelope();
        
        eAttacker.addComponent(pAttacker);
        eAttacker.addComponent(hAttacker);
        eAttacker.addComponent(rAttacker);
        eAttacker.addComponent(feAttacker);
        eAttacker.addComponent(j);
        eAttacker.addToWorld();
        
        pTarget = new Position();
        j = new Jet();
        
        eTarget.addComponent(pTarget);
        eTarget.addComponent(j);
        eTarget.addToWorld();
        
        w.initialize();
        w.setDelta(100);

        pTarget.x = 5;
        pTarget.y = 0;
        
        hAttacker.h = 0;
        pAttacker.x = 0;
        pAttacker.y = 0;
        feAttacker.angleRight = -(float)Math.PI/4f + (float)Math.PI*2f;
        feAttacker.angleLeft = (float)Math.PI/4;
        feAttacker.rangeLongSquared = 100f;
    }

    @Test
    public void testAttackerRotation()
    {
        w.process(false);
        assertEquals(1, rAttacker.targets.size());

        hAttacker.h = 3.14f / 2f; // rotate attacker to point north
        pTarget.x = 0; // move target to be bearing straight north
        pTarget.y = 5;
        w.process(false);
        assertEquals(1, rAttacker.targets.size());
        
        hAttacker.h = 3.14f; // rotate the attacker to point west 
        pTarget.x = -5;  // move target to be bearing straight west
        pTarget.y = 0;
        w.process(false);
        assertEquals(1, rAttacker.targets.size());
        
        hAttacker.h = 3 * 3.14f / 2f; // rotate the attacker to point straight south
        pTarget.x = 0; // move the target to be bearing straight south
        pTarget.y = -5;
        w.process(false);
        assertEquals(1, rAttacker.targets.size());    
        
        pTarget.y = 5; // move target outside of envelope
        w.process(false);
        assertEquals(0, rAttacker.targets.size());
    }
    
    @Test
    public void testTargetAtEdgeOfEnvelope()
    {
        pTarget.x = 1;
        pTarget.y = 1;
        w.process(false);
        assertEquals(1, rAttacker.targets.size());
        
        pTarget.x = 1;
        pTarget.y = 1.01f;
        w.process(false);
        assertEquals(0, rAttacker.targets.size());
        
        pTarget.x = 1;
        pTarget.y = -1;
        w.process(false);
        assertEquals(1, rAttacker.targets.size());
        
        pTarget.x = 1;
        pTarget.y = -1.01f;
        w.process(false);
        assertEquals(0, rAttacker.targets.size());
    }
    
}

