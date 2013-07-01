package airf.input.states;

import airf.input.Command;
import airf.input.InputState;
import airf.input.InputToIntent;

import com.artemis.Entity;

public class NoSelectionState implements InputState
{
    InputToIntent iti;
    
    public NoSelectionState(InputToIntent iti)
    {
        this.iti = iti;
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
            Entity e = iti.selectClosestJet(x, y);
            return new SelectedState(iti, e);
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
