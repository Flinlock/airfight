package airf.states.attack;

import airf.component.Radar;
import airf.system.JetSystem;

import com.artemis.Entity;

public class AttackModeGuns implements AttackState
{
    Entity jet;
    Entity target;
    JetSystem sys;
    
    public AttackModeGuns(JetSystem sys, Entity attacker, Entity target)
    {
        jet = attacker;
        this.target = target;
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
        else
            return this;
    }

    @Override
    public AttackState update()
    {
        Radar r = sys.getComponent(Radar.class, jet);
        if(r.targets.contains(target))
            return new TrackingModeGuns(sys, jet, target, 5);
                
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
