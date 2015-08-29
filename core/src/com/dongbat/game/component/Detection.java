/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Detection extends Component {

  private Array<UUID> detectionList;
  private Array<UUID> justDetectionList;
  private Array<UUID> lastDetectionList;

  public Detection(Array<UUID> detectionList, Array<UUID> justDetectionList, Array<UUID> lastDetectionList) {
    this.detectionList = detectionList;
    this.justDetectionList = justDetectionList;
    this.lastDetectionList = lastDetectionList;
  }

  public Detection() {
  }

  public Array<UUID> getDetectionList() {
    return detectionList;
  }

  public void setDetectionList(Array<UUID> detectionList) {
    this.detectionList = detectionList;
  }

  public Array<UUID> getJustDetectionList() {
    return justDetectionList;
  }

  public void setJustDetectionList(Array<UUID> justDetectionList) {
    this.justDetectionList = justDetectionList;
  }

  public Array<UUID> getLastDetectionList() {
    return lastDetectionList;
  }

  public void setLastDetectionList(Array<UUID> lastDetectionList) {
    this.lastDetectionList = lastDetectionList;
  }

}
