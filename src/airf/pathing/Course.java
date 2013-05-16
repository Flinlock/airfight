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
    
    public Course(PathDefinition path, AccelerationProfile profile)
    {
        this.path = path;
        this.profile = profile;
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

}
