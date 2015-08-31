/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.system.localSystem.LocalInputSystem;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.InputUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class GameStageSystem extends BaseSystem {

  private Stage stage = new Stage();
  private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"), new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));

  public GameStageSystem() {
  }

  @Override
  protected void dispose() {
    stage.dispose();
    skin.dispose();
    InputUtil.removeProcessor(stage);
  }

  @Override
  protected void initialize() {
    Viewport viewport = stage.getViewport();
    float worldHeight = viewport.getWorldHeight();
    float worldWidth = viewport.getWorldWidth();
    float width = worldWidth / 60;
    float height = worldHeight / 60;

    TextButton flee = new TextButton(" flee ", skin);
    flee.sizeBy(width * 4, height * 3);
    flee.setPosition(width * 2, height * 2);
    flee.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
        Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
        if (e == null || localPlayerId == null) {
          return;
        }
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
        Vector2 destination = move.getDirectionVelocity();
        AbilityUtil.use(world, e, "Flee", destination);
      }

    });

    TextButton hotBlow = new TextButton(" blow ", skin);
    hotBlow.sizeBy(width * 4, height * 3);
    hotBlow.setPosition(width * 2, height * 8);
    hotBlow.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
        Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
        if (e == null || localPlayerId == null) {
          return;
        }
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
        Vector2 destination = move.getDirectionVelocity();
        AbilityUtil.use(world, e, "HotBlow", destination);
      }
    });

    TextButton spitAndJoin = new TextButton(" split  ", skin);
    spitAndJoin.sizeBy(width * 4, height * 3);
    spitAndJoin.setPosition(width * 2, height * 14);
    spitAndJoin.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
        Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
        if (e == null || localPlayerId == null) {
          return;
        }
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
        Vector2 destination = move.getDirectionVelocity();
        AbilityUtil.use(world, e, "SplitAndJoin", destination);
      }
    });

    TextButton vacuum = new TextButton(" VAC  ", skin);
    vacuum.sizeBy(width * 4, height * 3);
    vacuum.setPosition(width * 2, height * 20);
    vacuum.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
        Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
        if (e == null || localPlayerId == null) {
          return;
        }
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
        Vector2 destination = move.getDirectionVelocity();
        AbilityUtil.use(world, e, "Vacuum", destination);
      }
    });

    stage.addActor(flee);
    stage.addActor(hotBlow);
    stage.addActor(spitAndJoin);
    stage.addActor(vacuum);
    InputUtil.addProcessor(stage, 0);
  }

  @Override
  protected void processSystem() {
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
//    table.add(cut);
    stage.act();
    stage.draw();
  }

}
