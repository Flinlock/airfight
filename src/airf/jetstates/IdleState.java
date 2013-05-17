package airf.jetstates;

import airf.component.Heading;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.pathing.Course;
import airf.pathing.CourseFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleState implements JetState
{
    enum IntentType {HARDL, TEST, HARDR};
    IntentType intent;
    boolean actionPending;
    public boolean entityChanged = true;
    JetSystem system;
    
    public IdleState(JetSystem system)
    {
        this.system = system;
    }

    @Override 
    public boolean changed()
    {
        return entityChanged;
    }
    
    @Override
    public JetState update(Entity e)
    {
        synchronized(this)
        {
            if(actionPending)
            {
                Path p = new Path();
                Position pos = e.getComponent(Position.class);
                Velocity v = e.getComponent(Velocity.class);
                Heading h = e.getComponent(Heading.class);
                switch(intent)
                {
                    case HARDL:
                    {
                        float tmp = h.h;
                        tmp = 360 - tmp - 90;
                        
                        if(tmp < 0)
                            tmp += 360;
                        p.course = CourseFactory.createCourseHardL(tmp);  // arg rotation is counter clockwise
                        break;
                    }
                    case HARDR:
                    {
                        float tmp = h.h;
                        tmp = 360 - tmp - 90;
                        
                        if(tmp < 0)
                            tmp += 360;
                        p.course = CourseFactory.createCourseHardR(tmp);  // arg rotation is counter clockwise
                        break;
                    }
                    case TEST:
                        p.course = CourseFactory.createCourseTest(h.h);
                        break;                    
                }
                p.p = 0;
                p.v = (float)Math.sqrt(v.x*v.x + v.y*v.y);
                p.x = pos.x;
                p.y = pos.y;
                
                e.addComponent(p);
                
                actionPending = false;
                
                return new ManeuveringState(system);                
            }
            entityChanged = false;
        }
        return this;
    }

    @Override
    public void intentHardL()
    {
        synchronized(this)
        {     
            intent = IntentType.HARDL;
            actionPending = true;            
        }
    }

    @Override
    public void intentHardR()
    {
        synchronized(this)
        {     
            intent = IntentType.HARDR;
            actionPending = true;            
        }        
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
