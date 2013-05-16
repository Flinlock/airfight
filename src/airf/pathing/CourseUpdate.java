package airf.pathing;

public class CourseUpdate
{
    public float pDelta;
    public float vDelta;
    public float pNew;
    public float vNew;
    public float dT;
    
    public CourseUpdate()
    {
        pDelta = 0;
        vDelta = 0;
        pNew = 0;
        vNew = 0;
        dT = 0;
    }
}
