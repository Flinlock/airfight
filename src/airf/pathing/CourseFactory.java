package airf.pathing;

import util.bezier.BezierCurve;

public class CourseFactory
{
    public static Course createCourseRightFortyFive()
    {
        BezierCurve c = new BezierCurve();
        
        c.setAnchorStart(0, 0);
        c.setAnchorEnd(-150, 150);
        c.setControlPointOne(75, 75);
        c.setControlPointTwo(75, 75);
        c.calculateLength(0.01f);

        PathDefinition path = new PathDefinition(c);
        path.setRotation(0);
        
        AccelerationProfile profile = new AccelerationProfile();
        
        return new Course(path, profile);        
    }
}
