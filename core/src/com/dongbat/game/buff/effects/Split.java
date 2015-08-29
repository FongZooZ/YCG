/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.PlaneMathCalculator;
import com.dongbat.game.util.UnitUtil;
import com.dongbat.game.util.factory.EntityFactory;

/**
 *
 * @author Admin
 */
public class Split implements BuffEffect {

  private float split;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    Vector2 targetPosition = PhysicsUtil.getPosition(world, target);
    Vector2 sourcePosition = PhysicsUtil.getPosition(world, source);
    UnitMovement component = EntityUtil.getComponent(world, target, UnitMovement.class);
    float targetRadius = PhysicsUtil.getRadius(world, target);
    float percentTaken = PlaneMathCalculator.getPercentTaken(targetPosition, sourcePosition, component.getDirectionVelocity(), targetRadius);
    if (percentTaken > 0.5) {
      percentTaken = 1 - percentTaken;
    } else if (percentTaken == 0.5) {
      // Bingo
      UnitUtil.destroy(target);
      return;
    }

    float squareTargetRadius = (float) (targetRadius * targetRadius * Math.PI);
    float newRadius = (float) Math.sqrt(squareTargetRadius * (1 - percentTaken) / Math.PI);
    float oldRadius = PhysicsUtil.getRadius(world, target);
    float newFoodSquare = (float) (oldRadius * oldRadius * Math.PI - newRadius * newRadius * Math.PI);
    float newFoodRadius = (float) Math.sqrt(newFoodSquare / Math.PI);

    PhysicsUtil.setRadius(world, target, newRadius);
    EntityFactory.createAbsorbableFood(world, sourcePosition, newFoodRadius);
  }

}
