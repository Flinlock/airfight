package airf.states.selectable;

import airf.component.PinTo;
import airf.component.Position;
import airf.component.Sprite;
import airf.states.Selectable;

import com.artemis.ComponentType;
import com.artemis.Entity;

public class Selected implements Selectable
{
    Entity highlight;
    Entity jet;
    ComponentType st;
    ComponentType pt;
    
    public Selected(Entity e, ComponentType sprite, ComponentType position, Entity hl)
    {
        jet = e;        
        st = sprite;
        pt = position;
        highlight = hl;
    }
    
    @Override
    public Selectable setSelected(boolean selected)
    {
        if(!selected)
        {
            highlight.removeComponent(PinTo.class);
            highlight.removeComponent(Sprite.class);
            highlight.removeComponent(Position.class);
            
            highlight.changedInWorld();
                        
            return new Unselected(jet, st, pt, highlight);
        }
        
        return this;
    }
}
