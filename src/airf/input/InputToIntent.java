package airf.input;

import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import airf.component.Jet;
import airf.component.Position;
import airf.component.Select;

import com.artemis.ComponentType;
import com.artemis.Entity;

public class InputToIntent implements KeyListener, MouseListener
{    
    Input input;
    boolean shiftDown;
    Jet playerJet;
    private ArrayList<Entity> playerJets;
    private Entity selectedJet;
    private ComponentType pt;
    private ComponentType jt;
    private ComponentType st;
    private int screenHeight;
    
    public InputToIntent(int screenHeight)
    {
        this.screenHeight = screenHeight;
        shiftDown = false;
        playerJets = new ArrayList<>();
        selectedJet = null;
        pt = ComponentType.getTypeFor(Position.class);
        jt = ComponentType.getTypeFor(Jet.class);
        st = ComponentType.getTypeFor(Select.class);
    }
    
    @Override
    public void inputEnded()
    {   
    }

    @Override
    public void inputStarted()
    {
        if(selectedJet == null)
            throw new IllegalStateException("A jet must always be selected");
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
        if(button == Input.MOUSE_LEFT_BUTTON)
        {
            y = screenHeight - y;
            Entity closest = null;
            float distance = Float.POSITIVE_INFINITY;
            for(Entity e : playerJets)
            {
                Position p = (Position)e.getComponent(pt);
                float d = (float)(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
                if(d < distance)
                {
                    distance = d;
                    closest = e;
                }
            }

            Select sOld = (Select)selectedJet.getComponent(st);
            Select sNew = (Select)closest.getComponent(st);
            sOld.state = sOld.state.setSelected(false);
            sNew.state = sNew.state.setSelected(true);
            selectedJet = closest;
        }
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
        
        Jet jet = (Jet)selectedJet.getComponent(jt);
        
        switch(key)
        {
            case Input.KEY_LEFT:
            {
                if(shiftDown)
                    jet.state.intentHardL();
                else
                    jet.state.intentSoftL();
                break;
            }
            case Input.KEY_RIGHT:
            {
                if(shiftDown)
                    jet.state.intentHardR();
                else
                    jet.state.intentSoftR();
                break;
            }
            case Input.KEY_UP:
            {
                jet.state.intentSpeedUp();
                break;
            }
            case Input.KEY_DOWN:
            {
                jet.state.intentSlowDown();
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

    public void addJet(Entity jet)
    {
        playerJets.add(jet);
    }

    public Entity getSelectedJet()
    {
        return selectedJet;
    }
    
    public void setSelectedJet(Entity e)
    {
        selectedJet = e;
    }    
}
