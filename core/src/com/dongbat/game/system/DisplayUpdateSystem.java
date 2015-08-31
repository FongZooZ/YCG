package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 * Created by FongZooZ on 8/31/2015.
 */
public class DisplayUpdateSystem extends EntityProcessingSystem {


    /**
     * Creates a new EntityProcessingSystem.
     */
    public DisplayUpdateSystem() {
        super(Aspect.all(Physics.class, Display.class));
    }

    /**
     * Process a entity this system is interested in.
     *
     * @param e the entity to process
     */
    @Override
    protected void process(Entity e) {
        Display display = EntityUtil.getComponent(world, e, Display.class);
        display.setPosition(PhysicsUtil.getPosition(world, e));
        display.setRadius(PhysicsUtil.getRadius(world, e) * 2);
        display.setRotation(PhysicsUtil.getVelocity(world, e).angle());
    }
}