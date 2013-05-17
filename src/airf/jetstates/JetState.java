package airf.jetstates;

import com.artemis.Entity;

public interface JetState
{
    public JetState update(Entity e);
    public void intentHardL();
    public void intentHardR();
    public void intentSoftL();
    public void intentSoftR();
    public void intentSpeedUp();
    public void intentSlowDown();
    public boolean changed();
}
