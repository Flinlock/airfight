package airf.component;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;

public class Radar extends Component
{
    public ArrayList<Entity> targets;
    public ArrayList<Boolean> shortRange;
//    public ArrayList<Entity> lost;
//    public ArrayList<Entity> targetsOld;
    
    public Radar()
    {
        targets = new ArrayList<>();
        shortRange = new ArrayList<>();
//        lost = new ArrayList<>();
//        targetsOld = new ArrayList<>();
    }
}
