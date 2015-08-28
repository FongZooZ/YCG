/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.objectUtil.Constants;

/**
 * @author Admin
 */
public class UnitUtil {

  /**
   * Make entity A eat entity B
   *
   * @param world artemis world
   * @param a entity A
   * @param b entity B
   */
  public static void eat(World world, Entity a, Entity b) {
    if (a == null || b == null) {
      return;
    }

    boolean consumable = canEat(world, a) && canBeEaten(world, b);
    boolean toxic = isToxic(world, b);

    if (consumable && !toxic) {
      float increaseRadius = PhysicsUtil.increaseRadius(PhysicsUtil.getRadius(world, a), PhysicsUtil.getRadius(world, b), 1f);
      PhysicsUtil.setRadius(world, a, increaseRadius);
      destroy(b);
      return;
    }
    if (consumable && toxic) {
      if (PhysicsUtil.getRadius(world, a) < 2) {
        return;
      }
      float increaseRadius = PhysicsUtil.increaseRadius(PhysicsUtil.getRadius(world, a), PhysicsUtil.getRadius(world, b), -1f);
      PhysicsUtil.setRadius(world, a, increaseRadius);
      destroy(b);
    }
  }

  /**
   * Check an entity can be eaten or not
   *
   * @param world artemis world
   * @param e entity that you want to check
   * @return true if that entity can be eaten
   */
  public static boolean canBeEaten(World world, Entity e) {
    Stats stats = EntityUtil.getComponent(world, e, Stats.class);

    if (stats == null) {
      return true;
    }

    return stats.isConsumable();
  }

  /**
   * Check an entity can eat other unit or not
   *
   * @param world artemis world
   * @param e entity that you want to check
   * @return true if that entity can eat other unit
   */
  public static boolean canEat(World world, Entity e) {
    Stats stats = EntityUtil.getComponent(world, e, Stats.class);
    if (stats == null) {
      return false;
    }

    return stats.isAllowComsumming();
  }

  /**
   * Destroy an entity
   *
   * @param e entity that you want to destroy
   */
  public static void destroy(Entity e) {
    if (e == null) {
      return;
    }
    com.badlogic.gdx.physics.box2d.World physicsWorld = PhysicsUtil.getPhysicsWorld(e.getWorld());
    Physics physics = EntityUtil.getComponent(e.getWorld(), e, Physics.class);
    if (physics == null) {
      return;
    }
    if (physics.getBody() == null) {
      return;
    }
    if (!physics.getBody().isActive()) {
      return;
    }

    if (EntityUtil.isAiUnit(e.getWorld(), e.getId()) || EntityUtil.isPlayer(e.getWorld(), e.getId())) {
      PhysicsUtil.setRadius(e.getWorld(), e, 2);
      PhysicsUtil.setPosition(e.getWorld(), e, new Vector2());
      return;
    }

    physicsWorld.destroyBody(physics.getBody());
    e.deleteFromWorld();
  }

  /**
   * Calculate speed of an entity
   *
   * @param world artemis world
   * @param a entity that you wan to check
   */
  public static void speedCalculation(World world, Entity a) {
    if (a == null) {
      return;
    }
    Stats stat = EntityUtil.getComponent(world, a, Stats.class);
    float speed = 15000 / PhysicsUtil.getRadius(world, a) + stat.getBaseRateSpeed() * 15;
    if (speed >= Constants.PHYSICS.MAX_VELOCITY) {
      speed = Constants.PHYSICS.MAX_VELOCITY;
    }
    stat.setBaseMovementSpeed(speed);
  }

  private static boolean isToxic(World world, Entity b) {
    Food foodComponent = EntityUtil.getComponent(world, b, Food.class);
    if (foodComponent != null) {
      return foodComponent.isToxic();
    }
    return false;
  }
}