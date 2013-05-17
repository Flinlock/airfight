package airf.pathing;

import util.bezier.BezierCurve;

public class CourseFactory
{
    public static Course createCourseHardL(float heading)
    {
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
}
