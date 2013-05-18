package airf.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import airf.component.Jet;

public class InputToIntent implements KeyListener, MouseListener
{
    Input input;
    boolean shiftDown;

    Jet playerJet;
    
    public InputToIntent()
    {
        shiftDown = false;
    }
    
    public void setPlayerJet(Jet j)
    {
        playerJet = j;
    }

    @Override
    public void inputEnded()
    {   
    }

    @Override
    public void inputStarted()
    {
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void setInput(Input input)
    {
        this.input = input;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int count)
    {   
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int x, int y)
    {   
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {   
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {   
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {   
    }

    @Override
    public void mouseWheelMoved(int delta)
    {   
    }

    @Override
    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_RSHIFT || key == Input.KEY_LSHIFT)
        {
            shiftDown = true;
            return;
        }
        
        switch(key)
        {
            case Input.KEY_LEFT:
            {
                if(shiftDown)
                    playerJet.state.intentHardL();
                else
                    playerJet.state.intentSoftL();
                break;
            }
            case Input.KEY_RIGHT:
            {
                if(shiftDown)
                    playerJet.state.intentHardR();
                else
                    playerJet.state.intentSoftR();
                break;
            }
            case Input.KEY_UP:
            {
                playerJet.state.intentSpeedUp();
                break;
            }
            case Input.KEY_DOWN:
            {
                playerJet.state.intentSlowDown();
                break;
            }
        }
    }

    @Override
    public void keyReleased(int key, char c)
    {
        if(key == Input.KEY_RSHIFT || key == Input.KEY_LSHIFT)
        {
            shiftDown = false;
            return;
        }
    }
    
}
