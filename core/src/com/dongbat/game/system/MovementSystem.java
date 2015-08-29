/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.UnitUtil;

/**
 *
 * @author Admin
 */
public class MovementSystem extends EntityProcessingSystem {

  public MovementSystem() {
    super(Aspect.all(UnitMovement.class, Stats.class, Physics.class));
  }

  @Override
  protected void process(Entity e) {
    UnitUtil.speedCalculation(world, e);
    UnitMovement ms = EntityUtil.getComponent(world, e, UnitMovement.class);
    if (!ms.isDisabled()) {
      MovementUtil.newMovementMechanism(world, e);
    }

  }

}
