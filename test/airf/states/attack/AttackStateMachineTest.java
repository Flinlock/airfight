package airf.states.attack;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import airf.component.FiringEnvelope;
import airf.component.Radar;

import com.artemis.Entity;
import com.artemis.World;

public class AttackStateMachineTest
{
    World w;
    Entity eTarg;
    Entity eAttacker;
    AttackState state;

    
    @Before
    public void setUp() throws Exception
    {
        w = new World();
        w.setDelta(33);
        eTarg = w.createEntity();
        eAttacker = w.createEntity();
    }

    @Test
    public void testNoTargetGunsTransitions()
    {
//        state = new NoTargetGuns(sys, eAttacker);
        
        // assertEquals(AttackModeGuns.class, state.intentSetTarget(eTarg).getClass());
    }
 
//    @Test
//    public void testStateTransitions()
//    {   
//        assertEquals(NoTargetGuns.class, state.intentActivateWeapon(WeaponType.GUNS).getClass());
//        
//        boolean thrown = false;
//        try
//        {
//            state.intentActivateWeapon(WeaponType.MISSILES);
//        }
//        catch(IllegalArgumentException ex)
//        {
//            thrown = true;
//        }
//        
//        assertTrue(thrown);
//        
//        state = state.intentSetTarget(eTarg);
//        assertEquals(AttackModeGuns.class, state.getClass());
//
//        FiringEnvelope env = eAttacker.getComponent(FiringEnvelope.class); 
//        assertNotNull(env);
//        
//        Radar r = eAttacker.getComponent(Radar.class);
//        assertNotNull(r);
//        
//        r.targets.add(eTarg);
//        
//        state = state.update();
//        
//        assertEquals(TrackingModeGuns.class, state.getClass());
//        
//        int i;
//        for(i = 0; i < 5*60; i++)
//        {
//            state = state.update();
//            if(state.getClass() == FiringModeGuns.class)
//                break;            
//        }
//        
//        assertEquals(FiringModeGuns.class, state.getClass());
//        
//        state = state.update();
//        
//        assertEquals(TrackingModeGuns.class, state.getClass());
//        
//        r.targets.clear();
//        
//        state = state.update();
//        
//        assertEquals(AttackModeGuns.class, state.getClass());
//        
//        r.lost.add(eTarg);
//        
//        state = state.update();
//        
//        assertEquals(NoTargetGuns.class, state.getClass());
//    }

}
