package airf.demo;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import airf.AirFightMain;
import airf.Constants;
import airf.EntityFactory;
import airf.EntityFactory.JetType;
import airf.component.Jet;
import airf.input.InputToIntent;
import airf.system.HeadingSystem;
import airf.system.JetSystem;
import airf.system.MovementSystem;
import airf.system.PathSystem;
import airf.system.SpriteRenderSystem;

import com.artemis.Entity;
import com.artemis.World;

public class MainPathingQueue extends BasicGame
{
    // TODO: All courses should be changed to be the same length (in time), or divisible by some minimum
    //       length.
    // TODO: Eventually change all velocity/updates to remove dependency on worldDelta which is 
    //       now fixed.
    
    public static void main(String[] args) throws SlickException, FileNotFoundException
    {
        AppGameContainer app = new AppGameContainer(new AirFightMain(new World()));
        app.setDisplayMode(Constants.WIDTH, Constants.HEIGHT, false);
        app.start();
    }
    
    private World world;
    private SpriteRenderSystem spriteRenderSystem;
//    private int UPDATE_PERIOD = 17;//1000 / 60;
    private int UPDATE_PERIOD = 33;//1000 / 60;
    int timeSinceLastUpdate;

    public MainPathingQueue(World w)
    {
        super("AirFight Demo - Pathing Queue");
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
        c.getGraphics().setBackground(Color.white);
        
        InputToIntent mapper = new InputToIntent();
        c.getInput().addMouseListener(mapper);
        c.getInput().addKeyListener(mapper);
                
        world.setSystem(new PathSystem());
        world.setSystem(new MovementSystem());
        world.setSystem(new HeadingSystem());
        JetSystem jsystem = new JetSystem();
        world.setSystem(jsystem);
        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(), true);

        world.initialize();
                        
        Entity testJet = EntityFactory.createJet(world, 250, 150, 0.01f, 0.01f, 0, JetType.WHITE, jsystem);
        testJet.addToWorld();
        mapper.setPlayerJet(testJet.getComponent(Jet.class));
        
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
