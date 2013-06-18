package airf.component;

import java.util.ArrayList;
import java.util.List;

import airf.jetstates.Maneuver;
import airf.jetstates.Maneuver.AccType;

import com.artemis.Component;

public class ManeuverQueue extends Component
{
    public ManeuverQueue(int queueSize)
    {
        maneuvers = new ArrayList<>();
        count = 0;
        maxQueueSize = queueSize;
    }
    
    public List<Maneuver> maneuvers;
    public int count;
    public Maneuver finishedManeuver;
    public int maxQueueSize;
    
    
    public static boolean willBeFast(ManeuverQueue pq, boolean isFast)
    {        
        AccType a = AccType.NONE;
        for(int i = pq.maneuvers.size()-1; i >= 0; i--)
        {
            Maneuver m = pq.maneuvers.get(i);
            a = m.getAcc();
            if(a != AccType.NONE)
                break;
        }
        
        if(a == AccType.NONE)
            return isFast;
        else
            return a == AccType.ACCELERATE;
    }
    
    public static void addManeuver(ManeuverQueue pq, Maneuver c)
    {
        pq.maneuvers.add(c);
    }

    public static float getFinalHeading(ManeuverQueue mq)
    {
        if(mq.maneuvers.isEmpty())
            return -1;
        
        return mq.maneuvers.get(mq.maneuvers.size()-1).getCourse().getHeading(1);
    }

    public static boolean isFull(ManeuverQueue mq)
    {
        return mq.maneuvers.size() == mq.maxQueueSize;
    }
}
