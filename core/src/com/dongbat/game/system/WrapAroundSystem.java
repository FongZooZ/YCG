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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class WrapAroundSystem extends EntityProcessingSystem {

  public WrapAroundSystem() {
    super(Aspect.all(Physics.class));

  }

  @Override
  protected void process(Entity e) {
    Physics physics = EntityUtil.getComponent(world, e, Physics.class);
    UnitMovement movement = EntityUtil.getComponent(world, e, UnitMovement.class);
    Vector2 pos = physics.getBody().getPosition();
    if (movement == null || movement.getDirectionVelocity() == null) {
      return;
    }
    float k = Float.POSITIVE_INFINITY;
    if (pos.x > scaleX) {

      Vector2 cpy = movement.getDirectionVelocity().cpy();
//      movement.setDirectionVelocity(new Vector2(-cpy.x, cpy.y));
      movement.setDisabled(true);

//      physics.getBody().applyForce(new Vector2(-10, 0), physics.getBody().getWorldCenter(), true);
      PhysicsUtil.applyImpulse(world, e, new Vector2(-10, 0));

      return;
    }

    if (pos.x < -scaleX) {
      Vector2 cpy = movement.getDirectionVelocity().cpy();
      movement.setDirectionVelocity(new Vector2(-cpy.x, cpy.y));
      movement.setDisabled(true);

      physics.getBody().applyForce(new Vector2(1000, 0), physics.getBody().getWorldCenter(), true);
      return;
    }

    if (pos.y < -scaleY) {
      Vector2 cpy = movement.getDirectionVelocity().cpy();
      movement.setDirectionVelocity(new Vector2(cpy.x, -cpy.y));
      movement.setDisabled(true);

      physics.getBody().applyForce(new Vector2(0, 1000), physics.getBody().getWorldCenter(), true);
      return;
    }

    if (pos.y > scaleY) {
      Vector2 cpy = movement.getDirectionVelocity().cpy();
      movement.setDirectionVelocity(new Vector2(cpy.x, -cpy.y));
      movement.setDisabled(true);

      physics.getBody().applyForce(new Vector2(0, -1000), physics.getBody().getWorldCenter(), true);
//			k = pos.y = -scaleY;
      return;
    }
    movement.setDisabled(false);
    if (k != Float.POSITIVE_INFINITY) {
      physics.getBody().setTransform(pos, physics.getBody().getAngle());
    }

  }

}
