package airf.pathing;


public class Maneuver
{
    Course course;
    AccType acc;
    public enum AccType {NONE, ACCELERATE, DECELERATE};
    
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
