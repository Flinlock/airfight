package airf.component;

import airf.jetstates.Maneuver;

import com.artemis.Component;

public class Path extends Component
{
    public Maneuver course;    
    public float x;  // path start coordinates
    public float y;
    public int totalTime;
    public float h;
}
