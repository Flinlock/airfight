package airf.states.attack;

import airf.system.JetSystem;

import com.artemis.Entity;

public class NoTargetGuns implements AttackState
{
    Entity jet;
    JetSystem sys;

    public NoTargetGuns(JetSystem sys, Entity eAttacker)
    {
        jet = eAttacker;
        this.sys = sys;
    }

    @Override
    public AttackState intentActivateWeapon(WeaponType weapon)
    {
        if(weapon == WeaponType.MISSILES)
            throw new IllegalArgumentException("Missiles not yet implemented");
        
        return this;
    }

    @Override
    public AttackState intentSetTarget(Entity target)
    {
        return new AttackModeGuns(sys, jet, target);
    }

    @Override
    public AttackState update()
    {
        return this;
    }

    @Override
    public AttackState targetLost()
    {
        return this;
    }


}
