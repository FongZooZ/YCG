/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.factory;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Queen;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;

import java.util.UUID;

import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;

/**
 * @author Admin
 */
public class UnitFactory {

  /**
   * Create sub-unit from an entity
   *
   * @param world         artemis world
   * @param parent        parent of sub-unit
   * @param position      spawn position
   * @param baseRateSpeed base speed
   * @param radius        radius of sub-unit
   * @param args          optional arguments for sub-unit
   * @return sub-unit entity that was just created
   */
  public static Entity createSubUnit(World world, Entity parent, Vector2 position, float baseRateSpeed, float radius, Object... args) {
    Entity e = world.createEntity();

    Stats stats = new Stats();
    stats.setBaseRateSpeed(baseRateSpeed);
    stats.setParent(world.getManager(UuidEntityManager.class).getUuid(e));

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));

//    DisplayPosition displayPosition = new DisplayPosition();
    Collision collision = new Collision();
    e.edit().add(physics)
            .add(stats)
            .add(new BuffComponent())
            .add(collision)
            .add(new UnitMovement());
    return e;
  }

  /**
   * Create projectile unit
   *
   * @param world    artemis world
   * @param parent   entity that execute that projectile unit
   * @param position spawn position
   * @param radius   unit radius
   * @return projectile unit that was just created
   */
  public static Entity createProjectileUnit(World world, Entity parent, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);
    stats.setParent(world.getManager(UuidEntityManager.class).getUuid(e));

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    e.edit().add(physics)
            .add(stats)
            .add(new BuffComponent())
            .add(new Collision())
            .add(new UnitMovement());

    return e;
  }

  public static Entity createQueen(World world, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);
    stats.setBaseRateSpeed(2000);

//    AbilityComponent abilityComponent = new AbilityComponent();
    BuffComponent buff = new BuffComponent();

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    UnitMovement movement = new UnitMovement();
    float posX = (float) ((Math.random() * 2 - 1) * scaleX);
    float posY = (float) ((Math.random() * 2 - 1) * scaleY);
    movement.setDirectionVelocity(new Vector2(posX, posY));
    e.edit().add(physics)
            .add(stats)
            .add(new Collision())
            .add(buff)
            .add(new Queen())
            .add(movement);
    BuffUtil.addBuff(world, e, e, "QueenTeleportSchedule", 99999999, 1);
    BuffUtil.addBuff(world, e, e, "ProduceFoodSchedule", 99999999, 1);
    BuffUtil.addBuff(world, e, e, "QueenGrowth", 99999999, 1);
    return e;
  }
}
