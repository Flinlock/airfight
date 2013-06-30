package airf.component;

import com.artemis.Component;

public class FiringEnvelope extends Component
{
    public float angleRight;  // angle of cone from heading to clock-wise side 
    public float angleLeft; // angle of cone from heading to counter-clock-wise side
    public float rangeShortSquared;
    public float rangeLongSquared;
}
