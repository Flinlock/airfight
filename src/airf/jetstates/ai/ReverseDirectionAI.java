package airf.jetstates.ai;

import airf.component.Heading;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.jetstates.JetState;
import airf.pathing.ManeuverFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class ReverseDirectionAI implements JetState
{
    JetSystem system;
    boolean changed;
    boolean first;
    
    public ReverseDirectionAI(JetSystem sys)
    {
        System.out.println("Entering ReverseDirectionAI.");
        first = true;
        changed = true;
        system = sys;
    }

    @Override
    public JetState update(Entity e)
    {
        changed = false;
        Path p = system.getComponent(Path.class, e);
        Position pos = system.getComponent(Position.class, e);
        Velocity v = system.getComponent(Velocity.class, e);
        Heading h = system.getComponent(Heading.class, e);

        if(p.p >= 1.0)
        {
            if(first)
            {
                p.course = ManeuverFactory.createCourseHardL(h.h, false).getCourse();

                p.p = 0;
                p.v = (float)Math.sqrt(v.x*v.x + v.y*v.y);
                p.x = pos.x;
                p.y = pos.y;

                changed = true;
                first = false;

                e.addComponent(p);
            }
            else
            {
                e.removeComponent(p);
                return new IdleStateAI(system);
            }
        }
        
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

    @Override
    public boolean changed()
    {
        return changed;
    }

}
