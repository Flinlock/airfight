package airf.jetstates;

import airf.pathing.Course;

public class Maneuver
{
    Course course;
    AccType acc;
    public enum AccType {NONE, ACCELERATE, DEACCELERATE};
    
    public Maneuver(Course c, AccType a)
    {
        course = c;
        acc = a;
    }
    
    public Course getCourse()
    {
        return course;
    }

    public AccType getAcc()
    {
        return acc;
    }
}
