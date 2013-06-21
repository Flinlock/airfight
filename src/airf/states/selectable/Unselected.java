package airf.states.selectable;

import airf.component.PinTo;
import airf.component.Position;
import airf.component.Sprite;
import airf.states.Selectable;

import com.artemis.ComponentType;
import com.artemis.Entity;

public class Unselected implements Selectable
{
    Entity highlight;
    Entity jet;
    ComponentType st;
    ComponentType pt;
    
    public Unselected(Entity e, ComponentType sprite, ComponentType position, Entity hl)
    {
        jet = e;        
        st = sprite;
        pt = position;
        highlight = hl;
    }
    
    @Override
    public Selectable setSelected(boolean selected)
    {
        if(selected)
        {
            Sprite s = new Sprite();
            s.name = "selection_highlight";
            s.scaleX = 1;
            s.scaleY = 1;
            s.rot = 0;
            
            Position p = new Position();
            Position pTarg = (Position)jet.getComponent(pt);
            p.x = pTarg.x;
            p.y = pTarg.y;
            p.lx = pTarg.lx;
            p.y = pTarg.ly;
            
            PinTo pin = new PinTo();
            pin.target = jet;
                        
            highlight.addComponent(s);
            highlight.addComponent(p);
            highlight.addComponent(pin);
            
            highlight.changedInWorld();
                        
            return new Selected(jet, st, pt, highlight);
        }
        
        return this;
    }

}
