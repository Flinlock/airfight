package airf.jetstates.ai;

import airf.component.Heading;
import airf.component.Jet;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.jetstates.JetState;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleStateAI implements JetState
{
    JetSystem system;
    
    public IdleStateAI(JetSystem sys)
    {
        system = sys;
    }
    
    @Override
    public JetState update(Entity e)
    {
        Path p = new Path();
        Position pos = system.getComponent(Position.class, e);
        Velocity v = system.getComponent(Velocity.class, e);
        Heading h = system.getComponent(Heading.class, e);
        Jet j = system.getComponent(Jet.class, e);
        
        float hAdjusted = h.h;
        hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
        
        if(hAdjusted < 0)
            hAdjusted += 360;
        
        // Maneuver to keep the jet on the screen
        
        // safe condition is if a jet can reverse it's direction of travel (e.g. two successive hard L's or hard R's)
        // without going off the screen
        //      A jet cannot escape going off the edge of the screen iff the safe condition is not true.
        
        // Look into the future, if not maneuvering, look at the jet's position at some dT>0 into the future, if about to maneuver look where the jet will be at
        // the end of the maneuver, if this position is not safe, maneuver before dT or do not choose that maneuver.
                
        return this;
    }

    @Override
    public void intentHardL()
    {
    }

    @Override
    public void intentHardR()
    {
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

    @Override
    public boolean changed()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
