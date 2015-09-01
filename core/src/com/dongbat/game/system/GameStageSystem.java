/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.stage.AbilityButton;
import com.dongbat.game.stage.RadialSprite;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.ECSUtil;
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
  private AbilityButton buttonFleeAbility = new AbilityButton("Flee", skin, "circle.png");
  private AbilityButton buttonBlowAbility = new AbilityButton("HotBlow", skin, "circle.png");
  private AbilityButton buttonSplitAbility = new AbilityButton("SplitAndJoin", skin, "circle.png");
  private AbilityButton buttonVacuumAbility = new AbilityButton("Vacuum", skin, "circle.png");

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

    buttonFleeAbility.createNormalButton(width * 7, width * 7, worldWidth - width * 7, height * 2);
    buttonFleeAbility.createRadialSprite(width * 7, width * 7, 1000, height * 2);
    buttonBlowAbility.createNormalButton(width * 7, width * 7, worldWidth - width * 7, height * 2 + width * 7);
    buttonBlowAbility.createRadialSprite(width * 7, width * 7, 1000, height * 2 + width * 7);
    buttonSplitAbility.createNormalButton(width * 7, width * 7, worldWidth - width * 7, height * 2 + width * 14);
    buttonSplitAbility.createRadialSprite(width * 7, width * 7, 1000, height * 2 + width * 14);
    buttonVacuumAbility.createNormalButton(width * 7, width * 7, worldWidth - width * 7, height * 2 + width * 21);
    buttonVacuumAbility.createRadialSprite(width * 7, width * 7, 1000, height * 2 + width * 21);
    //    ImageButton flee = createNormalButton(width * 7, width * 7, worldWidth - width * 7, height * 2, "Flee", stage, skin);
    //
//        ImageButton hotBlow = createNormalButton(width * 7, width * 7, worldWidth - flee.getWidth(), height * 2 + width * 7, "HotBlow", stage, skin);
    //    ImageButton spitAndJoin = createNormalButton(width * 7, width * 7, worldWidth - flee.getWidth(), height * 2 + width * 14, "SplitAndJoin", stage, skin);
    //    ImageButton vacuum = createNormalButton(width * 7, width * 7, worldWidth - flee.getWidth(), height * 2 + width * 21, "Vacuum", stage, skin);
    //    TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("circle.png")));
    //    radialSprite = new RadialSprite(textureRegion);
    //
    //    image = new Image(radialSprite);
    //    image.setSize(width * 7, width * 7);
    //    image.setPosition(1000, height * 2);
    //    stage.addActor(image);
    //    image.setVisible(true);
    //    float angle = AbilityUtil.getLastCast(world, null, null);
    //    radialSprite.setAngle(angle);
    title.setPosition(worldWidth - width * 30, worldHeight - height * 2);

    touchpad = new Touchpad(worldWidth / 40, skin);
    touchpad.setBounds(worldWidth / 50, worldWidth / 50, worldWidth / 6, worldWidth / 6);

    stage.addActor(touchpad);
    stage.addActor(buttonFleeAbility.imageButton);
    stage.addActor(buttonFleeAbility.radialImage);
    stage.addActor(buttonBlowAbility.imageButton);
    stage.addActor(buttonBlowAbility.radialImage);
    stage.addActor(buttonSplitAbility.imageButton);
    stage.addActor(buttonSplitAbility.radialImage);
    stage.addActor(buttonVacuumAbility.imageButton);
    stage.addActor(buttonVacuumAbility.radialImage);
    stage.addActor(title);
    InputUtil.addProcessor(stage, 0);
  }

  @Override
  protected void processSystem() {
    stage.act();
    buttonFleeAbility.radialSprite.setAngle(ECSUtil.getFrame(world) % 360 + 20);
    buttonBlowAbility.radialSprite.setAngle(ECSUtil.getFrame(world) % 360 + 120);
    buttonSplitAbility.radialSprite.setAngle(ECSUtil.getFrame(world) % 360 + 240);
    buttonVacuumAbility.radialSprite.setAngle(ECSUtil.getFrame(world) % 360 + 300);

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
//    table.add(cut);
    stage.draw();
  }

}
