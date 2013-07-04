package airf.input;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import airf.component.Jet;
import airf.component.PinTo;
import airf.component.Position;
import airf.component.Sprite;
import airf.input.states.NoSelectionState;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;

public class InputToIntent implements KeyListener, MouseListener
{    
    Input input;
    boolean shiftDown;
    Jet playerJet;
    private ArrayList<Entity> playerJets;
    private ComponentType pt;
    private ComponentType jt;
    private ComponentType spritet;
    private ComponentType ptt;
    private int screenHeight;
    InputState state;
    GameContainer gc;
    World world;

    Entity selectedJet;
    Entity highlight;
    Entity targetJet;
    Entity highlightTarget;
    
    public InputToIntent(int screenHeight, GameContainer gc, World w)
    {
        this.screenHeight = screenHeight;
        world = w;       
        shiftDown = false;
        playerJets = new ArrayList<>();
        pt = ComponentType.getTypeFor(Position.class);
        jt = ComponentType.getTypeFor(Jet.class);
        spritet = ComponentType.getTypeFor(Sprite.class);
        ptt = ComponentType.getTypeFor(PinTo.class);
        state = new NoSelectionState(this);
        this.gc = gc;
        
        selectedJet = null;
        targetJet = null;
        highlight = world.createEntity();
        highlight.addToWorld();
        highlightTarget = world.createEntity();
        highlightTarget.addToWorld();
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
        state = state.mouseClicked(KeyMap.mouseToCommand(button, shiftDown), x, y, count);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int x, int y)
    {   
        state = state.mouseDragged(oldx, oldy, x, y);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {   
        state = state.mouseMoved(oldx, oldy, x, y);
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        state = state.mousePressed(KeyMap.mouseToCommand(button, shiftDown), x, y);
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {   
        state = state.mouseReleased(KeyMap.mouseToCommand(button, shiftDown), x, y);
    }

    @Override
    public void mouseWheelMoved(int delta)
    {   
        state = state.mouseWheelMoved(delta);
    }

    @Override
    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_RSHIFT || key == Input.KEY_LSHIFT)
        {
            shiftDown = true;
            return;
        }
        
        state = state.keyPressed(KeyMap.keyToCommand(key, shiftDown), c);
    }

    @Override
    public void keyReleased(int key, char c)
    {
        if(key == Input.KEY_RSHIFT || key == Input.KEY_LSHIFT)
        {
            shiftDown = false;
            return;
        }
        
        state = state.keyReleased(KeyMap.keyToCommand(key, shiftDown), c);
    }
    
    /////////////////////////////////////////////////////////////////

    public void addJet(Entity jet)
    {
        playerJets.add(jet);
    }
    
    //// Functions for input statemachine, not for public use ///////
    
    public Entity findClosestJet(int x, int y)
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
        
        return closest;
    }
    
    public void setEntityWithTargetHighlight(Entity t)
    {
        if(t == targetJet)
            return;
        
        if(targetJet != null)
        {
            highlightTarget.removeComponent(ptt);
            highlightTarget.removeComponent(spritet);
            highlightTarget.removeComponent(pt);
            
            highlightTarget.changedInWorld();   
            
            targetJet = null;
        }
        
        if(t != null)
        {
            Sprite sprite = new Sprite();
            sprite.name = "target_highlight";
            sprite.scaleX = 1;
            sprite.scaleY = 1;
            sprite.rot = 0;
            
            Position p = new Position();
            Position pTarg = (Position)t.getComponent(pt);
            p.x = pTarg.x;
            p.y = pTarg.y;
            p.lx = pTarg.lx;
            p.y = pTarg.ly;
            
            PinTo pin = new PinTo();
            pin.target = t;
                        
            highlightTarget.addComponent(sprite);
            highlightTarget.addComponent(p);
            highlightTarget.addComponent(pin);
            
            highlightTarget.changedInWorld();
            
            targetJet = t;
        }        
    }
    
    public void setEntityWithSelectionHighlight(Entity s)
    {
        if(s == selectedJet)
            return;
        
        if(selectedJet != null)
        {
            highlight.removeComponent(ptt);
            highlight.removeComponent(spritet);
            highlight.removeComponent(pt);
            
            highlight.changedInWorld();   
            
            selectedJet = null;
        }
        
        if(s != null)
        {
            Sprite sprite = new Sprite();
            sprite.name = "selection_highlight";
            sprite.scaleX = 1;
            sprite.scaleY = 1;
            sprite.rot = 0;
            
            Position p = new Position();
            Position pTarg = (Position)s.getComponent(pt);
            p.x = pTarg.x;
            p.y = pTarg.y;
            p.lx = pTarg.lx;
            p.y = pTarg.ly;
            
            PinTo pin = new PinTo();
            pin.target = s;
                        
            highlight.addComponent(sprite);
            highlight.addComponent(p);
            highlight.addComponent(pin);
            
            highlight.changedInWorld();
            
            selectedJet = s;
        }
    }
    
//    public void setMouseCursor(CursorType c)
//    {
        //gc.setMouseCursor(arg0, arg1, arg2)
//    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type, Entity e)
    {        
        if(type == Jet.class)
            return (T)e.getComponent(jt);
                
        if(type == Position.class)
            return (T)e.getComponent(pt);
        
        throw new IllegalArgumentException();
    }
}
