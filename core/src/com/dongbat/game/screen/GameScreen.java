/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.dongbat.game.registry.AbilityRegistry;
import com.dongbat.game.registry.BuffRegistry;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;
import com.dongbat.game.util.factory.UnitFactory;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;

import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;

/**
 *
 * @author Admin
 */
public class GameScreen implements Screen {

  private final World world;

  public GameScreen() {
    WorldConfiguration config = ECSUtil.initWorldConfig();
    world = new World(config);
    UnitRegistry.load();
    BuffRegistry.load();
    AbilityRegistry.load();
    ECSUtil.init(world);
    PhysicsUtil.init(world);
//    PhysicsUtil.createEdge(world, BodyDef.BodyType.StaticBody, -0, 200, -0, -200, 0);
    PhysicsUtil.createEdge(world, BodyDef.BodyType.StaticBody, scaleX, -scaleY, scaleX, scaleY, 0);
//    PhysicsUtil.createEdge(world, BodyDef.BodyType.StaticBody, -scaleX, -scaleY, scaleX, -scaleY, 0);
//    PhysicsUtil.createEdge(world, BodyDef.BodyType.StaticBody, -scaleX, scaleY, scaleX, scaleY, 0);
    Entity localPlayer = EntityFactory.createPlayer(world, new Vector2(0, 100), "phong");

//    BuffUtil.addBuff(world, localPlayer, localPlayer, "Split", 10000, 1);
    UnitFactory.createQueen(world, new Vector2(0, 80), 3);
    LocalPlayerUtil.setLocalPlayer(UuidUtil.getUuid(localPlayer));
    LocalPlayerUtil.setLocalWorld(world);
    UnitRegistry.createUnit(world, "normal", new Vector2(100, -100));
		UnitRegistry.createUnit(world, "normal", new Vector2(-100, -100));
		UnitRegistry.createUnit(world, "normal", new Vector2(-100, 100));
		UnitRegistry.createUnit(world, "normal", new Vector2(500, 100));
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float f) {
    ECSUtil.process(world, f);
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
  }

}
