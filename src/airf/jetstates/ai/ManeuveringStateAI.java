package airf.jetstates.ai;

import com.artemis.Entity;

import airf.component.Path;
import airf.jetstates.IdleState;
import airf.jetstates.JetState;
import airf.system.JetSystem;

public class ManeuveringStateAI implements JetState
{
    JetSystem system;
    public boolean entityChanged = true;

    public ManeuveringStateAI(JetSystem s)
    {
        System.out.println("Entering ManeuveringStateAI.");
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
//        Path p = system.getComponent(Path.class, e);
//        if(p.p >= 1.0)
//        {
//            e.removeComponent(p);
//            return new IdleStateAI(system);
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
