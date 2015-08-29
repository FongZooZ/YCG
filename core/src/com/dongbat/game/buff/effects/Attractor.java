package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Attractor implements BuffEffect {

  private float radius;
  private float forceStrength;
  private int attractedTime;

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
    Vector2 playerPosition = PhysicsUtil.getPosition(world, source);
    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, playerPosition, radius);
    for (Entity e : foundList) {
      if (PhysicsUtil.getRadius(world, source) < PhysicsUtil.getRadius(world, e)) {
        return;
      }
      if (e != source) {
        Vector2 victimPosition = PhysicsUtil.getPosition(world, e);
        Vector2 direction = victimPosition.cpy().sub(playerPosition).cpy().scl(-1).nor();
        BuffUtil.addBuff(world, source, e, "Forced", attractedTime, 1, "forceStrength", forceStrength, "direction", direction);
      }
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
