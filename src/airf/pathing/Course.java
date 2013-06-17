package airf.pathing;

import java.awt.geom.Point2D;


/**
 * A two-dimensional parametric course.  The course consists
 * of a two-dimensional parametric path and an acceleration
 * profile. The path describes the shape of the course
 * and the profile describes the acceleration applied to an 
 * object as it moves along the path.
 * 
 * @author Michael McCarron
 *
 */
public class Course
{
    PathDefinition path;
    AccelerationProfile profile;
    float velocity;
    String name;
    
    public Course(float initialVelocity, PathDefinition path, AccelerationProfile profile)
    {
        this.velocity = initialVelocity;
        this.path = path;
        this.profile = profile;
    }
    
    public Course(float initialVelocity, PathDefinition path, AccelerationProfile profile, String name)
    {
        this.velocity = initialVelocity;
        this.path = path;
        this.profile = profile;
        this.name = name;
    }
    
    /**
     * 
     * @param elapsedTime Time spent on this maneuver so far.
     * @return Percent traveled along the course.
     */
    public float calculateP(int elapsedTime)
    {
        float p = 0;
        float v = velocity;
        float t = elapsedTime;
        for(Point2D.Float accPair : profile)
        {
            float acc = accPair.y;  // point at which acceleration changes
            float pEnd = accPair.x; // acceleration up to that point
            float pTmp = p + v*t + 0.5f*acc*t*t; 
            if(pTmp > pEnd)
            {
                float dP = pEnd - p;
                
                //dP = v*t + 0.5f*acc*t*t;
                
                float a = 0.5f*acc;
                float b = v;
                float c = -dP;
                                
                // let's calculate the time it takes to move from p to pEnd
                float dT;
                if(a != 0)
                    dT = (float)((-b+Math.sqrt(b*b - 4*a*c)) / (2*a));
                else
                    dT = -c/b; 
                
                // let's move to pEnd and update velocity
                p = pEnd;
                v += acc*dT;
                t -= dT;
            }
            else
            {
                float dP = v*t + 0.5f*acc*t*t;
                p += dP;
                v += acc*t;
                break;
            }
        }
        
        return p;
    }
    
    /**
     * Convert a position on the course to the actual x,y
     * coordinates.
     * 
     * @param p Position on the course in percent.
     * @return X,y coordinates corresponding to the supplied position.
     */
    public Point2D.Float getPoint(float p)
    {
        return path.getPoint(p);
    }
    
    /**
     * Calculates the heading of an object moving along the course
     * in a positive p direction at the given percent along the course.
     * 
     * @param p Position on the course in percent.
     * @return Heading in radians.
     */
    public float getHeading(float p)
    {
        return path.getHeading(p);
    }

//    /**
//     * Get the end heading in radians.
//     * 
//     * @return Final heading in radians.
//     */
//    public float getEndHeading()
//    {
//        Point2D.Float p2 = path.getPoint(1.0f);
//        Point2D.Float p1 = path.getPoint(0.999f);
//        
//        float ret = 0;
//        
//        if(p2.x == p1.x)
//        {
//            if(p2.y > p1.y)
//                ret = 180f;
//            else if(p2.y < p1.y)
//                ret = 0f;
//        }
//        else
//            ret = (float)(Math.atan2(p2.y - p1.y, p2.x - p1.x)) + (float)Math.PI;  // add PI to change range from -pi,+pi to 0,2pi
//        
//        return ret;
//    }
    
    public float getLength()
    {
        return path.getLength();                
    }
    
    @Override
    public String toString()
    {
        if(name != null)
            return name;
        else
            return super.toString();
    }

    public float getInitialVelocity()
    {
        return velocity;
    }
}
