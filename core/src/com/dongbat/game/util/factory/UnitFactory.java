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
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.DisplayPosition;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Queen;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;

import java.util.UUID;

import static com.dongbat.game.registry.UnitRegistry.get;
import static com.dongbat.game.registry.UnitRegistry.setUnitData;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;

/**
 * @author Admin
 */
public class UnitFactory {

  /**
   * Create projectile unit
   *
   * @param world artemis world
   * @param parent entity that execute that projectile unit
   * @param position spawn position
   * @param radius unit radius
   * @return projectile unit that was just created
   */
  public static Entity createProjectileUnit(World world, Entity parent, Vector2 position, float radius, boolean allowConsumming, boolean consumable) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(allowConsumming);
    stats.setConsumable(consumable);
    stats.setParent(world.getManager(UuidEntityManager.class).getUuid(e));

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
    physics.getBody().setBullet(true);

    e.edit().add(physics)
      .add(stats)
      .add(new BuffComponent())
      .add(new Collision())
      .add(new Detection())
      .add(new UnitMovement());

    return e;
  }

  /**
   * Create unit and add to artemis world
   *
   * @param world artemis world
   * @param unitType type of unit in String
   * @param position spawn position
   * @param args optional arguments
   * @return Unit that was just created
   */
  public static Entity createUnit(World world, String unitType, Vector2 position, Object... args) {
    Entity e = world.createEntity(UUID.randomUUID());

    UnitInfo unitInfo = get(unitType);
    setUnitData(world, e, args);

    Collision collision = new Collision();

    DisplayPosition displayPosition = new DisplayPosition();

    Stats stats = new Stats();
    stats.setBaseRateSpeed(unitInfo.getBaseSpeedRate());

    Physics physics = new Physics();
    e.edit().add(physics);
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, unitInfo.getRadius(), e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
    UnitMovement movement = new UnitMovement();

    AiControl aiControl = new AiControl(unitInfo.getDefinitionPath());
    e.edit()
      .add(new BuffComponent())
      .add(aiControl)
      .add(new Player())
      .add(new Detection())
      .add(displayPosition)
      .add(stats)
      .add(movement)
      .add(collision);
    BuffUtil.addBuff(world, e, e, "FeedSmaller", -1, 1);

    return e;
  }

  public static Entity createQueen(World world, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);
    stats.setBaseRateSpeed(2000);

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
      .add(new Detection())
      .add(movement);

    BuffUtil.addBuff(world, e, e, "QueenTeleportSchedule", -1, 1);
    BuffUtil.addBuff(world, e, e, "ProduceFoodSchedule", -1, 1);
    BuffUtil.addBuff(world, e, e, "FeedSmaller", -1, 1);
    BuffUtil.addBuff(world, e, e, "SelfDefense", -1, 1);
    BuffUtil.addBuff(world, e, e, "QueenGrowth", 99999999, 1);
    return e;
  }
}
