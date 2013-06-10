package airf.jetstates;

import util.bezier.BezierCurve;

public class TestLength
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        BezierCurve c1 = new BezierCurve();
        c1.setAnchorEnd(-45, 89);
        c1.setAnchorStart(0, 0);
        c1.setControlPointTwo(-18, 69);
        c1.setControlPointOne(0, 36.5f);
        
        BezierCurve c2 = new BezierCurve();
        c2.setAnchorEnd(-66, 66);
        c2.setAnchorStart(0, 0);
        c2.setControlPointTwo(-30, 66);
        c2.setControlPointOne(0, 36.5f);
        
        c1.calculateLength(0.001f);
        c2.calculateLength(0.001f);
        
        System.out.println(Float.toString(c1.getLength()));
        System.out.println(Float.toString(c2.getLength()));
    }

}
