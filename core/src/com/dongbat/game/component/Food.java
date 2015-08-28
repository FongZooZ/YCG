/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;

/**
 *
 * @author Admin
 */
public class Food extends Component {

  private boolean isToxic;

  public Food() {
    isToxic = false;
  }

  public Food(boolean isToxic) {
    this.isToxic = isToxic;
  }

  public boolean isIsToxic() {
    return isToxic;
  }

  public void setIsToxic(boolean isToxic) {
    this.isToxic = isToxic;
  }

}
