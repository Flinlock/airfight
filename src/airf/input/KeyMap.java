package airf.input;

import org.newdawn.slick.Input;

/**
 * This class contains the mapping from keys / mouse buttons to commands. This
 * extra level of abstraction allows the separation of keys and what they keys
 * do, so that we can change the keys to anything we want, and only have to 
 * modify this class.
 *  
 * @author Absolom
 *
 */
public class KeyMap
{
    static Command keyToCommand(int key, boolean shiftDown)
    {
        Command ret = Command.NO_COMMAND;
        switch(key)
        {
            case Input.KEY_END:
            {
                ret = Command.SELECT_TARGET;
                break;
            }
            case Input.KEY_LEFT:
            {
                if(shiftDown)
                    ret = Command.HARD_LEFT;
                else
                    ret = Command.SOFT_LEFT;
                break;
            }
            case Input.KEY_RIGHT:
            {
                if(shiftDown)
                    ret = Command.HARD_RIGHT;
                else
                    ret = Command.SOFT_RIGHT;
                break;
            }
            case Input.KEY_UP:
            {
                ret = Command.ACCELERATE;
                break;
            }
            case Input.KEY_DOWN:
            {
                ret = Command.DECELERATE;
                break;
            }
        }
        
        return ret;
    }
    
    static Command mouseToCommand(int button, boolean shiftDown)
    {
        Command ret = Command.NO_COMMAND;
        
        if(button == Input.MOUSE_LEFT_BUTTON)
            ret = Command.SELECT;
        
        return ret;
    }
    
}
