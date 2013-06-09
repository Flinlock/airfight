package airf.pathing;

import util.bezier.BezierCurve;

public class CourseFactory
{    
    static private float convertHeading(float h)
    {
        float hAdjusted = h;
        hAdjusted = 360 - hAdjusted - 90;  // convert from clockwise to counter clockwise rotation and adjust for coordinate frame differences
        
        if(hAdjusted < 0)
            hAdjusted += 360;
        
        return hAdjusted;
    }
    
    public static Course createCourseHardL(float heading, boolean fast)
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
        
        return new Course(path, profile);        
    }

    public static Course createCourseHardR(float heading, boolean fast)
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
        
        return new Course(path, profile);
    }

    public static Course createCourseSoftR(float heading, boolean fast)
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
        
        return new Course(path, profile);
    }

    public static Course createCourseSoftL(float heading, boolean fast)
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
        
        return new Course(path, profile);
    }

    public static Course createCourseAccel(float heading)
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
        
        return new Course(path, profile);
    }

    public static Course createCourseDecel(float heading)
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
        
        return new Course(path, profile);
    }
    
    public static Course createCourseStraight(float heading, boolean fast)
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
        profile.addDivider(0.0f, -0.000001f);
        
        return new Course(path, profile);
    }
}
