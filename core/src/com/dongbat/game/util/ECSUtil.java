/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.system.AiControlledSystem;
import com.dongbat.game.system.Box2dSystem;
import com.dongbat.game.system.BuffSystem;
import com.dongbat.game.system.CollisionCleanupSystem;
import com.dongbat.game.system.CollisionSystem;
import com.dongbat.game.system.ConsumingSystem;
import com.dongbat.game.system.InputProcessorSystem;
import com.dongbat.game.system.localSystem.Box2dDebugRendererSystem;
import com.dongbat.game.system.localSystem.CameraUpdateSystem;
import com.dongbat.game.system.localSystem.GridRendererSystem;
import com.dongbat.game.system.localSystem.HUDRenderSystem;
import com.dongbat.game.system.localSystem.LocalInputSystem;
import com.dongbat.game.system.MovementSystem;
import com.dongbat.game.system.BorderlandSystem;
import com.dongbat.game.system.DetectionCleanupSystem;
import com.dongbat.game.system.DetectionSystem;
import com.dongbat.game.system.localSystem.Shaperenderer1;
import com.dongbat.game.system.localSystem.SpriteRenderSystem;
import com.dongbat.game.util.objectUtil.PredictableRandom;
import com.dongbat.game.util.objectUtil.WorldProgress;

/**
 * @author Admin
 */
public class ECSUtil {

  private static final ObjectMap<World, WorldProgress> worldProgressMap = new ObjectMap<World, WorldProgress>();
  private static final ObjectMap<World, PredictableRandom> worldRandomMap = new ObjectMap<World, PredictableRandom>();

//  public static World createWorld() {
//    return createWorld(false);
//  }
//  public static World createWorld(boolean isServer) {
//    WorldConfiguration config = new WorldConfiguration();
//    setSystem(config, new SpawnningFoodSystem(), false);
//    setSystem(config, new Box2dSystem(), false);
//    setSystem(config, new BuffSystem(), false);
//    setSystem(config, new Box2dDebugRendererSystem(), true);
//    setSystem(config, new HUDRenderSystem(), true);
//    setSystem(config, new MovementSystem(), false);
//    setSystem(config, new CollisionCleanupSystem(), false);
//    setSystem(config, new CollisionSystem(), false);
////		setSystem(config, new FoodMovementSystem(), false);
//    setSystem(config, new ConsumingSystem(), false);
//    setSystem(config, new GridRendererSystem(), true);
//    setSystem(config, new BorderlandSystem(), false);
//    setSystem(config, new CameraUpdateSystem(), true);
//    setSystem(config, new AiControlledSystem(), false);
//    setSystem(config, new LocalInputSystem(), false);
//    setSystem(config, new InputProcessorSystem(), false);
//
//    config.setManager(new UuidEntityManager());
//    World world = new World(config);
//    WorldProgress worldProgress = new WorldProgress(0.01f);
//    worldProgressMap.put(world, worldProgress);
//    worldRandomMap.put(world, new PredictableRandom());
//    return world;
//  }
  public static WorldConfiguration initWorldConfig() {
    WorldConfiguration config = new WorldConfiguration();

    setSystem(config, new CollisionCleanupSystem(), false);
    setSystem(config, new Box2dSystem(1), false);
    setSystem(config, new AiControlledSystem(), false);
    setSystem(config, new BuffSystem(), false); // gay lag, mat 300 entities
    setSystem(config, new CollisionSystem(1), false); // 1200 collided trong list
    setSystem(config, new DetectionCleanupSystem(50), false);
    setSystem(config, new DetectionSystem(50), false);
    setSystem(config, new ConsumingSystem(), false);
    setSystem(config, new InputProcessorSystem(), false);

    // for rendering
    setSystem(config, new CameraUpdateSystem(), true);
    setSystem(config, new SpriteRenderSystem(), true); // gay lag: mat 1200 entites
    setSystem(config, new HUDRenderSystem(), true); // gay lag
    setSystem(config, new Shaperenderer1(), true);
    setSystem(config, new LocalInputSystem(), true); // gay lag, mat 200
    setSystem(config, new MovementSystem(), true); // gay lag, mat 400
    setSystem(config, new GridRendererSystem(), true); // gay lag, mat hon 300
    setSystem(config, new BorderlandSystem(), true);
//    setSystem(config, new Box2dDebugRendererSystem(), true);

    config.setManager(new UuidEntityManager());
    return config;
  }

  /**
   * Set systems to the artemis world then initialize
   *
   * @param world
   */
  public static void init(World world) {
    WorldProgress worldProgress = new WorldProgress(0.015f);
    worldProgressMap.put(world, worldProgress);
    worldRandomMap.put(world, new PredictableRandom());
  }

  /**
   * Process a game loop
   *
   * @param world artemis world
   * @param delta delta time
   */
  public static void process(World world, float delta) {
    WorldProgress worldProgress = getWorldProgress(world);
    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
      worldProgress.setRewinding(true);
    }

    worldProgressMap.get(world).stepWorld(world, delta);
    processPassive(world, delta);

  }

  public static void normalProcess(World world, float delta) {
    world.setDelta(delta);
    world.process();
    getWorldProgress(world).advanced();
  }

  /**
   * Process passive system
   *
   * @param world artemis world
   * @param delta delta time
   */
  public static void processPassive(World world, float delta) {
    world.setDelta(delta);
    ImmutableBag<BaseSystem> systems = world.getSystems();
    for (BaseSystem system : systems) {
      if (system.isPassive()) {
        system.process();
      }
    }
  }

  /**
   * Set a system to artemis world
   *
   * @param world artemis world
   * @param system system you want to add to world
   * @param isPassive is passive system or not
   */
  private static void setSystem(WorldConfiguration config, BaseSystem system, boolean isPassive) {
    config.setSystem(system, isPassive);
  }

  public static PredictableRandom getRandom(World world) {
    return worldRandomMap.get(world);
  }

  public static WorldProgress getWorldProgress(World world) {
    return worldProgressMap.get(world);
  }

  public static long getFrame(World world) {
    return worldProgressMap.get(world).getFrame();
  }

  public static float getStep(World world) {
    return worldProgressMap.get(world).getStep();
  }

  public static void setFrame(World world, long frame) {
    worldProgressMap.get(world).setFrame(frame);
  }

}
