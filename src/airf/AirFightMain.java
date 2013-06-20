package airf;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import airf.EntityFactory.JetType;
import airf.component.Jet;
import airf.input.InputToIntent;
import airf.pathing.ManeuverFactory;
import airf.states.jet.player.IdleState;
import airf.system.DiscreteTimeSystem;
import airf.system.HeadingSystem;
import airf.system.JetSystem;
import airf.system.MovementSystem;
import airf.system.PathSystem;
import airf.system.SpriteRenderSystem;

import com.artemis.Entity;
import com.artemis.World;

public class AirFightMain extends BasicGame
{    
    public static void main(String[] args) throws SlickException, FileNotFoundException
    {
        AppGameContainer app = new AppGameContainer(new AirFightMain(new World()));
        app.setDisplayMode(800, 600, false);
        app.start();
    }
    
//    private World world;
//    private SpriteRenderSystem spriteRenderSystem;
    int timeSinceLastUpdate;

    public AirFightMain(World w)
    {
        super("AirFight Proto - Maneuvers Demo");
//        world = w;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.clear();
//        spriteRenderSystem.process();
    }

    @Override
    public void init(GameContainer c) throws SlickException
    {   
//        ManeuverFactory mf = new ManeuverFactory(Constants.TIME_SLOT_PERIOD);
//        
//        c.getGraphics().setBackground(Color.white);
//        
//        InputToIntent mapper = new InputToIntent();
//        c.getInput().addMouseListener(mapper);
//        c.getInput().addKeyListener(mapper);
//                
//        DiscreteTimeSystem dts = new DiscreteTimeSystem(Constants.TIME_SLOT_PERIOD / Constants.UPDATE_PERIOD, mf); 
//        world.setSystem(dts);
//        JetSystem jsystem = new JetSystem();
//        world.setSystem(jsystem);
//        world.setSystem(new PathSystem());
////        world.setSystem(new MovementSystem());
////        world.setSystem(new HeadingSystem());
//        
//        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(), true);
//        
//        world.initialize();        
//                
//        Entity jet = EntityFactory.createJet(world, 150, 150, false, JetType.WHITE, 
//                mf.createCourseStraight(0, false),
//                new IdleState(jsystem, mf));
//        jet.addToWorld();
//        mapper.setPlayerJet(jet.getComponent(Jet.class));
//                
//        timeSinceLastUpdate = 0;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
//        timeSinceLastUpdate += delta;
//        while(timeSinceLastUpdate >= Constants.UPDATE_PERIOD)
//        {
//            world.setDelta(Constants.UPDATE_PERIOD);
//            world.process();
//            timeSinceLastUpdate -= Constants.UPDATE_PERIOD;
//        }
    }

}
