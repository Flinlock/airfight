package airf.jetstates;

import airf.component.Heading;
import airf.component.Jet;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.pathing.CourseFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleState implements JetState
{
    enum IntentType {HARDL, TEST, HARDR, SOFTR, SOFTL, ACCEL, DEACCEL};
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
                Position pos = system.getComponent(Position.class, e);
                Velocity v = system.getComponent(Velocity.class, e);
                Heading h = system.getComponent(Heading.class, e);
                Jet j = system.getComponent(Jet.class, e);
                
                actionPending = false;
                
                float hAdjusted = h.h;
                hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
                
                if(hAdjusted < 0)
                    hAdjusted += 360;
                
                switch(intent)
                {
                    case HARDL:
                    {
                        p.course = CourseFactory.createCourseHardL(hAdjusted);
                        break;
                    }
                    case HARDR:
                    {
                        p.course = CourseFactory.createCourseHardR(hAdjusted);
                        break;
                    }
                    case SOFTR:
                    {
                        p.course = CourseFactory.createCourseSoftR(hAdjusted); 
                        break;
                    }
                    case SOFTL:
                    {
                        p.course = CourseFactory.createCourseSoftL(hAdjusted);
                        break;
                    }
                    case ACCEL:
                    {
                        if(!j.fast)
                        {
                            j.fast = true;
                            p.course = CourseFactory.createCourseAccel(hAdjusted);
                        }
                        else
                            return this;
                        
                        break;
                    }
                    case DEACCEL:
                    {
                        if(j.fast)
                        {
                            j.fast = false;
                            p.course = CourseFactory.createCourseDeaccel(hAdjusted);
                        }
                        else
                            return this;
                        
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
        synchronized(this)
        {     
            intent = IntentType.SOFTL;
            actionPending = true;            
        }        
    }

    @Override
    public void intentSoftR()
    {
        synchronized(this)
        {     
            intent = IntentType.SOFTR;
            actionPending = true;            
        }        
    }

    @Override
    public void intentSpeedUp()
    {
        synchronized(this)
        {     
            intent = IntentType.ACCEL;
            actionPending = true;            
        }        
    }

    @Override
    public void intentSlowDown()
    {
        synchronized(this)
        {     
            intent = IntentType.DEACCEL;
            actionPending = true;            
        }        
    }

}
