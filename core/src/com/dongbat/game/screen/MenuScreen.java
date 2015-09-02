/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dongbat.game.util.InputUtil;
import com.dongbat.game.util.ScreenUtil;

/**
 *
 * @author Admin
 */
public class MenuScreen extends ScreenAdapter {

  private Stage stage = new Stage();
  private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"), new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));

  private TextButton buttonSinglePlayer = new TextButton("Single Player", skin);
  private TextButton buttonMultiPlayer = new TextButton("Multiplayer", skin);
  private TextButton buttonAi = new TextButton("Play With Bots", skin);
//  private Label title = new Label("Game Title", skin);

  @Override
  public void show() {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    stage.getViewport().update(width, height);
    stage.setViewport(new ExtendViewport(800, 480));

    buttonSinglePlayer.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        ScreenUtil.setScreen(new GameScreen());
      }

    });

    buttonAi.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        ScreenUtil.setScreen(new BotGameScreen());

      }

    });

    buttonMultiPlayer.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
      }

    });
//    stage.setDebugAll(true);

    buttonSinglePlayer.setPosition(-75, 80);
    buttonSinglePlayer.setSize(150, 60);
    buttonAi.setPosition(-75, 0);
    buttonAi.setSize(150, 60);
    buttonMultiPlayer.setPosition(-75, -80);
    buttonMultiPlayer.setSize(150, 60);

    stage.addActor(buttonSinglePlayer);
    stage.addActor(buttonMultiPlayer);
    stage.addActor(buttonAi);

    InputUtil.addProcessor(stage, 0);
  }

  @Override
  public void render(float f) {
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
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
    stage.dispose();
    skin.dispose();
    InputUtil.removeProcessor(stage);
  }

}
