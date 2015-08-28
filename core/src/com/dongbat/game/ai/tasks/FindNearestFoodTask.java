/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class FindNearestFoodTask extends LeafTask<Entity> {

  @Override
  public void run() {
    Entity e = getObject();
    UnitMovement unitMovement = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    Array<Entity> foodInRadius = PhysicsUtil.findFoodInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 50);
    Array<Entity> foodInMap = PhysicsUtil.findFoodInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 5000);
    Entity nearestFood = PhysicsUtil.findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), foodInRadius);
    Vector2 currentPos = PhysicsUtil.getPosition(e.getWorld(), e);
    if (nearestFood == null) {
      Entity mapsFood = PhysicsUtil.findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), foodInMap);
      if (mapsFood == null) {
        if (unitMovement.getDirectionVelocity() == null) {
          MovementUtil.setTarget(e, currentPos);
        } else {
          MovementUtil.setTarget(e, unitMovement.getDirectionVelocity());
        }
      } else {
        Vector2 foodPosition = PhysicsUtil.getPosition(mapsFood.getWorld(), mapsFood);
        MovementUtil.setTarget(e, foodPosition.cpy().sub(currentPos));
      }

    } else {
      Vector2 foodPosition = PhysicsUtil.getPosition(e.getWorld(), nearestFood);
      MovementUtil.setTarget(e, foodPosition.cpy().sub(currentPos));
    }
    success();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }

}
