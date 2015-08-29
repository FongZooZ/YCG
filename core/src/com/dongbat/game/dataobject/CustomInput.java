/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.dataobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.util.localUtil.Constants.inputType;

/**
 *
 * @author Admin
 */
public class CustomInput {

  private inputType type;
  private Vector2 position;
  private int abilityNum;
//  private ObjectMap<Integer, String> abilityMap;

  public CustomInput(inputType type, Vector2 position, int abilityNum) {
    this.type = type;
    this.position = position;
    this.abilityNum = abilityNum;
//    this.abilityMap = abilityMap;
  }

  public inputType getType() {
    return type;
  }

  public void setType(inputType type) {
    this.type = type;
  }

  public int getAbilityNum() {
    return abilityNum;
  }

  public void setAbilityNum(int abilityNum) {
    this.abilityNum = abilityNum;
  }

//  public ObjectMap<Integer, String> getAbilityMap() {
//    return abilityMap;
//  }
//
//  public void setAbilityMap(ObjectMap<Integer, String> abilityMap) {
//    this.abilityMap = abilityMap;
//  }

  public CustomInput(Vector2 position) {
    this.position = position;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

}
