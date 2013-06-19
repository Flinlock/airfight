package util.bezier;

public class Slope
{
    float rise;
    float run;
    
    public Slope(float rise, float run)
    {
        this.rise = rise;
        this.run = run;
    }
    
    public float getRise()
    {
        return rise;
    }
    
    public float getRun()
    {
        return run;
    }

    public float getRatio()
    {
        return rise / run;
    }

}
