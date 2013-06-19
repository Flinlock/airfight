package airf.demo;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import airf.EntityFactory;
import airf.EntityFactory.JetType;
import airf.component.Jet;
import airf.input.InputToIntent;
import airf.jetstates.IdleState;
import airf.pathing.ManeuverFactory;
import airf.system.DiscreteTimeSystem;
import airf.system.JetSystem;
import airf.system.PathSystem;
import airf.system.SpriteRenderSystem;

import com.artemis.Entity;
import com.artemis.World;

public class ManeuverDemo extends BasicGame
{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int QUEUE_MAX = 3;
    public static final int TIME_SLOT_PERIOD = 5000;
    public static final int UPDATE_PERIOD = 33;
    
    public static void main(String[] args) throws SlickException, FileNotFoundException
    {
        AppGameContainer app = new AppGameContainer(new ManeuverDemo(new World()));
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.start();
    }
    
    private World world;
    private SpriteRenderSystem spriteRenderSystem;
    int timeSinceLastUpdate;

    public ManeuverDemo(World w)
    {
        super("AirFight Proto - Maneuvers Demo");
        world = w;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.clear();
        spriteRenderSystem.process();
    }

    @Override
    public void init(GameContainer c) throws SlickException
    {   
        ManeuverFactory mf = new ManeuverFactory(TIME_SLOT_PERIOD);
        
        c.getGraphics().setBackground(Color.white);
        
        InputToIntent mapper = new InputToIntent();
        c.getInput().addMouseListener(mapper);
        c.getInput().addKeyListener(mapper);
                
        DiscreteTimeSystem dts = new DiscreteTimeSystem(TIME_SLOT_PERIOD / UPDATE_PERIOD, mf); 
        world.setSystem(dts);
        JetSystem jsystem = new JetSystem();
        world.setSystem(jsystem);
        world.setSystem(new PathSystem());
        
        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(HEIGHT), true);
        
        world.initialize();        
                
        Entity jet = EntityFactory.createJet(world, 150, 150, false, JetType.WHITE, 
                mf.createCourseStraight(0, false),
                new IdleState(jsystem, mf),
                QUEUE_MAX);
        jet.addToWorld();
        mapper.setSelectedJet(jet);
                
        timeSinceLastUpdate = 0;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        timeSinceLastUpdate += delta;
        while(timeSinceLastUpdate >= UPDATE_PERIOD)
        {
            world.setDelta(UPDATE_PERIOD);
            world.process();
            timeSinceLastUpdate -= UPDATE_PERIOD;
        }
    }

}
