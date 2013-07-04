package airf.input.states;

import com.artemis.Entity;

import airf.component.Jet;
import airf.input.Command;
import airf.input.InputState;
import airf.input.InputToIntent;

public class SelectedWithTargetState implements InputState
{
    InputToIntent iti;
    Entity selectedJet;
    Entity target;

    public SelectedWithTargetState(InputToIntent iti, Entity selectedJet, Entity target)
    {
        this.iti = iti;
        this.selectedJet = selectedJet;
        this.target = target;
        
        iti.setEntityWithTargetHighlight(target);
        iti.setEntityWithSelectionHighlight(selectedJet);
    }

    @Override
    public InputState mouseClicked(Command cmd, int x, int y, int count)
    {
        return this;
    }

    @Override
    public InputState mouseDragged(int oldx, int oldy, int x, int y)
    {
        return this;
    }

    @Override
    public InputState mouseMoved(int oldx, int oldy, int x, int y)
    {
        return this;
    }

    @Override
    public InputState mousePressed(Command cmd, int x, int y)
    {
        if(cmd == Command.SELECT)
        {
            Entity e = iti.findClosestJet(x, y);
            
            if(e != selectedJet)
            {                
                iti.setEntityWithSelectionHighlight(null);    
                iti.setEntityWithTargetHighlight(null);
                
                Jet jet = iti.getComponent(Jet.class, e);
                Entity targ = jet.stateAttack.getTarget();
                
                if(targ == null)
                    return new SelectedState(iti, e);
                else
                    return new SelectedWithTargetState(iti, e, targ);
            }
        }
        
        return this;
    }

    @Override
    public InputState mouseReleased(Command cmd, int x, int y)
    {
        return this;
    }

    @Override
    public InputState mouseWheelMoved(int delta)
    {
        return this;
    }

    @Override
    public InputState keyPressed(Command cmd, char c)
    {
        if(cmd == Command.SELECT_TARGET)
        {
            iti.setEntityWithTargetHighlight(null);            
            return new SelectTargetState(iti, selectedJet);
        }
        
        if(cmd == Command.CANCEL)
        {
            iti.setEntityWithSelectionHighlight(null);
            iti.setEntityWithTargetHighlight(null);
            
            return new NoSelectionState(iti);
        }
        
        if(cmd == Command.CLEAR_TARGET)
        {
            iti.setEntityWithTargetHighlight(null);

            Jet jet = iti.getComponent(Jet.class, selectedJet);
            jet.stateAttack = jet.stateAttack.targetLost();
            
            return new SelectedState(iti, selectedJet);
        }
        return this;
    }

    @Override
    public InputState keyReleased(Command cmd, char c)
    {
        return this;
    }

}
