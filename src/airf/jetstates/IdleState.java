package airf.jetstates;

import airf.Constants;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.pathing.ManeuverFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleState implements JetState
{
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
    
    private float getNextHeading(ManeuverQueue mq, Path p)
    {
        float fH = ManeuverQueue.getFinalHeading(mq);  // need to get the heading of the jet at the end of the last maneuver on the queue
        if(fH == -1)
            fH = p.course.getEndHeading();
         
        return fH / (float)Math.PI * 180f;
    }
    
    @Override
    public JetState update(Entity e)
    {
        synchronized(this)
        {
            if(actionPending) // add a new path to the queue
            {
                Maneuver m;
                ManeuverQueue mq = system.getComponent(ManeuverQueue.class, e);
                Heading h = system.getComponent(Heading.class, e);
                Jet j = system.getComponent(Jet.class, e);
                Path p = system.getComponent(Path.class, e);

                actionPending = false;
                
                if(mq.maneuvers.size() >= Constants.QUEUE_MAX) // can we queue up another maneuver?
                    return this;
                                                
                switch(intent)
                {
                    case HARDL:
                    {       
                        m = ManeuverFactory.createCourseHardL(getNextHeading(mq,p), false);
                        break;
                    }
                    case HARDR:
                    {
                        m = ManeuverFactory.createCourseHardR(getNextHeading(mq,p), false);
                        break;
                    }
                    case SOFTR:
                    { 
                        m = ManeuverFactory.createCourseSoftR(getNextHeading(mq,p), false); 
                        break;
                    }
                    case SOFTL:
                    {
                        m = ManeuverFactory.createCourseSoftL(getNextHeading(mq,p), false);
                        break;
                    }
                    case ACCEL:
                    {
                        if(!ManeuverQueue.willBeFast(mq,j.fast))
                            m = ManeuverFactory.createCourseAccel(getNextHeading(mq,p));
                        else
                            return this;
                        
                        break;
                    }
                    case DEACCEL:
                    {
                        if(ManeuverQueue.willBeFast(mq,j.fast))
                            m = ManeuverFactory.createCourseDecel(getNextHeading(mq,p));
                        else
                            return this;
                        
                        break;
                    }
                    default:
                        m = ManeuverFactory.createCourseStraight(getNextHeading(mq,p), false);
                        break;
                }

                ManeuverQueue.addManeuver(mq, m);             
            }
        }

        entityChanged = false;
        
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
