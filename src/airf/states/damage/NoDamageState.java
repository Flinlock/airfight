package airf.states.damage;

public class NoDamageState implements DamageState
{

    @Override
    public DamageState takeDamage()
    {
        System.out.println("Took Damage!!!");
        return this;
    }

    @Override
    public DamageState update()
    {
        return this;
    }

}
