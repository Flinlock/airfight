package airf.states.attack;

import airf.component.Expires;
import airf.component.Jet;
import airf.component.Position;
import airf.component.Sprite;
import airf.component.Velocity;
import airf.system.JetSystem;

import com.artemis.Entity;

public class FiringModeGuns implements AttackState
{
    Entity jet;
    Entity target;
    int firingCount;
    JetSystem sys;
    
    Entity artEntity;

    public FiringModeGuns(JetSystem sys, Entity attacker, Entity target)
    {
        jet = attacker;
        this.target = target;
        firingCount = 0;
        this.sys = sys;
        
        // create damage and apply it
        Jet j = sys.getComponent(Jet.class, target);
        j.stateDamage.takeDamage();    
        
        // spawn art assets
        artEntity = sys.getWorld().createEntity();
        
        Sprite sprite = new Sprite();
        sprite.name = "gun_fire";
        sprite.scaleX = 0.2f;
        sprite.scaleY = 0.2f;
        artEntity.addComponent(sprite);
        
        Position pAttacker = sys.getComponent(Position.class, jet);
        Position p = new Position();
        p.x = pAttacker.x;
        p.y = pAttacker.y;
        artEntity.addComponent(p);
        
        Position pTarget = sys.getComponent(Position.class, target);
        Velocity v = new Velocity();
        v.x = pTarget.x - pAttacker.x;
        v.y = pTarget.y - pAttacker.y;
        float mag = (float)Math.sqrt(v.x*v.x + v.y*v.y);
        v.x /= mag;
        v.y /= mag;
        
        v.x *= 0.4f;
        v.y *= 0.4f;
        artEntity.addComponent(v);
        
        Expires ex = new Expires();
        ex.lifeTime = 600;
        artEntity.addComponent(ex);
        
        artEntity.addToWorld();
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
            return exit(new AttackModeGuns(sys, jet, target));
        
        return this;
    }

    @Override
    public AttackState targetLost()
    {
        return exit(new NoTargetGuns(sys, jet));
    }

    @Override
    public AttackState update()
    {        
        if(firingCount++ > 40)
            return exit(new TrackingModeGuns(sys, jet, target, 120));
        
        return this;
    }
    
    private AttackState exit(AttackState ret)
    {
        artEntity.deleteFromWorld();
        
        return ret;
    }

}
