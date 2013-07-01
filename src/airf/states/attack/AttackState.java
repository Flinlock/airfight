package airf.states.attack;


import com.artemis.Entity;

public interface AttackState
{
    public AttackState intentActivateWeapon(WeaponType weapon);
    public AttackState intentSetTarget(Entity target);
    public AttackState targetLost();
    public AttackState update();
}
