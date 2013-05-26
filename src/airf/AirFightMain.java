package airf;

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
import airf.system.HeadingSystem;
import airf.system.JetSystem;
import airf.system.MovementSystem;
import airf.system.PathSystem;
import airf.system.SpriteRenderSystem;

import com.artemis.Entity;
import com.artemis.World;

public class AirFightMain extends BasicGame
{
    // THINK_TASK: This game can go two different ways, action type game, or tactical game.  To get the
    //    tactical feel, when make a decision about maneuvers, there should be a need to predict what the
    //    target jet is going to do to stay behind it, and teamwork can be used to reduce number of 
    //    escape opportunities.
    //  Since I want this to be more tactical, I'm thinking about testing out discreet time.  Every move
    //    takes the same amount of time and you have to queue up the next move by the next maneuver time
    //    slot, otherwise the plane defaults to straight flight.  Thus everyone's maneuver decisions are
    //    made simultaneously which requires predicting what your opponent will do.
    
    // TASK: Get an AI controlled airplane implemented that flies around randomly but stays on the screen
    // TASK: Implment gun attacks, then try to shoot down the AI plane and tweak maneuvers/gun parameters
    // TASK: Implement animation (guns, airplane turning)
    
    // TODO: Remove degrees from the entirety of the program, use Radians only
    // TODO: Change all time dependent calculations to use a fixed deltaT, change game loop to constant 
    //       60 logic fps, unlimited graphics fps (probably wont need intraframe interpolation, so might
    //       leave it at fixdd graphics fps as well)
    // TODO: Consider refactoring the FSM and structure of the jet code as it evolves
    // TODO: PathSystem should throw an exception if the course velocity ever goes negative
    
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
                
//        Entity testJet = EntityFactory.createJet(world, 150, 150, 0.01f, 0.01f, 0, JetType.WHITE, jsystem);
//        testJet.addToWorld();
//        mapper.setPlayerJet(testJet.getComponent(Jet.class));
        
        Entity enemyJet = EntityFactory.createAIJet(world, 250, 150, 0.01f, 0.01f, 0, JetType.BLACK, jsystem);
        enemyJet.addToWorld();
        
        timeSinceLastUpdate = 0;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        timeSinceLastUpdate += delta;
        while(timeSinceLastUpdate >= UPDATE_PERIOD)
//        if(timeSinceLastUpdate >= UPDATE_PERIOD )
        {
            world.setDelta(UPDATE_PERIOD);
            world.process();
            timeSinceLastUpdate -= UPDATE_PERIOD;
        }
    }

}
