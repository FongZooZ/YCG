/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.util.objectUtil.Constants;

/**
 * @author Admin
 */
public class MovementUtil {

  /**
   * Move an unit
   *
   * @param world artemis world
   * @param e entity want to move
   * @param isImpulse is receive impulse, force or not
   */
  public static void move(World world, Entity e, boolean isImpulse) {
    UnitMovement unitMovement = EntityUtil.getComponent(world, e, UnitMovement.class);
    Stats stat = EntityUtil.getComponent(world, e, Stats.class);
    float scale = stat.getBaseMovementSpeed() + stat.getModifierSpeed();

    Physics physicsComponent = EntityUtil.getComponent(world, e, Physics.class);
    Body body = physicsComponent.getBody();
    float mass = body.getMass();
    Vector2 currentVelocity = body.getLinearVelocity();
    Vector2 position = body.getPosition();
    if (unitMovement.getDirectionVelocity() == null) {

      body.setLinearVelocity(new Vector2(0, 0));
      return;
    }
    if (unitMovement.getDirectionVelocity().cpy().sub(position.cpy()).len() < PhysicsUtil.getcollisionRadius(world, e)) {
      body.setLinearVelocity(new Vector2(0, 0));
    }

//		Vector2 desiredVelocity = unitMovement.getDirectionVelocity().cpy().sub(PhysicsUtil.getPosition(world, e).cpy()).nor().scl(scale * world.delta);
    // TODO: fix this
    Vector2 desiredVelocity = unitMovement.getDirectionVelocity().cpy().nor().scl(scale / 10 * world.delta * 10);
    Vector2 impulse = desiredVelocity.sub(currentVelocity).scl(1f / mass);

    if (isImpulse) {
//      body.setLinearVelocity(desiredVelocity);
      PhysicsUtil.applyImpulse(world, e, impulse);

//      PhysicsUtil.applyForceToCenter(world, e, impulse);
    } else {
//      body.setLinearVelocity(desiredVelocity);
      PhysicsUtil.applyImpulse(world, e, impulse);

//      PhysicsUtil.applyForce(world, e, desiredVelocity);
    }
    Vector2 add = position.cpy().add(body.getLinearVelocity().cpy().scl(world.getDelta()));
    Vector2 v1 = add.cpy().sub(unitMovement.getDirectionVelocity());
    Vector2 v2 = position.sub(unitMovement.getDirectionVelocity());
    if (v1.dot(v2) < 0) {
      unitMovement.setDirectionVelocity(null);
    }

    float angleRad = body.getLinearVelocity().angleRad();
    body.setTransform(body.getPosition(), angleRad);
  }

  public static void newMovementMechanism(World world, Entity e) {
    UnitMovement unitMovement = EntityUtil.getComponent(world, e, UnitMovement.class);

    Physics physicsComponent = EntityUtil.getComponent(world, e, Physics.class);
    Body body = physicsComponent.getBody();
    float mass = body.getMass();
    if (unitMovement.getDirectionVelocity() == null) {
//      body.setLinearVelocity(new Vector2(0, 0));
      return;
    }

    Vector2 directionVel = unitMovement.getDirectionVelocity().cpy();

    // TODO: calculate based on radius, use fixed mass
    // or make a steering-like behavior with real mass
    float desiredSpd = calculalteDesiredSpeed(world, e);
    Vector2 currentVector = body.getLinearVelocity();

    directionVel.nor().scl(desiredSpd).sub(currentVector);

    Vector2 impulse = directionVel.scl(mass);

    PhysicsUtil.applyImpulse(world, e, impulse);
  }

  public static float calculalteDesiredSpeed(World world, Entity e) {
    float radius = PhysicsUtil.getcollisionRadius(world, e);

    return 20;
  }

  /**
   * Disable movement of an entity
   *
   * @param e entity that you want to disable movement
   */
  public static void disableMovement(Entity e) {
    UnitMovement ums = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    if (ums == null) {
      return;
    }
    ums.setDisabled(true);
  }

  /**
   * Enable movement of an entity
   *
   * @param e entity that you want to enable movement
   */
  public static void enableMovement(Entity e) {
    UnitMovement ums = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    if (ums == null) {
      return;
    }
    ums.setDisabled(false);
  }

  /**
   * Move unit to specific location on map
   *
   * @param entity entity want to move
   * @param destination location that entity will move to
   */
  public static void moveTo(Entity entity, Vector2 destination) {
    World world = entity.getWorld();
    Physics physicsComponent = EntityUtil.getComponent(world, entity, Physics.class);
    Stats stat = EntityUtil.getComponent(world, entity, Stats.class);
    Body body = physicsComponent.getBody();
    float mass = body.getMass();
    Vector2 currentVelocity = body.getLinearVelocity();
    Vector2 position = body.getPosition();
    float scale = stat.getBaseMovementSpeed() + stat.getModifierSpeed();
    Vector2 desiredVelocity = destination.cpy().sub(position).nor().scl(scale);

    Vector2 impulse = desiredVelocity.sub(currentVelocity).scl(mass * 10);

    PhysicsUtil.applyImpulse(entity.getWorld(), entity, impulse);
  }

  /**
   * Check entity has arrived to destination or not
   *
   * @param e entity that you want to check
   * @return true if entity has arrived
   */
  public static boolean hasArrived(Entity e) {
    UnitMovement ms = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    Vector2 position = PhysicsUtil.getPosition(e.getWorld(), e);
    Vector2 intent = ms.getDirectionVelocity();
    return intent.epsilonEquals(position, Constants.GAME.EPSILON);
  }

  /**
   * Set target for an entity, that entity will seek to target
   *
   * @param e entity that you want to set target for
   * @param target entity that you want to set target to
   */
  public static void setTarget(Entity e, Entity target) {
    Vector2 position = PhysicsUtil.getPosition(target.getWorld(), target);
    setTarget(e, position);
  }

  /**
   * Set target for an entity, that entity will seek to target (location) on map
   *
   * @param e entity that you want to set target for
   * @param target location that you want to set target to
   */
  public static void setTarget(Entity e, Vector2 target) {
    UnitMovement component = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    component.setDirectionVelocity(target);

  }

  public static void addMoveInput(World world, Entity e, Vector2 destination) {
    long lastFrameIndex = ECSUtil.getFrame(world);

    CustomInput customInput = new CustomInput(Constants.inputType.MOVE, destination, 0);

    EntityUtil.getComponent(e.getWorld(), e, Player.class).getInputs().put(lastFrameIndex + 3, customInput);

  }
}
