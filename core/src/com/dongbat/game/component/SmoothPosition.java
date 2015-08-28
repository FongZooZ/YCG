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
public class SmoothPosition extends Component {

  private Vector2 displayedPosition;

  public SmoothPosition(Vector2 displayedPosition) {
    this.displayedPosition = displayedPosition;
  }

  public SmoothPosition() {
  }

  public Vector2 getDisplayedPosition() {
    return displayedPosition;
  }

  public void setDisplayedPosition(Vector2 displayedPosition) {
    this.displayedPosition = displayedPosition;
  }

}
