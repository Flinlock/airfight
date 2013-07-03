package airf.system;

import java.util.ArrayList;

import airf.component.FiringEnvelope;
import airf.component.Heading;
import airf.component.Jet;
import airf.component.Position;
import airf.component.Radar;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class JetRadarSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Heading> hm;
    @Mapper ComponentMapper<Radar> rm;
    @Mapper ComponentMapper<FiringEnvelope> fem;
    
    ArrayList<Entity> targets;
    ArrayList<Entity> attackers;

    @SuppressWarnings("unchecked")
    public JetRadarSystem()
    {
        super(Aspect.getAspectForAll(Jet.class));
        
        targets = new ArrayList<>();
        attackers = new ArrayList<>();
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities)
    {   
        for(Entity eA : attackers)
        {
            FiringEnvelope fe = fem.get(eA);
            Position pA = pm.get(eA);
            Heading hA = hm.get(eA);
            Radar r = rm.get(eA);
            
            r.targets.clear();
            
            for(Entity eT : targets)
            {
                if(eA == eT)
                    continue;
                
                Position pT = pm.get(eT);

                // calculate angle of attacker to target
                float xB = pT.x - pA.x;
                float yB = pT.y - pA.y;
                
                float angleToTarget = (float)Math.atan2(yB, xB);
                // keep angle between 0 and 2*PI
                angleToTarget = angleToTarget < 0 ? angleToTarget + (float)(2*Math.PI) : angleToTarget;
                
                // Adjust the target's angle to be relative to the attacker's heading
                float angleToTargetT = angleToTarget - hA.h;
                // keep angle between 0 and 2*PI
                angleToTargetT = angleToTargetT < 0 ? angleToTargetT + (float)(2*Math.PI) : angleToTargetT;
                
                // See doc/envelope_calcs.svg
                if(!(angleToTargetT > fe.angleLeft && angleToTargetT < fe.angleRight))
                {
                    // find if the target is within a range demarcation
                    float rangeToTargetSquared = (float)(Math.pow(xB, 2) + Math.pow(yB,  2));
                    if(rangeToTargetSquared < fe.rangeLongSquared)
                    {
                        // target is in range, find if short or long, then add to Radar component
                        if(rangeToTargetSquared < fe.rangeShortSquared)
                        {
                            // short range
                            r.shortRange.add(true);
                            r.targets.add(eT);
                        }
                        else
                        {
                            // long range
                            r.shortRange.add(false);
                            r.targets.add(eT);
                        }
                    }                
                }
            }
        }
    }
    
    @Override
    protected void inserted(Entity e)
    {
        FiringEnvelope fe = fem.getSafe(e);
        
        targets.add(e);
        
        if(fe != null)
            attackers.add(e);
    }
    
    @Override
    protected void removed(Entity e)
    {
        targets.remove(e);
        attackers.remove(e);
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
