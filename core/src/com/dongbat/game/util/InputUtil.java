/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

/**
 *
 * @author tao
 */
public class InputUtil {

  private static InputMultiplexer multiplexer;

  public static void init() {
    multiplexer = new InputMultiplexer();
    Gdx.input.setInputProcessor(multiplexer);
  }

  public static void addProcessor(InputProcessor processor, int index) {
    multiplexer.addProcessor(index, processor);
  }

  public static void removeProcessor(InputProcessor processor) {
    multiplexer.removeProcessor(processor);
  }
}
