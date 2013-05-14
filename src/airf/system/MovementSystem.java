package airf.system;

import airf.component.Position;
import airf.component.Path;
import airf.component.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;
    
    @SuppressWarnings("unchecked")
    public MovementSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Velocity.class).exclude(Path.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Velocity v = vm.get(e);
        
        p.lx = p.x;
        p.ly = p.y;
        
        p.x += world.delta*v.x;
        p.y += world.delta*v.y;
    }

}
