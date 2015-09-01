/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.registry.AbilityRegistry;
import com.dongbat.game.registry.BuffRegistry;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.system.AiControlledSystem;
import com.dongbat.game.system.AnimationRenderSystem;
import com.dongbat.game.system.Box2dSystem;
import com.dongbat.game.system.BuffSystem;
import com.dongbat.game.system.ConsumingSystem;
import com.dongbat.game.system.DetectionCleanupSystem;
import com.dongbat.game.system.DetectionSystem;
import com.dongbat.game.system.DisplayUpdateSystem;
import com.dongbat.game.system.FoodAnimationSystem;
import com.dongbat.game.system.InputProcessorSystem;
import com.dongbat.game.system.MovementSystem;
import com.dongbat.game.system.localSystem.GridRendererSystem;
import com.dongbat.game.system.localSystem.ParallaxBackgroundSystem;
import com.dongbat.game.system.localSystem.SpriteRenderSystem;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import static com.dongbat.game.util.ECSUtil.worldProgressMap;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.UnitFactory;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;
import com.dongbat.game.util.objectUtil.WorldProgress;

/**
 *
 * @author Admin
 */
public class DynamicMenuScreen implements Screen {

  private final World world;
  WorldProgress worldProgress;

  public DynamicMenuScreen() {
    WorldConfiguration config = new WorldConfiguration();
    config.setSystem(new Box2dSystem(2), false);
    config.setSystem(new AiControlledSystem(5), false);
    config.setSystem(new BuffSystem(), false);
    config.setSystem(new DetectionCleanupSystem(1), false);
    config.setSystem(new DetectionSystem(1), false);
    config.setSystem(new ConsumingSystem(), false);
    config.setSystem(new InputProcessorSystem(), false);
    config.setSystem(new MovementSystem(), false);
    config.setSystem(new SpriteRenderSystem(), true);
    config.setSystem(new GridRendererSystem(), true);
    config.setSystem(new ParallaxBackgroundSystem(), true);
    config.setSystem(new DisplayUpdateSystem(), true);
    config.setSystem(new FoodAnimationSystem(), true);
    config.setSystem(new AnimationRenderSystem(), true);
//    config.setSystem(new Shaperenderer1(), true);

    config.setManager(new UuidEntityManager());

    world = new World(config);
    UnitRegistry.load();
    BuffRegistry.load();
    AbilityRegistry.load();
    ECSUtil.init(world);
    worldProgress = new WorldProgress(0.015f);
    worldProgressMap.put(world, worldProgress);

    world.getSystem(DetectionSystem.class).setModifier(3);

    PhysicsUtil.init(world);

    PhysicsCameraUtil.getCamera().zoom = 5;
    PhysicsCameraUtil.getCamera().position.set(new Vector2(80, -70), 0);
    PhysicsCameraUtil.getCamera().update();

    Entity queen = UnitFactory.createQueen(world, new Vector2(100, -80), 8);
    BuffComponent queenBuff = EntityUtil.getComponent(world, queen, BuffComponent.class);

    queenBuff.getBuffs().remove("QueenTeleportSchedule");
    queenBuff.getBuffs().remove("SelfDefense");
    queenBuff.getBuffs().remove("FeedSmaller");
//    queenBuff.getBuffs().remove("ProduceFoodSchedule");
    BuffUtil.addBuff(world, queen, queen, "SelfDefense", -1, 1, "framePerFood", 15);
    UnitMovement queenMove = EntityUtil.getComponent(world, queen, UnitMovement.class);
    queenMove.setDirectionVelocity(new Vector2());
//    PhysicsUtil.setPosition(world, queen, new Vector2(500, -500));

    Entity createUnit = UnitFactory.createUnit(world, "bao", new Vector2(0, 30));
    PhysicsUtil.setRadius(world, createUnit, 1);

    UnitFactory.createUnit(world, "bao", new Vector2(10, 30));
    UnitFactory.createUnit(world, "bao", new Vector2(100, 100));
    UnitFactory.createUnit(world, "bao", new Vector2(50, -40));
//    UnitFactory.createUnit(world, "bao", new Vector2(30, 30));
//    UnitFactory.createUnit(world, "bao", new Vector2(0, -30));
//    UnitFactory.createUnit(world, "bao", new Vector2(-10, -30));
//    UnitFactory.createUnit(world, "bao", new Vector2(-20, -30));
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float f) {
    worldProgress.stepWorld(world, f);
    world.setDelta(f);
    ImmutableBag<BaseSystem> systems = world.getSystems();
    for (BaseSystem system : systems) {
      if (system.isPassive()) {
        system.process();
      }
    }
    
  }

  @Override
  public void resize(int i, int i1) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    world.dispose();
  }

}
