/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author implicit-invocation
 */
public class AssetUtil {

  private static AssetManager manager;
  private static TextureLoader.TextureParameter parameter;

  private static ObjectMap<String, TextureAtlas> unitAtlas;
  public static Texture db;
  public static Texture logo;

  public static AssetManager getManager() {
    if (manager == null) {
      init();
    }
    return manager;
  }

  private static void init() {
    manager = new AssetManager(new ResolutionFileResolver(new InternalFileHandleResolver(), new ResolutionFileResolver.Resolution(800, 480, "hdpi"),
      new ResolutionFileResolver.Resolution(1280, 720, "xhdpi"),
      new ResolutionFileResolver.Resolution(1600, 960, "xxhdpi")));

    parameter = new TextureLoader.TextureParameter();
    parameter.minFilter = Texture.TextureFilter.Linear;
    parameter.magFilter = Texture.TextureFilter.Linear;

    unitAtlas = new ObjectMap<String, TextureAtlas>();
  }

  public static void loadAsset() {
    AssetManager manager = getManager();
    manager.load("texture/unit/move/move.atlas", TextureAtlas.class);
    manager.load("db.png", Texture.class, parameter);
    manager.load("Bluebird logo.png", Texture.class, parameter);

  }

  public static boolean update() {
    AssetManager manager = getManager();
    boolean done = manager.update();

    if (done) {
      unitAtlas.put("move", manager.get("texture/unit/move/move.atlas", TextureAtlas.class));
      db = manager.get("db.png", Texture.class);
      logo = manager.get("Bluebird logo.png", Texture.class);
    }
    return done;
  }

}
