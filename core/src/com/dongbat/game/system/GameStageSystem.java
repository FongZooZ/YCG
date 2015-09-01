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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.stage.AbilityButton;
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
  private AbilityButton buttonFleeAbility;
  private AbilityButton buttonBlowAbility;
  private AbilityButton buttonSplitAbility;
  private AbilityButton buttonVacuumAbility;

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

    buttonFleeAbility = new AbilityButton("Flee", skin, "texture/cooldown_button/cooldown_button.png");
    buttonFleeAbility.setSize(width * 7, width * 7);
    buttonFleeAbility.setPosition(worldWidth - width * 7, height * 2);
    buttonBlowAbility = new AbilityButton("HotBlow", skin, "texture/cooldown_button/cooldown_button.png");
    buttonBlowAbility.setSize(width * 7, width * 7);
    buttonBlowAbility.setPosition(worldWidth - width * 7, height * 2 + width * 7);
    buttonSplitAbility = new AbilityButton("SplitAndJoin", skin, "texture/cooldown_button/cooldown_button.png");
    buttonSplitAbility.setSize(width * 7, width * 7);
    buttonSplitAbility.setPosition(worldWidth - width * 7, height * 2 + width * 14);
    buttonVacuumAbility = new AbilityButton("Vacuum", skin, "texture/cooldown_button/cooldown_button.png");
    buttonVacuumAbility.setSize(width * 7, width * 7);
    buttonVacuumAbility.setPosition(worldWidth - width * 7, height * 2 + width * 21);

    title.setPosition(worldWidth - width * 30, worldHeight - height * 2);

    touchpad = new Touchpad(worldWidth / 40, skin);
    touchpad.setBounds(worldWidth / 50, worldWidth / 50, worldWidth / 6, worldWidth / 6);

    stage.addActor(touchpad);
    stage.addActor(buttonFleeAbility);
    stage.addActor(buttonBlowAbility);
    stage.addActor(buttonSplitAbility);
    stage.addActor(buttonVacuumAbility);
    stage.addActor(title);
    InputUtil.addProcessor(stage, 0);
  }

  @Override
  protected void processSystem() {
    stage.act();

    if (touchpad.isTouched()) {
      float x = touchpad.getKnobPercentX();
      float y = touchpad.getKnobPercentY();

      UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
      Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
      if (e == null || localPlayerId == null) {
        return;
      }
      UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
      move.setDirectionVelocity(new Vector2(x, y));
    }
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    stage.draw();
  }

}
