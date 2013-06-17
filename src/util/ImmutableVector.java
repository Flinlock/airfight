package util;

public class ImmutableVector
{
    public final float x;
    public final float y;
    
    public ImmutableVector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public ImmutableVector add(ImmutableVector v)
    {
        return new ImmutableVector(x + v.x, y + v.y);
    }
    
    public ImmutableVector sub(ImmutableVector v)
    {
        return new ImmutableVector(x - v.x, y - v.y);
    }
    
    public ImmutableVector normalise()
    {
        float mag = (float)Math.sqrt(x*x + y*y);
        
        return new ImmutableVector(x / mag, y / mag);
    }
    
    public ImmutableVector lOrtho()
    {
        return new ImmutableVector(-y, x);
    }
    
    public ImmutableVector rOrtho()
    {
        return new ImmutableVector(y, -x);
    }
    
    public ImmutableVector scale(float scale)
    {
        return new ImmutableVector(x*scale, y*scale);
    }
    
    public ImmutableVector projectOnto(ImmutableVector a1, ImmutableVector a2)
    {
        return new ImmutableVector(dot(a1), dot(a2));
    }
    
    public float dot(ImmutableVector v)
    {
        return v.x * x + v.y * y;
    }
    
    public float mag()
    {
        return (float)Math.sqrt(x*x + y*y);
    }

    /**
     * Calculate a rotated version of this vector.
     * 
     * @param r Amount to rotate counter-clockwise in Radians.
     * @return Rotated vector.
     */
    public ImmutableVector rot(float r)
    {
        return new ImmutableVector((float)(x*Math.cos(r)-y*Math.sin(r)), 
                                   (float)(x*Math.sin(r)+y*Math.cos(r)));
    }
}
