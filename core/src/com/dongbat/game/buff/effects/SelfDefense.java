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
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.EntityFactory;

/**
 *
 * @author Admin
 */
public class SelfDefense implements BuffEffect {

  private int foodPerFrame;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    foodPerFrame = 1;
  }

  @Override
  public void update(World world, Entity source, Entity target) {

    Detection detection = EntityUtil.getComponent(world, target, Detection.class);
    if (detection.getNearestPlayer() != null) {
      System.out.println("minh minh");
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
