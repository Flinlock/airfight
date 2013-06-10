package airf.pathing;

import util.bezier.BezierCurve;
import airf.jetstates.Maneuver;
import airf.jetstates.Maneuver.AccType;

public class ManeuverFactory
{    
    static private float convertHeading(float h)
    {
        float hAdjusted = h;
        hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
        
        if(hAdjusted < 0)
            hAdjusted += 360;
        
        return hAdjusted;
    }
    
    // heading is in degrees
    public static Maneuver createCourseHardL(float heading, boolean fast)
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

    public static Maneuver createCourseHardR(float heading, boolean fast)
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

    public static Maneuver createCourseSoftR(float heading, boolean fast)
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

    public static Maneuver createCourseSoftL(float heading, boolean fast)
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

    public static Maneuver createCourseAccel(float heading)
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
        profile.addDivider(0.0f, 0.000001f/(160f/270f));

        return new Maneuver(new Course(path, profile),AccType.ACCELERATE);
    }

    public static Maneuver createCourseDecel(float heading)
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
        profile.addDivider(0.0f, -0.000001f);

        return new Maneuver(new Course(path, profile),AccType.DEACCELERATE);
    }

    public static Maneuver createCourseStraight(float heading, boolean fast)
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
