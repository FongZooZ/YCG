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
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.localUtil.Constants;

/**
 *
 * @author Admin
 */
public class Respawn implements BuffEffect {

  private float minRadius = 0.4f;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    PhysicsUtil.setRadius(source.getWorld(), source, 0f);
    EntityUtil.getComponent(world, target, Stats.class).setAllowComsumming(false);
    EntityUtil.getComponent(world, target, Stats.class).setConsumable(false);
    PhysicsUtil.setVelocity(world, target, new Vector2());
    EntityUtil.getComponent(world, target, UnitMovement.class).setDisabled(true);
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    EntityUtil.getComponent(world, target, Stats.class).setAllowComsumming(true);
    EntityUtil.getComponent(world, target, Stats.class).setConsumable(true);
    EntityUtil.getComponent(world, target, UnitMovement.class).setDisabled(false);

    PhysicsUtil.setRadius(target.getWorld(), target, minRadius);
    Vector2 randomPos
      = new Vector2(MathUtils.random(-Constants.GAME.FRAME_WIDTH / 2, Constants.GAME.FRAME_WIDTH / 2), MathUtils.random(-Constants.GAME.FRAME_HEIGHT / 2, Constants.GAME.FRAME_HEIGHT / 2));
    PhysicsUtil.setPosition(target.getWorld(), target, randomPos);
  }

}