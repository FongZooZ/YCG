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
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;

/**
 *
 * @author Admin
 */
public class RandomDestinationSchedule implements BuffEffect {

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    UnitMovement unitComponent = EntityUtil.getComponent(world, target, UnitMovement.class);
    float posX = (float) ((Math.random() * 2 - 1) * scaleX);
    float posY = (float) ((Math.random() * 2 - 1) * scaleY);
    unitComponent.setDirectionVelocity(new Vector2(posX, posY).nor().scl(100));
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
