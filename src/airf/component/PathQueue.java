package airf.component;

import java.util.ArrayList;
import java.util.List;

import airf.pathing.Course;

import com.artemis.Component;

public class PathQueue extends Component
{
    public PathQueue()
    {
        course = new ArrayList<>();
        startX = new ArrayList<>();
        startY = new ArrayList<>();
        count = 0;
    }
    
    public List<Course> course;
    public List<Float> startX;
    public List<Float> startY;
    public int count;
}
