/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Admin
 */
public class DisplayPosition extends Component {

  private Vector2 position;

  public DisplayPosition() {
  }

  public DisplayPosition(Vector2 position) {
    this.position = position.cpy();
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position.set(position);
  }

  public void setX(float x) {
    position.x = x;
  }

  public void setY(float y) {
    position.y = y;
  }

  public float getX() {
    return position.x;
  }

  public float getY() {
    return position.y;
  }
}
