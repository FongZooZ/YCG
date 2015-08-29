package com.dongbat.game.registry;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.DisplayPosition;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;

import java.util.UUID;

/**
 * Created by FongZooZ on 7/11/2015.
 */
public class UnitRegistry {

  private static final ObjectMap<String, UnitInfo> unitRegistry = new ObjectMap<String, UnitInfo>();

  /**
   * Load unit data from assest
   */
  public static void load() {
    FileHandle internal = Gdx.files.internal("unit");
    for (FileHandle file : internal.list()) {
      if (file.isDirectory()) {
        continue;
      }
      String content = file.readString();
      UnitInfo unitInfo = getUnitInfo(content);
      if (unitInfo != null) {
        unitRegistry.put(file.nameWithoutExtension(), unitInfo);
      }
    }
  }

  /**
   * Get UnitInfo by name
   *
   * @param name Unit name
   * @return info of unit
   */
  public static UnitInfo get(String name) {
    return unitRegistry.get(name);
  }

  /**
   * Set data for unit
   *
   * @param world artemis world
   * @param unit unit want to set data
   * @param args data to set
   */
  public static void setUnitData(World world, Entity unit, Object... args) {
    Stats unitStats = EntityUtil.getComponent(world, unit, Stats.class);

    for (int i = 0; i < args.length; i += 2) {
      String field = (String) args[i];
      Object obj = args[i + 1];
      unitStats.getUserData().put(field, obj);
    }
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
    e.edit().add(new BuffComponent())
      .add(aiControl)
      .add(new Player())
      .add(displayPosition)
      .add(stats)
      .add(movement)
      .add(collision);
    return e;
  }

  /**
   * Private function to get UnitInfo from string
   *
   * @param info String to parse
   * @return UnitInfo
   */
  private static UnitInfo getUnitInfo(String info) {
    return (UnitInfo) ReflectionUtil.parseData(UnitInfo.class, info);
  }

}
