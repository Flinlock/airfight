package airf;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import util.bezier.BezierCurve;
import airf.EntityFactory.JetType;
import airf.component.Path;
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
        app.setDisplayMode(1024, 768, false);
        app.start();
    }
    
    private World world;
    private SpriteRenderSystem spriteRenderSystem;
    private int UPDATE_PERIOD = 17;//1000 / 60;
    int timeSinceLastUpdate;

    public AirFightMain(World w)
    {
        super("AirFight Proto");
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
        
//        InputMapperSystem mapper = new InputMapperSystem();
//        c.getInput().addMouseListener(mapper);
//        c.getInput().addKeyListener(mapper);
//        world.setSystem(mapper);
        
        world.setSystem(new PathSystem());
        world.setSystem(new MovementSystem());
        world.setSystem(new HeadingSystem());
        world.setSystem(new JetSystem());
        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(), true);

        world.initialize();
        
        BezierCurve curve = new BezierCurve();
        curve.setAnchorStart(0, 0);
        curve.setAnchorEnd(600, 600);
        curve.setControlPointOne(800, 0);
        curve.setControlPointTwo(800, 0);
        curve.calculateLength(0.01f);
        PathDefinition path = new PathDefinition(curve);
        Path pathc = new Path();
        pathc.p = 0;
        pathc.v = (float)Math.sqrt(0.01f*0.01f + 0.02f*0.02f);
        pathc.path = path;
        
        Entity testJet = EntityFactory.createJet(world, 50, 50, 0.01f, 0.02f, 270, JetType.WHITE);
        testJet.addComponent(pathc);
        testJet.addToWorld();
        
        timeSinceLastUpdate = 0;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        timeSinceLastUpdate += delta;
//        if(timeSinceLastUpdate >= UPDATE_PERIOD )
//        {
            world.setDelta(timeSinceLastUpdate);
            world.process();
            timeSinceLastUpdate = 0;
//        }
    }

}
