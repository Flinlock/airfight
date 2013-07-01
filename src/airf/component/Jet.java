package airf.component;

import airf.states.JetState;
import airf.states.attack.AttackState;
import airf.states.damage.DamageState;

import com.artemis.Component;

public class Jet extends Component
{
    public JetState state;
    public DamageState stateDamage;
    public AttackState stateAttack;    
    public boolean fast;
}
