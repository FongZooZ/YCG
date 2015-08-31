package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Vacuum implements BuffEffect {

  private float radius = 5;
  private float degree = 30;
  private float forceStrength;
  private int duration;

  /**
   * Start buff effect
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void durationStart(World world, Entity source, Entity target) {

  }

  /**
   * Update buff effect when game loop process
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void update(World world, Entity source, Entity target) {
    Vector2 playerPosition = PhysicsUtil.getPosition(world, source);
    UnitMovement unitMs = EntityUtil.getComponent(world, target, UnitMovement.class);
    if (unitMs.getDirectionVelocity() == null) {
      return;
    }
    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, playerPosition, radius);
    Array<Entity> filterList = WorldQueryUtil.filterEntityInRay(world, foundList, unitMs.getDirectionVelocity(), degree);
    for (Entity e : filterList) {
      if (e != source) {
        Vector2 victimPosition = PhysicsUtil.getPosition(world, e);
        Vector2 direction = victimPosition.cpy().sub(playerPosition).cpy().scl(-1).nor();
        BuffUtil.addBuff(world, source, e, "Forced", duration, 1, "forceStrength", forceStrength, "direction", direction);
      }
    }
  }

  /**
   * End buff when duration is reach maximum value
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  @Override
  public void durationEnd(World world, Entity source, Entity target) {

  }
}
