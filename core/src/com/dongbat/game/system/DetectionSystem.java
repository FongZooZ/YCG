/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.Player;
import com.dongbat.game.util.WorldQueryUtil;

/**
 *
 * @author Admin
 */
public class DetectionSystem extends EntityProcessingSystem {

  public DetectionSystem() {
    super(Aspect.all(Player.class, Detection.class));
  }

  @Override
  protected void process(Entity e) {

    WorldQueryUtil.findFoodInRadius(world, Vector2.Zero, 10);

    WorldQueryUtil.findPlayerInRadius(world, Vector2.Zero, 10);

    Array<Entity> findQueenInRadius = WorldQueryUtil.findQueenInRadius(world, Vector2.Zero, 100);

    if (findQueenInRadius.size > 0) {
      System.out.println("dccm");
    }
  }

}
