/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author implicit-invocation
 */
public class AssetUtil {

  private static AssetManager manager = new AssetManager();

  public static Animation getAnimation(String atlas, String name, float frameDuration) {
    // TODO: use asset manager
    TextureAtlas textureAtlas = new TextureAtlas(atlas);
    Animation animation = new Animation(frameDuration, textureAtlas.findRegions(name));
    return animation;
  }
}
