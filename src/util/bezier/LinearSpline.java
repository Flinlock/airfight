package util.bezier;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import util.ImmutableVector;

public class LinearSpline
{
    List<Point2D.Float> points;
    List<Float> lengths;
    List<Float> percents;
    float length;
    
    public LinearSpline()
    {
        points = new ArrayList<>();
        lengths = new ArrayList<>();
        percents = new ArrayList<>();
        length = 0;
    }
    
    public LinearSpline(List<Point2D.Float> pnts)
    {
        points = new ArrayList<>(pnts);
        lengths = new ArrayList<>();
        percents = new ArrayList<>();
        calculateLength();
    }
    
    public void addVertex(Point2D.Float v)
    {
        points.add(v);
        calculateLength();
    }
    
    public int getNumberOfSplines()
    {
        return points.size();
    }
    
    public float getLength()
    {
        return length;
    }
    
    private void calculateLength()
    {
        lengths.clear();
        percents.clear();
        length = 0;
        
        for(int i = 1; i < points.size(); i++)
        {
            Point2D.Float p1 = points.get(i-1);
            Point2D.Float p2 = points.get(i);
            
            float l = (float)Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
            lengths.add(l);
            length += l;
        }
        
        for(float l : lengths)
        {
            percents.add(l / length);
        }
    }

    /**
     * Return the point on the linear spline that is
     * exactly t percent along the spline. If t is
     * negative, the point for t = 0 is returned.  If
     * t > 1.0, the point for t = 1.0 is returned.
     * 
     * @param t Percentage of the length of the spline at which the desired point sits.
     * @return The coordinates of the point on the spline exactly t percent from the start of the spline.
     */
    public ImmutableVector getPoint(float t)
    {
        if(t <= 0)
        {
            Point2D.Float pnt = points.get(0);
            return new ImmutableVector(pnt.x, pnt.y);
        }
        
        if(t >= 1.0)
        {
            Point2D.Float pnt = points.get(points.size()-1);
            return new ImmutableVector(pnt.x, pnt.y);
        }
        
        float p = 0;
        boolean found = false;
        
        int i;
        for(i = 0; i < percents.size(); i++)
        {
            float dP = percents.get(i);
            if(p + dP >= t)
            {
                found = true;
                break;
            }
            p += percents.get(i);
        }
        
        if(!found)
            throw new IllegalStateException();
        
        Point2D.Float pip1, pi;
        
        pip1 = points.get(i+1);
        pi = points.get(i);

        float qx, qy;
        float partialT = (t-p) / percents.get(i);
        
        qx = (pip1.x - pi.x)*partialT + pi.x;
        if(pip1.x == pi.x)
            qy = pi.y + partialT*(pip1.y - pi.y);
        else
            qy = pi.y + ((pip1.y - pi.y) * (qx - pi.x)) / (pip1.x - pi.x);
        
        return new ImmutableVector(qx,qy);
    }

    /**
     * Return the slope of the spline section that
     * contains the point which is
     * exactly t percent along the spline. If t is
     * negative, the slope of the first spline section is
     * returned.  If t > 1.0, the slope of the final
     * spline section is returned.
     * 
     * @param t Percentage of the length of the spline at which the slope is desired.
     * @return The slope of the spline section which contains the point exactly t percent along the spline.
     */
    public Slope getSlope(float t)
    {
        if(t <= 0)
        {
            Point2D.Float p1 = points.get(0);
            Point2D.Float p2 = points.get(1);
            return calcSlope(p1, p2);
        }
        
        if(t >= 1.0)
        {
            Point2D.Float p1 = points.get(points.size()-2);
            Point2D.Float p2 = points.get(points.size()-1);
            return calcSlope(p1, p2);
        }
        
        float p = 0;
        boolean found = false;
        
        int i;
        for(i = 0; i < percents.size(); i++)
        {
            float dP = percents.get(i);
            if(p + dP > t)
            {
                found = true;
                break;
            }
            p += percents.get(i);
        }

        if(!found)
            throw new IllegalStateException();

        Point2D.Float p1 = points.get(i);
        Point2D.Float p2 = points.get(i+1);
        
        return calcSlope(p1, p2);
    }

    private Slope calcSlope(java.awt.geom.Point2D.Float p1, java.awt.geom.Point2D.Float p2)
    {
        float rise = p2.y - p1.y;
        float run = p2.x - p1.x;
                
        return new Slope(rise, run);
    }
}
