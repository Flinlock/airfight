package airf.jetstates;

import airf.component.Path;
import airf.system.JetSystem;

import com.artemis.Entity;

public class ManeuveringState implements JetState
{
    JetSystem system;
    public boolean entityChanged = true;
    
    public ManeuveringState(JetSystem s)
    {
        system = s;
    }
    
    @Override 
    public boolean changed()
    {
        return entityChanged;
    }
    
    @Override
    public JetState update(Entity e)
    {
        Path p = system.getPath(e);
        if(p.p >= 1.0)
        {
            e.removeComponent(p);
            return new IdleState(system);
        }
        
        entityChanged = false;
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
