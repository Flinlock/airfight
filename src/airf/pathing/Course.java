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
     * Calculate the new position and velocity after traveling
     * along the course for dT seconds assuming an initial
     * position on the course of p and initial velocity v.
     * 
     * @param p Initial position on the course in percent.
     * @param v Initial velocity in coordinate space units per second.
     * @param dT Amount of time traveling in milliseconds.
     * 
     * @return A course update describing the new position and velocity after traveling.
     */
    public CourseUpdate calculateUpdate(float p, float v, float dT)
    {
        CourseUpdate ret = new CourseUpdate();
        
        // convert v to percent / second
        float L = path.getLength();
        v /= L;
        
        // follow algorithm from paper
        float pStart = p;
        float vStart = v;
        
        while(true)
        {
            float ai = profile.getAcceleration(p)/L;
            float pi = profile.getEndP(p);
            
            float pTARG = p + v*dT + ai/2f*dT*dT;
            
            if(pTARG > pi)
            {
                pTARG = pi;
                float t;
                if(ai != 0)
                    t = (-1*v + (float)Math.sqrt((v*v - 2*ai*(p - pTARG))))/ai;
                else
                    t = (pTARG - p) / v;
                p = pTARG;
                v += ai*t;
                dT -= t;
                if(pi == 1.0f)
                {
                    ret.pNew = p;
                    ret.vNew = v;
                    ret.dT = dT;
                    break;
                }
            }
            else
            {
                ret.pNew = p + v*dT + ai/2f*dT*dT;
                ret.vNew = v + ai*dT;
                ret.dT = 0;
                break;
            }
        }

        ret.pDelta = ret.pNew - pStart;
        ret.vDelta = ret.vNew - vStart;
        ret.vNew *= L; // convert from percent per second to coordinate units per second
        ret.vDelta *= L;
        return ret;
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
     * Get the end heading in radians.
     * 
     * @return Final heading in radians.
     */
    public float getEndHeading()
    {
        Point2D.Float p2 = path.getPoint(1.0f);
        Point2D.Float p1 = path.getPoint(0.999f);
        
        float ret = 0;
        
        if(p2.x == p1.x)
        {
            if(p2.y > p1.y)
                ret = 180f;
            else if(p2.y < p1.y)
                ret = 0f;
        }
        else
            ret = (float)(Math.atan2(p2.y - p1.y, p2.x - p1.x)) + (float)Math.PI;  // add PI to change range from -pi,+pi to 0,2pi
        
        return ret;
    }
    
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
