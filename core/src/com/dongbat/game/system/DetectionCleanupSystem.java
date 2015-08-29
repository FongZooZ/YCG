/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.dongbat.game.component.Detection;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author Admin
 */
public class DetectionCleanupSystem extends EntityProcessingSystem {

  public DetectionCleanupSystem() {
    super(Aspect.all(Detection.class));
  }

  @Override
  protected void process(Entity e) {

    Detection detection = EntityUtil.getComponent(world, e, Detection.class);
    detection.setNearestFood(null);
    detection.setNearestPlayer(null);
    detection.setNearestQueen(null);
  }

}
