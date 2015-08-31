/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.Stats;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;

import java.util.UUID;

import static com.dongbat.game.util.UnitUtil.eat;
import static com.dongbat.game.util.WorldQueryUtil.isFood;

/**
 *
 * @author Admin
 */
public class ConsumingSystem extends EntityProcessingSystem {

  public ConsumingSystem() {
    super(Aspect.all(Collision.class));
  }

  @Override
  protected void process(Entity e) {

    Collision collision = EntityUtil.getComponent(world, e, Collision.class);
    Stats stats = EntityUtil.getComponent(world, e, Stats.class);
    Array<UUID> collidedList = collision.getCollidedList();

    for (UUID idB : collidedList) {
      Entity b = UuidUtil.getEntityByUuid(world, idB);

      if (b == null || !b.isActive()) {
        continue;
      }
      if (stats != null) {
        if (stats.getParent() == idB) {
          continue;
        }
      }

      if (isFood(world, b.getId())) {
        if (PhysicsUtil.isBodyTouch(world, e, b)) {
          eat(world, e, b);
        }
      } else {
        boolean bodyContain = PhysicsUtil.isBodyContain(world, e, b);
        if (bodyContain) {
          eat(world, e, b);
        }
      }
    }

  }

}
