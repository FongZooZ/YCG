/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.dongbat.game.component.Collision;
import com.dongbat.game.util.EntityUtil;

/**
 * @author Admin
 */
public class CollisionCleanupSystem extends EntityProcessingSystem {


    public CollisionCleanupSystem() {
        super(Aspect.all(Collision.class));
    }

    /**
     * Process a entity this system is interested in.
     *
     * @param e the entity to process
     */
    @Override
    protected void process(Entity e) {
        if (e == null) {
            return;
        }
        Collision collision = EntityUtil.getComponent(world, e, Collision.class);
//            collision.getCollidedList().clear();
        collision.getJustCollidedList().clear();
    }
}
