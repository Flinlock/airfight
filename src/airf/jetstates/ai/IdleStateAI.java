package airf.jetstates.ai;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import airf.Constants;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.Path;
import airf.component.Position;
import airf.component.Velocity;
import airf.jetstates.IntentType;
import airf.jetstates.JetState;
import airf.jetstates.ManeuveringState;
import airf.pathing.Course;
import airf.pathing.ManeuverFactory;
import airf.system.JetSystem;

import com.artemis.Entity;

public class IdleStateAI implements JetState
{
    JetSystem system;
    boolean changed;
    Random rand;
    boolean fast;
    List<IntentType> maneuvers;
    
    public IdleStateAI(JetSystem sys)
    {
        System.out.println("Entering IdleStateAI.");
        system = sys;
        changed = true;
        fast = false;
        rand = new Random();
        maneuvers = new ArrayList<>();
        maneuvers.add(IntentType.HARDL);
        maneuvers.add(IntentType.HARDR);
        maneuvers.add(IntentType.SOFTL);
        maneuvers.add(IntentType.SOFTR);
        maneuvers.add(IntentType.ACCEL);
        maneuvers.add(IntentType.DEACCEL);
    }
    
    @Override
    public JetState update(Entity e)
    {
        Path p = new Path();
        Position pos = system.getComponent(Position.class, e);
        Velocity v = system.getComponent(Velocity.class, e);
        Heading h = system.getComponent(Heading.class, e);
        Jet j = system.getComponent(Jet.class, e);
        
        changed = false;
                
        // Maneuver to keep the jet on the screen
        
        Point2D.Float pntFuture = new Point2D.Float();
        
        pntFuture.x = pos.x + v.x*100/1000f;
        pntFuture.y = pos.y + v.y*100/1000f;
                
        if(!isSafeLocation(pntFuture, h.h))
        {
            p.course = ManeuverFactory.createCourseHardL(h.h, false);

            p.p = 0;
            p.v = (float)Math.sqrt(v.x*v.x + v.y*v.y);
            p.x = pos.x;
            p.y = pos.y;
            
            e.addComponent(p);
            
            return new ReverseDirectionAI(system);
        }
        
        if(rand.nextInt(100) < 4)
        {
            IntentType intent = maneuvers.get(rand.nextInt(maneuvers.size()));

            switch(intent)
            {
                case HARDL:
                {
                    p.course = ManeuverFactory.createCourseHardL(h.h, false);
                    break;
                }
                case HARDR:
                {
                    p.course = ManeuverFactory.createCourseHardR(h.h, false);
                    break;
                }
                case SOFTR:
                {
                    p.course = ManeuverFactory.createCourseSoftR(h.h, false);
                    break;
                }
                case SOFTL:
                {
                    p.course = ManeuverFactory.createCourseSoftL(h.h, false);
                    break;
                }
                case ACCEL:
                {
                    if(!j.fast)
                    {
                        j.fast = true;
                        p.course = ManeuverFactory.createCourseAccel(h.h);
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
                        p.course = ManeuverFactory.createCourseDeaccel(h.h);
                    }
                    else
                        return this;

                    break;
                }
                case TEST:
                    p.course = ManeuverFactory.createCourseTest(h.h, false);
                    break;                    
            }

            p.p = 0;
            p.v = (float)Math.sqrt(v.x*v.x + v.y*v.y);
            p.x = pos.x;
            p.y = pos.y;
            
            Point2D.Float endPnt = p.course.getPoint(1.0f);
            endPnt.x += pos.x;
            endPnt.y += pos.y;
            float endH = p.course.getEndHeading();
            
            if(!isSafeLocation(endPnt, endH))
                return this;

            e.addComponent(p);

            return new ManeuveringStateAI(system);  
        }
        
        // safe condition is if a jet can reverse it's direction of travel (e.g. two successive hard L's or hard R's)
        // without going off the screen
        //      A jet cannot escape going off the edge of the screen iff the safe condition is not true.
        
        // Look into the future, if not maneuvering, look at the jet's position at some dT>0 into the future, if about to maneuver look where the jet will be at
        // the end of the maneuver, if this position is not safe, maneuver before dT or do not choose that maneuver.
                
        return this;
    }
    
    public boolean isSafeLocation(Point2D.Float pos, float heading)
    {
        boolean leftBad = false;
        boolean rightBad = false;
        
        Course cL1 = ManeuverFactory.createCourseHardL(heading, false);
        Point2D.Float pntL1 = cL1.getPoint(1.0f);
        pntL1.x += pos.x;
        pntL1.y += pos.y;
        
        if(pntL1.x < 0 || pntL1.x > Constants.WIDTH)
            leftBad = true;
        
        if(pntL1.y < 0 || pntL1.y > Constants.HEIGHT)
            leftBad = true;

        float headingL = cL1.getEndHeading();
        Course cL2 = ManeuverFactory.createCourseHardL(headingL, false);
        Point2D.Float pntL2 = cL2.getPoint(1.0f);
        pntL2.x += pntL1.x;
        pntL2.y += pntL1.y;
        
        if(pntL2.x < 0 || pntL2.x > Constants.WIDTH)
            leftBad = true;
        
        if(pntL2.y < 0 || pntL2.y > Constants.HEIGHT)
            leftBad = true;
        
        Course cR1 = ManeuverFactory.createCourseHardR(heading, false);
        Point2D.Float pntR1 = cR1.getPoint(1.0f);
        pntR1.x += pos.x;
        pntR1.y += pos.y;
        
        if(pntR1.x < 0 || pntR1.x > Constants.WIDTH)
            rightBad = true;
        
        if(pntR1.y < 0 || pntR1.y > Constants.HEIGHT)
            rightBad = true;
        

        float headingR = cR1.getEndHeading();
        Course cR2 = ManeuverFactory.createCourseHardR(headingR, false);

        Point2D.Float pntR2 = cR2.getPoint(1.0f);
        pntR2.x += pntR1.x;
        pntR2.y += pntR1.y;
        
        if(pntR2.x < 0 || pntR2.x > Constants.WIDTH)
            rightBad = true;
        
        if(pntR2.y < 0 || pntR2.y > Constants.HEIGHT)
            rightBad = true;
        
        return !(rightBad & leftBad);
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
        return changed;
    }

}
