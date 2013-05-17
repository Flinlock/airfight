package airf.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import airf.component.Jet;

public class InputToIntent implements KeyListener, MouseListener
{
    Input input;

    Jet playerJet;
    
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
        switch(key)
        {
            case Input.KEY_1:
            {
                playerJet.state.intentHardL();
                break;
            }
            case Input.KEY_2:
            {
                playerJet.state.intentHardR();
                break;
            }
            case Input.KEY_3:
            {
                break;
            }
            case Input.KEY_4:
            {
                break;
            }
            case Input.KEY_5:
            {
                break;
            }
            case Input.KEY_6:
            {
                break;
            }
        }
    }

    @Override
    public void keyReleased(int key, char c)
    {
    }
    
}
