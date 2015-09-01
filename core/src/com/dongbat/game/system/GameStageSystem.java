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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.component.UnitMovement;
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
  private Label title = new Label("Full game play is coming soon, with multiplayer feature over WIFI and internet", skin);
  private Touchpad touchpad;

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

    TextButton flee = new TextButton("Flee", skin);
    flee.setSize(width * 7, height * 6);
    flee.setPosition(worldWidth - width * 2 - flee.getWidth(), height * 5);
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

    TextButton hotBlow = new TextButton("Blow", skin);
    hotBlow.setSize(width * 7, height * 6);
    hotBlow.setPosition(worldWidth - width * 2 - flee.getWidth(), height * 13);
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

    TextButton spitAndJoin = new TextButton("Split", skin);
    spitAndJoin.setSize(width * 7, height * 6);
    spitAndJoin.setPosition(worldWidth - width * 2 - flee.getWidth(), height * 21);
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

    TextButton vacuum = new TextButton("Vacuum", skin);
    vacuum.setSize(width * 7, height * 6);
    vacuum.setPosition(worldWidth - width * 2 - flee.getWidth(), height * 29);
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

    title.setPosition(worldWidth - width * 30, worldHeight - height * 2);

    touchpad = new Touchpad(worldWidth / 40, skin);
    touchpad.setBounds(worldWidth / 50, worldWidth / 50, worldWidth / 8, worldWidth / 8);

    stage.addActor(touchpad);

    stage.addActor(flee);
    stage.addActor(hotBlow);
    stage.addActor(spitAndJoin);
    stage.addActor(vacuum);
    stage.addActor(title);
    InputUtil.addProcessor(stage, 0);
  }

  @Override
  protected void processSystem() {
    stage.act();
    if(touchpad.isTouched()) {
      float x = touchpad.getKnobPercentX();
      float y = touchpad.getKnobPercentY();

      UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
      Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
      if (e == null || localPlayerId == null) {
        return;
      }
      UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
      move.setDirectionVelocity(new Vector2(x, y));
    }
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
//    table.add(cut);
    stage.draw();
  }

}
