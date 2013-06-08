package util.bezier;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import util.ImmutableVector;

/**
 * A parametric cubic Bezier curve that can be rotated.  P1 is always at zero 
 * and rotation is counter clockwise.
 * 
 * @author Michael McCarron
 *
 */
public class BezierCurve
{
    float p1x;
    float p1y;
    float p2x;
    float p2y;
    float c1x;
    float c1y;
    float c2x;
    float c2y;
        
    boolean lengthValid;
    LinearSpline linearApproximation;
    
    public BezierCurve()
    {
        p1x = 0;
        p1y = 0;
        
        p2x = 1;
        p2y = 1;
        
        c1x = 0;
        c1y = 1;
        
        c2x = 0;
        c2y = 1;
        
        lengthValid = false;
    }
        
    public float getLength()
    {
        if(!lengthValid)
            throw new IllegalStateException("Length has not been estimated yet!");
        
        return linearApproximation.getLength();
    }
    
    public void calculateLength(float maxFlattness)
    {
        linearApproximation = generateApproximation(maxFlattness);
        lengthValid = true;
    }
    
    public void setAnchorEnd(float x, float y)
    {
        lengthValid = false;
        
        p2x = x;
        p2y = y;
    }

    public void setAnchorStart(float x, float y)
    {
        lengthValid = false;
        
        p1x = x;
        p1y = y;
    }
    
    public void setControlPointOne(float x, float y)
    {
        lengthValid = false;
        
        c1x = x;
        c1y = y;
    }
    
    public void setControlPointTwo(float x, float y)
    {
        lengthValid = false;
        
        c2x = x;
        c2y = y;
    }
    
    public ImmutableVector evaluateBezierCurve(float p)
    {
        float x = (float)Math.pow(1-p,3)*p1x + 3*(float)Math.pow(1-p, 2)*p*c1x + 3*(1-p)*p*p*c2x + p*p*p*p2x;
        float y = (float)Math.pow(1-p,3)*p1y + 3*(float)Math.pow(1-p, 2)*p*c1y + 3*(1-p)*p*p*c2y + p*p*p*p2y;
        ImmutableVector ret = new ImmutableVector(x, y);
        
        return ret;
    }
    
    public ImmutableVector getPoint(float t)
    {
        if(!lengthValid)
            throw new IllegalStateException("Approximation has not been calculated yet!");
        
        return linearApproximation.getPoint(t);
    }

    public float flattness()
    {
        ImmutableVector v1 = new ImmutableVector(c1x-p1x, c1y-p1y);
        ImmutableVector v2 = new ImmutableVector(c2x-p1x, c2y-p1y);
        ImmutableVector v = new ImmutableVector(p2x-p1x, p2y-p1y).normalise();
        
        float f1 = v1.sub(v.scale(v1.dot(v))).mag();
        float f2 = v2.sub(v.scale(v2.dot(v))).mag();
        
        return f1 > f2 ? f1 : f2;
    }

    public List<BezierCurve> split(float t)
    {
        List<BezierCurve> ret = new ArrayList<BezierCurve>();
        
        BezierCurve c = new BezierCurve();
        
        float Ax = (1-t)*p1x + t*c1x;
        float Bx = (1-t)*c1x + t*c2x;
        float Cx = (1-t)*c2x + t*p2x;
        float Mx = (1-t)*Ax + t*Bx;
        float Nx = (1-t)*Bx + t*Cx;
        float Px = (1-t)*Mx + t*Nx;
        
        float Ay = (1-t)*p1y + t*c1y;
        float By = (1-t)*c1y + t*c2y;
        float Cy = (1-t)*c2y + t*p2y;
        float My = (1-t)*Ay + t*By;
        float Ny = (1-t)*By + t*Cy;
        float Py = (1-t)*My + t*Ny;
        
        c.setAnchorStart(p1x, p1y);
        c.setAnchorEnd(Px, Py);
        c.setControlPointOne(Ax, Ay);
        c.setControlPointTwo(Mx, My);
        
        ret.add(c);
        
        c = new BezierCurve();
        
        c.setAnchorEnd(p2x, p2y);
        c.setAnchorStart(Px, Py);
        c.setControlPointOne(Nx, Ny);
        c.setControlPointTwo(Cx, Cy);
        
        ret.add(c);
        
        return ret;
    }
    
    LinearSpline generateApproximation(float maxFlattness)
    {
        return new LinearSpline(flatten(this, maxFlattness));
    }
    
    private static List<Point2D.Float> flatten(BezierCurve c, float maxFlattness)
    {
        if(c.flattness() <= maxFlattness)
        {
            List<Point2D.Float> ret = new ArrayList<>();
            ret.add(new Point2D.Float(c.p1x,c.p1y));
            ret.add(new Point2D.Float(c.p2x,c.p2y));
            
            return ret;
        }

        List<Point2D.Float> ret = new ArrayList<>();
        List<BezierCurve> s = c.split(0.5f);
        ret.addAll(flatten(s.get(0), maxFlattness));
        List<Point2D.Float> tmp = flatten(s.get(1), maxFlattness);
        tmp.remove(0);
        ret.addAll(tmp);
        
        return ret;
    }

    public ImmutableVector getAnchorStart()
    {
        return new ImmutableVector(p1x, p1y);
    }

//    @Override
//    public int hashCode()
//    {
//        return Math.round(px+py+c1x+c1y+c2x+c2y);
//    }
//    
//    @Override
//    public boolean equals(Object o)
//    {
//        if(!(o instanceof BezierCurve))
//            return false;
//        
//        BezierCurve c = (BezierCurve)o;
//        
//        if(c.px != px)
//            return false;
//        
//        if(c.py != py)
//            return false;
//        
//        if(c.c1x != c1x)
//            return false;
//        
//        if(c.c1y != c1y)
//            return false;
//        
//        if(c.c2x != c2x)
//            return false;
//        
//        if(c.c2y != c2y)
//            return false;
//        
//        return true;        
//    }
}
