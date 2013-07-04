package airf.input.states;

import airf.component.Jet;
import airf.input.Command;
import airf.input.InputState;
import airf.input.InputToIntent;

import com.artemis.Entity;

public class SelectTargetState implements InputState
{
    InputToIntent iti;
    Entity selectedJet;

    public SelectTargetState(InputToIntent iti, Entity selectedJet)
    {
        this.iti = iti;
        this.selectedJet = selectedJet;
        
        // TODO: Add highlights to possible targets
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
            Entity target = iti.findClosestJet(x, y);
            
            Jet jet = iti.getComponent(Jet.class, selectedJet);
            jet.stateAttack = jet.stateAttack.intentSetTarget(target);
            
            // TODO: remove highlights from possible targets
            
            return new SelectedWithTargetState(iti, selectedJet, target);
        }
        
        if(cmd == Command.CANCEL)
        {
            // TODO: remove highlights from possible targets
            
            return new SelectedState(iti, selectedJet);
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
        return this;
    }

    @Override
    public InputState keyReleased(Command cmd, char c)
    {
        return this;
    }

}
