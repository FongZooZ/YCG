/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.stage;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class AbilityButton extends Actor {

  public String ability;
  public RadialSprite radialSprite;
  public Skin skin;
  public ImageButton imageButton;
  public Image radialImage;

  public AbilityButton(String ability, Skin skin, String texture) {
    this.ability = ability;
    TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal(texture)));
    radialSprite = new RadialSprite(textureRegion);
    this.skin = skin;
  }

  public long getCooldown() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
    World world = LocalPlayerUtil.getLocalWorld();
    Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
    if (e == null || localPlayerId == null) {
      return 0;
    }
    return AbilityUtil.getCooldown(world, e, ability);
  }

  public long getLastCast() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
    World world = LocalPlayerUtil.getLocalWorld();
    Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
    if (e == null || localPlayerId == null) {
      return 0;
    }
    return AbilityUtil.getLastCast(world, e, ability);
  }

  public float getAngle() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
    World world = LocalPlayerUtil.getLocalWorld();
    Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
    if (e == null || localPlayerId == null) {
      return 0;
    }
    long cooldown = AbilityUtil.getCooldown(world, e, ability);
    long lastCast = AbilityUtil.getLastCast(world, e, ability);

    if (lastCast == -1 || ECSUtil.getFrame(world) - lastCast > cooldown) {
      return 0;
    } else {
      if(cooldown == 0){
        return 360;
      }
      return (ECSUtil.getFrame(world) - lastCast) / cooldown * 360;
    }

  }

  public ImageButton createNormalButton(float widthSize, float heightSize, float posX, float posY) {
    imageButton = new ImageButton(skin);
    imageButton.setSize(widthSize, heightSize);
    imageButton.setPosition(posX, posY);
    imageButton.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
        World world = LocalPlayerUtil.getLocalWorld();
        Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
        if (e == null || localPlayerId == null) {
          return;
        }
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
        Vector2 destination = move.getDirectionVelocity();
        AbilityUtil.use(world, e, ability, destination);
      }

    });
    return imageButton;
  }

  public Image createRadialSprite(float width, float height, float posX, float posY) {
    radialImage = new Image(radialSprite);
    radialImage.setSize(width, height);
    radialImage.setPosition(posX, posY);
    return radialImage;
  }
}
