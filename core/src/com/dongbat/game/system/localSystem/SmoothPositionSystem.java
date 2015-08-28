/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.DisplayPosition;
import com.dongbat.game.component.SmoothPosition;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class SmoothPositionSystem extends EntityProcessingSystem {

  float tightness = 0.1f;

  public SmoothPositionSystem() {
    super(Aspect.all(SmoothPosition.class));
  }

  @Override
  protected void process(Entity e) {
    Vector2 position = PhysicsUtil.getPosition(world, e);
    DisplayPosition displayedComponent = EntityUtil.getComponent(world, e, DisplayPosition.class);
    Vector2 displayedPosition = displayedComponent.getPosition();
    Vector2 sub = position.cpy().sub(displayedPosition.cpy()).scl(tightness);
  }

}
