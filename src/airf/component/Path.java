package airf.component;

import airf.PathDefinition;

import com.artemis.Component;

public class Path extends Component
{
    public PathDefinition path;
    public float p;  // parameterized position on path
    public float v;  // velocity along path
}
