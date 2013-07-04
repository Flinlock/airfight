package airf.input.states;

import com.artemis.Entity;

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
        
        // Add highlight to target
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
                return new SelectedState(iti, e);
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
            // TODO: Remove highlight from target
            
            return new SelectTargetState(iti, selectedJet);
        }
        
        if(cmd == Command.CANCEL)
        {
            iti.setEntityWithSelectionHighlight(null);
            // TODO: Remove highlight from target
            
            return new NoSelectionState(iti);
        }
        
        if(cmd == Command.CLEAR_TARGET)
        {
            // TODO: Remove highlight from target
            // TODO: Clear target intent to selected jet
            
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
