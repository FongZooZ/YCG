/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class Forced implements BuffEffect {

  private float forceStrength;
  private Vector2 direction;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    if (direction == null) {
      float posX = (float) MathUtils.random(-1f, 1f);
      float posY = (float) MathUtils.random(-1f, 1f);
      direction = new Vector2(posX, posY);
    }
    MovementUtil.disableMovement(target);
    PhysicsUtil.setVelocity(world, target, new Vector2());
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    PhysicsUtil.applyImpulse(world, target, direction.cpy().nor().scl(forceStrength));
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    MovementUtil.enableMovement(target);
    PhysicsUtil.setVelocity(world, target, new Vector2());
  }
}
