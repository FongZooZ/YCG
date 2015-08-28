/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 *
 * @author Admin
 */
public class TestScreen extends ScreenAdapter {

  Stage stage;
  TextButton button;
  TextButtonStyle textButtonStyle;
  BitmapFont font;
  Skin skin;
  TextureAtlas buttonAtlas;

  @Override
  public void show() {
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    font = new BitmapFont();
    skin = new Skin();
    buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
    skin.addRegions(buttonAtlas);
    textButtonStyle = new TextButtonStyle();
    textButtonStyle.font = font;
    textButtonStyle.up = skin.getDrawable("up-button");
    textButtonStyle.down = skin.getDrawable("down-button");
    textButtonStyle.checked = skin.getDrawable("checked-button");
    button = new TextButton("Button1", textButtonStyle);
    stage.addActor(button);

  }

  @Override
  public void render(float delta) {
    super.render(delta); //To change body of generated methods, choose Tools | Templates.
    stage.draw();
  }

  public TestScreen() {
  }

}
