package airf.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Input;

import airf.component.Position;
import airf.component.Select;
import airf.component.Sprite;
import airf.states.selectable.Selected;
import airf.states.selectable.Unselected;

import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;

public class InputToIntentTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testSelectJet()
    {
        int screenHeight = 1000;
        World w = new World();
        InputToIntent i = new InputToIntent(screenHeight);
        Entity jet1 = w.createEntity();
        Entity jet2 = w.createEntity();
        
        /////
        
        Position pos = new Position();
        pos.x = 0;
        pos.y = 0;
        jet1.addComponent(pos);
        
        Select s = new Select();
        Entity eHl = w.createEntity();
        eHl.addToWorld();
        s.state = new Unselected(jet1, ComponentType.getTypeFor(Sprite.class), 
                ComponentType.getTypeFor(Position.class), eHl);
        jet1.addComponent(s);
        jet1.addToWorld();
        
        i.addJet(jet1);
        
        /////
        
        pos = new Position();
        pos.x = 200;
        pos.y = 200;
        jet2.addComponent(pos);
        
        s = new Select();
        eHl = w.createEntity();
        eHl.addToWorld();
        s.state = new Unselected(jet2, ComponentType.getTypeFor(Sprite.class), 
                ComponentType.getTypeFor(Position.class), eHl);
        jet2.addComponent(s);
        jet2.addToWorld();
        
        i.addJet(jet2);
        
        /////
        
        i.mousePressed(Input.MOUSE_LEFT_BUTTON, 2, 900);
        assertEquals(Unselected.class, jet2.getComponent(Select.class).state.getClass());
        assertEquals(Selected.class, jet1.getComponent(Select.class).state.getClass());
        i.mousePressed(Input.MOUSE_LEFT_BUTTON, 200, 700);
        assertEquals(Unselected.class, jet1.getComponent(Select.class).state.getClass());
        assertEquals(Selected.class, jet2.getComponent(Select.class).state.getClass());
    }

}
