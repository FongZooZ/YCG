/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class BorderlandSystem extends EntityProcessingSystem {

  boolean pushing = false;

  public BorderlandSystem() {
    super(Aspect.all(Physics.class));

  }

  @Override
  protected void process(Entity e) {
    Physics physics = EntityUtil.getComponent(world, e, Physics.class);
    UnitMovement movement = EntityUtil.getComponent(world, e, UnitMovement.class);
    Vector2 pos = physics.getBody().getPosition();
    if (movement == null) {
      return;
    }
    if (pos.x > scaleX) {

      BuffUtil.addBuff(world, e, e, "Forced", 50, 1, "forceStrength", 0.5f, "direction", new Vector2(-1, 0));
    }

    if (pos.x < -scaleX) {
      BuffUtil.addBuff(world, e, e, "Forced", 50, 1, "forceStrength", 0.5f, "direction", new Vector2(1, 0));

    }

    if (pos.y < -scaleY) {
      BuffUtil.addBuff(world, e, e, "Forced", 50, 1, "forceStrength", 0.5f, "direction", new Vector2(0, 1));

    }

    if (pos.y > scaleY) {
      BuffUtil.addBuff(world, e, e, "Forced", 50, 1, "forceStrength", 0.5f, "direction", new Vector2(0, -1));

    }

  }

}
