/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.factory;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.DisplayPosition;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.component.UnitType;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.UUID;

/**
 * @author Admin
 */
public class EntityFactory {

  /**
   * Create player unit and add to world
   *
   * @param world    artemis world
   * @param type
   * @param position spawn position
   * @return Player entity that was just created
   */
  public static Entity createPlayer(World world, Vector2 position, String type) {
    Entity e = world.createEntity(UUID.randomUUID());
    Collision collision = new Collision();

    DisplayPosition displayPosition = new DisplayPosition();

    UnitType unitType = new UnitType(type);
    UnitInfo info = UnitRegistry.get(type);

    String abilities = info.getAbilities();

    AbilityComponent abilityComponent = new AbilityComponent();
    AbilityUtil.abilityComponentFilled(abilities, abilityComponent);

    Stats stats = new Stats();
    stats.setBaseRateSpeed(info.getBaseSpeedRate());

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, info.getRadius(), e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    // start of shit
    Texture texture = new Texture(Gdx.files.internal("circle.png"));
    Sprite sprite = new Sprite(texture);
    Box2DSprite box2DSprite = new Box2DSprite(sprite);

    CircleShape circle = new CircleShape();
    circle.setRadius(info.getRadius());

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 0;
    fixtureDef.shape = circle;
    Fixture spriteFixture = physics.getBody().createFixture(fixtureDef);
    spriteFixture.setUserData(box2DSprite);
    circle.dispose();
    // end of shit

    UnitMovement movement = new UnitMovement();
    movement.setDisabled(false);

    Player player = new Player();

    e.edit().add(abilityComponent)
            .add(unitType)
            .add(new BuffComponent())
            .add(displayPosition)
            .add(physics)
            .add(stats)
            .add(player)
            .add(movement)
            .add(new Detection())
            .add(collision);

    return e;
  }

  /**
   * Create Food with box2d Steering behavior
   *
   * @param world    artemis world
   * @param position spawn position
   * @return Food entity that was just created
   */
  public static Entity createSteeringFood(World world, Vector2 position) {
    Entity e = world.createEntity(UUID.randomUUID());
    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, 0.25f, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    e.edit().add(new Collision())
            .add(physics)
            .add(new Food())
            .add(new UnitMovement())
            .add(new Detection())
            .add(new BuffComponent());
    return e;
  }

  public static Entity createAbsorbableFood(World world, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());
    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
    Food food = new Food();
    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);

    e.edit().add(new Collision())
            .add(physics)
                    //      .add(food)
            .add(stats)
            .add(new UnitMovement())
            .add(new Detection())
            .add(new BuffComponent());
    return e;
  }

  /**
   * Create projectile unit, used for firing an ability from player unit
   *
   * @param world    artemis world
   * @param position spawn position
   * @return unit that was just created
   */
  public static Entity createProjectileUnit(World world, Vector2 position) {
    Entity e = world.createEntity(UUID.randomUUID());

    UnitInfo unitInfo = new UnitInfo();
    unitInfo.setRadius(10);

    Collision collision = new Collision();

    DisplayPosition displayPosition = new DisplayPosition();

    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);

    Physics physics = new Physics();
    e.edit().add(physics);
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, unitInfo.getRadius(), e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    e.edit().add(collision)
            .add(displayPosition)
            .add(stats)
            .add(new Detection())
            .add(physics);

    return e;
  }
}
