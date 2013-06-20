package airf.states.jet.player;

import airf.component.Path;
import airf.pathing.ManeuverFactory;
import airf.states.JetState;
import airf.system.JetSystem;

import com.artemis.Entity;

public class ManeuveringState implements JetState
{
    JetSystem system;
    public boolean entityChanged = true;
    ManeuverFactory mf;
    
    public ManeuveringState(JetSystem s, ManeuverFactory mf)
    {
        system = s;
        this.mf = mf;
    }
    
    @Override 
    public boolean changed()
    {
        return entityChanged;
    }
    
    @Override
    public JetState update(Entity e)
    {
//        Path p = system.getComponent(Path.class, e);
//        if(p.p >= 1.0)
//        {
//            e.removeComponent(p);
//            return new IdleState(system,mf);
//        }
//        
//        entityChanged = false;
        return this;
    }

    @Override
    public void intentHardL()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void intentHardR()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void intentSoftL()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void intentSoftR()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void intentSpeedUp()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void intentSlowDown()
    {
        // TODO Auto-generated method stub

    }

}
