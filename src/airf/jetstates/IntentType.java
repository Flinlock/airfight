package airf.jetstates;


public enum IntentType 
{
    HARDL(0), 
    TEST(100), 
    HARDR(1), 
    SOFTR(2), 
    SOFTL(3), 
    ACCEL(4), 
    DEACCEL(5);
    
    int num;
    
    IntentType(int num)
    {
        this.num = num;
    }
};