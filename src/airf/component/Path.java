package airf.component;

import airf.pathing.Course;

import com.artemis.Component;

public class Path extends Component
{
    public Course course;
    public float p;  // parameterized position on path (percent of total length)
    public float v;  // current velocity along path
    
    public float x;  // path start coordinates
    public float y;
}
