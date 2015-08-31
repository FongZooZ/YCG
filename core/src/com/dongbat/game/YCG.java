package com.dongbat.game;

import com.artemis.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.registry.AbilityRegistry;
import com.dongbat.game.registry.BuffRegistry;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.screen.GameScreen;
import com.dongbat.game.screen.TestScreen;
import static com.dongbat.game.util.PhysicsUtil.getPosition;
import com.dongbat.game.util.ScreenUtil;

public class YCG extends Game {

  SpriteBatch batch;
  Texture img;

  @Override
  public void create() {
    ScreenUtil.setGame(this);
    UnitRegistry.load();
    BuffRegistry.load();
    AbilityRegistry.load();
    setScreen(new GameScreen());

  }

  @Override
  public void render() {

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.render();
  }
}
