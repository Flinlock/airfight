package airf.input.states;

import airf.component.Jet;
import airf.input.Command;
import airf.input.InputState;
import airf.input.InputToIntent;

import com.artemis.Entity;

public class SelectedState implements InputState
{
    InputToIntent iti;
    Entity selectedJet;
    
    public SelectedState(InputToIntent iti, Entity selectedJet)
    {
        this.iti = iti;
        this.selectedJet = selectedJet;
        
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
        Jet jet = iti.getComponent(Jet.class, selectedJet);
        switch(cmd)
        {
            case SELECT_TARGET:
            {
                return new SelectTargetState(iti, selectedJet);
            }
            case CANCEL:
            {
                iti.setEntityWithSelectionHighlight(null);
                return new NoSelectionState(iti);
            }
            case HARD_LEFT:
            {
                jet.state.intentHardL();
                break;
            }
            case HARD_RIGHT:
            {
                jet.state.intentHardR();
                break;
            }
            case SOFT_LEFT:
            {
                jet.state.intentSoftL();
                break;
            }
            case SOFT_RIGHT:
            {
                jet.state.intentSoftR();
                break;
            }
            case ACCELERATE:
            {
                jet.state.intentSpeedUp();
                break;
            }
            case DECELERATE:
            {
                jet.state.intentSlowDown();
                break;
            }
            case IMMELMAN:
            {
                break;
            }
            case NO_COMMAND:
                break;
            case SELECT:
                break;
            case STRAIGHT:
                break;
            default:
                break;
        }
        return this;
    }

    @Override
    public InputState keyReleased(Command cmd, char c)
    {
        return this;
    }

}
