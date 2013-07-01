package airf.input;

public interface InputState
{
    public InputState mouseClicked(Command cmd, int x, int y, int count);
    public InputState mouseDragged(int oldx, int oldy, int x, int y);
    public InputState mouseMoved(int oldx, int oldy, int x, int y);
    public InputState mousePressed(Command cmd, int x, int y);
    public InputState mouseReleased(Command cmd, int x, int y);
    public InputState mouseWheelMoved(int delta);
    public InputState keyPressed(Command cmd, char c);
    public InputState keyReleased(Command cmd, char c);
}
