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
 *
 * @author Admin
 */
public class Box2dSystem extends BaseSystem {

	@Override
	protected void processSystem() {
		World physicsWorld = PhysicsUtil.getPhysicsWorld(world);
		physicsWorld.step(world.delta, 8, 3);

	}

}
