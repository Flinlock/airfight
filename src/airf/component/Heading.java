package airf.component;

import com.artemis.Component;

public class Heading extends Component
{
    // heading in radians, 0 corresponds to (1,0), 
    // (assume normal cartesian coordinates: (1,0) is right, (0,1) is up), 
    // increasing heading corresponds to counter-clockwise rotation
    public float h;  
}
