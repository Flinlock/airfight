package airf.pathing;

import util.bezier.BezierCurve;
import airf.Constants;
import airf.jetstates.Maneuver;
import airf.jetstates.Maneuver.AccType;

public class ManeuverFactory
{    
    float vSlow;
    float vFast;
    int timeSlotPeriod;
    float lenFast;
    float lenSlow;
    
    public ManeuverFactory(int timeSlotPeriod)
    {
        this.timeSlotPeriod = timeSlotPeriod;
        vSlow = 1f / (float)timeSlotPeriod;
        vFast = vSlow;
        
        lenSlow = createCourseStraight(0, false).getCourse().getLength();
        lenFast = createCourseStraight(0, true).getCourse().getLength();
    }
    
    private float convertHeading(float h) // TODO: Remove
    {
        float hAdjusted = h;
//        hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
//        
//        if(hAdjusted < 0)
//            hAdjusted += 360;
        
        return hAdjusted;
    }
    
    // heading is in degrees
    public Maneuver createCourseHardL(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        float v;

        if(fast)
            System.out.println("Creating fast course");
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(66.66f, 66.66f);
            c.setControlPointOne(36.66f, 0);
            c.setControlPointTwo(66.66f, 36.66f);
            c.calculateLength(0.01f);
            v = vSlow;
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(104.21f, 104.21f);
            c.setControlPointOne(57.58f, 0);
            c.setControlPointTwo(104.21f, 57.58f);
            c.calculateLength(0.01f);
            v = vFast;              
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(v, path, profile),AccType.NONE);              
    }

    public Maneuver createCourseHardR(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        float v;
        
        if(fast)
            System.out.println("Creating fast course");
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(66.66f, -66.66f);
            c.setControlPointOne(36.66f, 0);
            c.setControlPointTwo(66.66f, -36.66f);
            c.calculateLength(0.01f);
            v = vSlow;
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(104.21f, -104.21f);
            c.setControlPointOne(57.58f, 0);
            c.setControlPointTwo(104.21f, -57.58f);
            c.calculateLength(0.01f);
            v = vFast;            
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Maneuver(new Course(v, path, profile),AccType.NONE);  
    }

    public Maneuver createCourseSoftR(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        float v;

        if(fast)
            System.out.println("Creating fast course");

        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(46.193f, -89.815f);
            c.setControlPointOne(0, 37.12f);
            c.setControlPointTwo(18.18f, -69.7f);
            c.calculateLength(0.01f);
            v = vSlow;
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(72.123f, -140.5f);
            c.setControlPointOne(0, 57.83f);
            c.setControlPointTwo(28.54f, -108.84f);
            c.calculateLength(0.01f); 
            v = vFast;           
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(v, path, profile),AccType.NONE);
    }

    public Maneuver createCourseSoftL(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        float v;
        
        if(fast)
            System.out.println("Creating fast course");
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(46.193f, 89.815f);
            c.setControlPointOne(0, 37.12f);
            c.setControlPointTwo(18.18f, 69.7f);
            c.calculateLength(0.01f);
            v = vSlow;
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(72.123f, 140.5f);
            c.setControlPointOne(0, 57.83f);
            c.setControlPointTwo(28.54f, 108.84f);
            c.calculateLength(0.01f);
            v = vFast;            
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(v, path, profile),AccType.NONE);
    }

    public Maneuver createCourseAccel(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();

        c.setAnchorStart(0, 0);
        c.setAnchorEnd(133.867f, 0);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(133.867f, 0);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180f * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        float vStart = lenSlow / (float)Constants.TIME_SLOT_PERIOD;
        float vEnd = lenFast / (float)Constants.TIME_SLOT_PERIOD;
        float acc = (vEnd - vStart) / (float)Constants.TIME_SLOT_PERIOD;
        float accNorm = acc / path.getLength();
        
        float vNorm =  lenSlow / path.getLength() * vSlow;
                
        profile.addDivider(0, accNorm);

        return new Maneuver(new Course(vNorm, path, profile),AccType.ACCELERATE);
    }

    public Maneuver createCourseDecel(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(133.867f, 0);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(133.867f, 0);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180f * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        float vEnd = lenSlow / (float)Constants.TIME_SLOT_PERIOD;
        float vStart = lenFast / (float)Constants.TIME_SLOT_PERIOD;
        float acc = (vEnd - vStart) / (float)Constants.TIME_SLOT_PERIOD;
        float accNorm = acc / path.getLength();
        
        float vNorm =  lenFast / path.getLength() * vFast;
                
        profile.addDivider(0, accNorm);

        return new Maneuver(new Course(vNorm, path, profile),AccType.DECELERATE);
    }

    public Maneuver createCourseStraight(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        float v;

        if(fast)
            System.out.println("Creating fast course");
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(104.5f, 0);
            c.setControlPointOne(0, 0);
            c.setControlPointTwo(104.5f, 0);
            c.calculateLength(0.01f);
            v = vSlow;
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(163.234f, 0);
            c.setControlPointOne(0, 0);
            c.setControlPointTwo(163.234f, 0);
            c.calculateLength(0.01f); 
            v = vFast;                       
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(v, path, profile, "Straight Flight"),AccType.NONE);
    }   
}
