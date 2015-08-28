/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author Admin
 */
public class AiControlledSystem extends EntityProcessingSystem {

	public AiControlledSystem() {
		super(Aspect.all(AiControl.class));
	}

	@Override
	protected void process(Entity e) {
		AiControl ai = EntityUtil.getComponent(world, e, AiControl.class);
		if (ai.getTree() == null) {
			if (ai.getDefinitionPath() != null) {
				BehaviorTree<Entity> behaviorTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(ai.getDefinitionPath());
				ai.setTree(behaviorTree);
			}
		}

		ai.getTree().setObject(e);
		ai.getTree().step();
	}

}
