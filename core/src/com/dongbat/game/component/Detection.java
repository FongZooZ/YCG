/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Detection extends Component {

  private Entity queen;
  private Entity player;

  public Detection() {
  }

  public Detection(Entity queen, Entity player) {
    this.queen = queen;
    this.player = player;
  }

  public Entity getQueen() {
    return queen;
  }

  public void setQueen(Entity queen) {
    this.queen = queen;
  }

  public Entity getPlayer() {
    return player;
  }

  public void setPlayer(Entity player) {
    this.player = player;
  }

}
