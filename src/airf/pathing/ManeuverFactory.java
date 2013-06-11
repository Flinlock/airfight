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
    
    public ManeuverFactory(int timeSlotPeriod)
    {
        this.timeSlotPeriod = timeSlotPeriod;
        Maneuver m = createCourseStraight(0, false);
        vSlow = m.getCourse().getLength() / timeSlotPeriod; // 0.0209
        
        m = createCourseStraight(0, true);
        vFast = m.getCourse().getLength() / timeSlotPeriod; // 0.032646798
    }
    
    private float convertHeading(float h)
    {
        float hAdjusted = h;
        hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
        
        if(hAdjusted < 0)
            hAdjusted += 360;
        
        return hAdjusted;
    }
    
    // heading is in degrees
    public Maneuver createCourseHardL(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(66.66f, 66.66f);
            c.setControlPointOne(0, 36.66f);
            c.setControlPointTwo(30, 66.66f);
            c.calculateLength(0.01f);
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(104.21f, 104.21f);
            c.setControlPointOne(0, 57.58f);
            c.setControlPointTwo(46.71f, 104.21f);
            c.calculateLength(0.01f);            
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Maneuver(new Course(path, profile),AccType.NONE);        
    }

    public Maneuver createCourseHardR(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(-66.66f, 66.66f);
            c.setControlPointOne(0, 36.66f);
            c.setControlPointTwo(-30, 66.66f);
            c.calculateLength(0.01f);
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(-104.21f, 104.21f);
            c.setControlPointOne(0, 57.58f);
            c.setControlPointTwo(-46.71f, 104.21f);
            c.calculateLength(0.01f);              
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(path, profile),AccType.NONE);
    }

    public Maneuver createCourseSoftR(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(-46.193f, 89.815f);
            c.setControlPointOne(0, 37.12f);
            c.setControlPointTwo(-18.18f, 69.7f);
            c.calculateLength(0.01f);
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(-72.123f, 140.5f);
            c.setControlPointOne(0, 57.83f);
            c.setControlPointTwo(-28.54f, 108.84f);
            c.calculateLength(0.01f);            
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(path, profile),AccType.NONE);
    }

    public Maneuver createCourseSoftL(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();

        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(46.193f, 89.815f);
            c.setControlPointOne(0, 37.12f);
            c.setControlPointTwo(18.18f, 69.7f);
            c.calculateLength(0.01f);
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(72.123f, 140.5f);
            c.setControlPointOne(0, 57.83f);
            c.setControlPointTwo(28.54f, 108.84f);
            c.calculateLength(0.01f);            
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(path, profile),AccType.NONE);
    }

    public Maneuver createCourseAccel(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 206.566f);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 206.566f);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        float a = (float)((c.getLength() - vSlow*(Constants.TIME_SLOT_PERIOD))*2/Math.pow(Constants.TIME_SLOT_PERIOD, 2));                
        profile.addDivider(0, a );
        // d = d_0 + v_0*t + 1/2a*t*t
        // c.getLength() = vSlow*t + 1/2*a*t*t
        // c.getLength() = vSlow*(Constants.TIME_SLOT_PERIOD) + 1/2*a*(Constants.TIME_SLOT_PERIOD)^2
        

        return new Maneuver(new Course(path, profile),AccType.ACCELERATE);
    }

    public Maneuver createCourseDecel(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 132.638f);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 132.638f);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        profile.addDivider(0, (vSlow - vFast) / (float)Constants.TIME_SLOT_PERIOD );

        return new Maneuver(new Course(path, profile),AccType.DEACCELERATE);
    }

    public Maneuver createCourseStraight(float heading, boolean fast)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        if(!fast)
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(0, 104.5f);
            c.setControlPointOne(0, 0);
            c.setControlPointTwo(0, 104.5f);
            c.calculateLength(0.01f);
        }
        else
        {
            c.setAnchorStart(0, 0);
            c.setAnchorEnd(0, 163.234f);
            c.setControlPointOne(0, 0);
            c.setControlPointTwo(0, 163.234f);
            c.calculateLength(0.01f);                        
        }

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();

        return new Maneuver(new Course(path, profile, "Straight Flight"),AccType.NONE);
    }   
}
