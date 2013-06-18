package airf.jetstates;

import airf.component.Jet;
import airf.component.ManeuverQueue;
import airf.component.Path;
import airf.jetstates.Maneuver.AccType;
import airf.pathing.ManeuverFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleState implements JetState
{
    IntentType intent;
    boolean actionPending;
    public boolean entityChanged = true;
    JetSystem system;
    ManeuverFactory mf;
        
    public IdleState(JetSystem system, ManeuverFactory mf)
    {
        this.system = system;
        this.mf = mf;
    }

    @Override 
    public boolean changed()
    {
        return entityChanged;
    }
    
    private float getNextHeading(ManeuverQueue mq, Path p)
    {
        float fH;
        if(mq.maneuvers.isEmpty())
            fH = p.course.getCourse().getHeading(1);
        else
            fH = ManeuverQueue.getFinalHeading(mq);
        
//        if(fH > 360)
//            fH -= 360;
//        if(fH < 0)
//            fH += 360;
        
        return fH / (float)Math.PI * 180f;
    }
    
    @Override
    public JetState update(Entity e)
    {
        synchronized(this)
        {
            ManeuverQueue mq = system.getComponent(ManeuverQueue.class, e);
            Jet j = system.getComponent(Jet.class, e);

            if(mq.finishedManeuver != null)
            {
                System.out.println("Finishing Manuever!");
                AccType acc = mq.finishedManeuver.getAcc();
                if(acc == AccType.ACCELERATE)
                {
                    System.out.print("accelerating");
                    j.fast = true;
                }
                else if(acc == AccType.DECELERATE)
                {
                    System.out.print("decelerating");
                    j.fast = false;
                }
            }
            
            Path p = system.getComponent(Path.class, e);
            
            if(p.course == null)
                p.course = mf.createCourseStraight(p.h,j.fast);
            
            if(actionPending) // add a new path to the queue
            {
                Maneuver m;

                actionPending = false;
                
                boolean tmp = j.fast;
                if(p.course.getAcc() == AccType.ACCELERATE)
                    tmp = true;
                else if(p.course.getAcc() == AccType.DECELERATE)
                    tmp = false;
                boolean fast = ManeuverQueue.willBeFast(mq,tmp);
                
                if(ManeuverQueue.isFull(mq)) // can we queue up another maneuver?
                    return this;
                                                
                switch(intent)
                {
                    case HARDL:
                    {       
                        m = mf.createCourseHardL(getNextHeading(mq,p), fast);
                        break;
                    }
                    case HARDR:
                    {
                        m = mf.createCourseHardR(getNextHeading(mq,p), fast);
                        break;
                    }
                    case SOFTR:
                    { 
                        m = mf.createCourseSoftR(getNextHeading(mq,p), fast); 
                        break;
                    }
                    case SOFTL:
                    {
                        m = mf.createCourseSoftL(getNextHeading(mq,p), fast);
                        break;
                    }
                    case ACCEL:
                    {
                        if(!fast)
                            m = mf.createCourseAccel(getNextHeading(mq,p));
                        else
                            return this;
                        
                        break;
                    }
                    case DEACCEL:
                    {
                        if(fast)
                            m = mf.createCourseDecel(getNextHeading(mq,p));
                        else
                            return this;
                        
                        break;
                    }
                    default:
                        m = mf.createCourseStraight(getNextHeading(mq,p), fast);
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
