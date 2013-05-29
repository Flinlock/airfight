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
    
    public static Course createCourseHardL(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(150, 150);
        c.setControlPointOne(0, 75);
        c.setControlPointTwo(75, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile);        
    }

    public static Course createCourseTest(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 300);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 300);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading * (float)Math.PI / 180); // heading
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile);        
    }

    public static Course createCourseHardR(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(-150, 150);
        c.setControlPointOne(0, 75);
        c.setControlPointTwo(-75, 150);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile);
    }

    public static Course createCourseSoftR(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(-70, 222);
        c.setControlPointOne(0, 110);
        c.setControlPointTwo(-16, 196);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile);
    }

    public static Course createCourseSoftL(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(70, 222);
        c.setControlPointOne(0, 110);
        c.setControlPointTwo(16, 196);
        c.calculateLength(0.01f);

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
        c.setAnchorEnd(0, 160);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 160);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        profile.addDivider(0.0f, 0.000001f/(160f/270f));
        
        return new Course(path, profile);
    }

    public static Course createCourseDeaccel(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 270);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 270);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        profile.addDivider(0.0f, -0.000001f);
        
        return new Course(path, profile);
    }

    public static Course createCourseStraight(float heading)
    {
        heading = convertHeading(heading);
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(0, 270);
        c.setControlPointOne(0, 0);
        c.setControlPointTwo(0, 270);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(heading / 180 * (float)Math.PI);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile,"Straight Flight");
    }   
}
