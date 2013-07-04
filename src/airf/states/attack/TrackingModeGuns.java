package airf.states.attack;

import airf.component.Radar;
import airf.system.JetSystem;

import com.artemis.Entity;

public class TrackingModeGuns implements AttackState
{
    Entity jet;
    Entity target;
    int trackingCount;
    JetSystem sys;
    
    public TrackingModeGuns(JetSystem sys, Entity attacker, Entity target, int delay)
    {
        jet = attacker;
        this.target = target;
        trackingCount = delay;
        this.sys = sys;
    }

    @Override
    public AttackState intentActivateWeapon(WeaponType weapon)
    {
        return this;
    }

    @Override
    public AttackState intentSetTarget(Entity target)
    {
        if(target != this.target)
            return new AttackModeGuns(sys, jet, target);
        
        return this;
    }

    @Override
    public AttackState update()
    {
        Radar r = sys.getComponent(Radar.class, jet);
        if(!r.targets.contains(target))
            return new AttackModeGuns(sys, jet, target);
        
        if(trackingCount-- <= 0)
            return new FiringModeGuns(sys, jet, target);
                
        return this;
    }

    @Override
    public AttackState targetLost()
    {
        return new NoTargetGuns(sys, jet);
    }

    @Override
    public Entity getTarget()
    {
        return target;
    }

}
