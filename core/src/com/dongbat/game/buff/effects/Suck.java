package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.util.EntityUtil;

/**
 * Created by FongZooZ on 8/29/2015.
 */
public class Suck implements BuffEffect {
  /**
   * Start buff effect
   *
   * @param world  artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void durationStart(World world, Entity source, Entity target) {

  }

  /**
   * Update buff effect when game loop process
   *
   * @param world  artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void update(World world, Entity source, Entity target) {
    CollisionComponent collision = EntityUtil.getComponent(world, target, CollisionComponent.class);
    if (collision.getJustCollidedList() != null) {
      System.out.println(collision.getJustCollidedList());
    }
  }

  /**
   * End buff when duration is reach maximum value
   *
   * @param world  artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void durationEnd(World world, Entity source, Entity target) {

  }
}
