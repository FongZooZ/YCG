/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.dongbat.game.util.PhysicsUtil;

/**
 * @author Admin
 */
public class Box2dSystem extends BaseSystem {

    private World physicWorld;

    @Override
    protected void initialize() {
        physicWorld = PhysicsUtil.getPhysicsWorld(world);
    }

    @Override
    protected void processSystem() {
        physicWorld = PhysicsUtil.getPhysicsWorld(world);
        physicWorld.step(world.getDelta(), 8, 3);

    }

}
