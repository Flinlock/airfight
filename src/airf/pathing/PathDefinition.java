package airf.pathing;

import java.awt.geom.Point2D;

import util.ImmutableVector;
import util.bezier.BezierCurve;

/**
 * Defines a 2-D parametric path using a rotated Bezier curve.
 * 
 * @author Michael McCarron
 *
 */
public class PathDefinition
{
    BezierCurve c;
    float rot;
    
    public PathDefinition(BezierCurve c)
    {
        this.c = c;
        rot = 0;
    }
    
    /**
     * Sets the rotation of the path.
     * @param r Rotation in radians.
     */
    public void setRotation(float r)
    {
        if(r > 2*Math.PI || r < 0)
            throw new IllegalArgumentException();
        
        rot = r;
    }
    
    /**
     * If an object traveling along the curve is moving at rate v and
     * delta time has passed, what is the change in the value of the
     * curve's parameter the would result in the same distance traveled
     * along the curve.  E.g. if P(t_0) = f(p_0) and P(t_1) = f(p_1) s.t.
     * t_1-t_0 = delta and the distance along the curve f between P(t_1) and
     * P(t_0) is equal to v * delta, then this function returns p_1 - p_2. 
     * 
     * @param v Velocity along the path.
     * @param delta Amount of time that has passed.
     * @return The delta of the curve's parameter that equals the distance traveled along the curve in parameter delta time.
     */
    public float getProgressDelta(float v, float delta)
    {
        float l = c.getLength();
        return delta / (v * l); 
    }
    
    public boolean isPathComplete(float p)
    {
        return p >= 1f;
    }
        
    public Point2D.Float getPoint(float p)
    {
        ImmutableVector v = c.getPoint(p);//.rot(rot);
        ImmutableVector origin = c.getAnchorStart();
        v = v.sub(origin);
        v = v.rot(2*(float)Math.PI - rot);
        v = v.add(origin);
        return new Point2D.Float(v.x,v.y);  // ensure rotation around origin
    }

    public float getLength()
    {
        return c.getLength();
    }
}
