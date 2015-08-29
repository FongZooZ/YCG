package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.ability.AbilityInfo;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.EntityFactory;
import com.dongbat.game.util.objectUtil.Constants;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Flee implements Ability {

  private int duration;
  private float forceStrength;

  /**
   * Get tooltip of ability
   *
   * @return tooltip in String
   */
  @Override
  public String getTooltip() {
    return "Player will move faster but loss amount of food behind!";
  }

  /**
   * Cast ability to the target when entity caster
   * cast by using input on screen
   *
   * @param world  artemis world
   * @param caster entity which use ability
   * @param target target of ability
   */
  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    AbilityComponent playerAbilityList = EntityUtil.getComponent(world, caster, AbilityComponent.class);
    AbilityInfo info = playerAbilityList.getAbility("Flee");
    if (info == null) {
      return;
    }
    float playerRadius = PhysicsUtil.getRadius(world, caster);
    Vector2 playerPosition = PhysicsUtil.getPosition(world, caster);
    UnitMovement unitMovement = EntityUtil.getComponent(world, caster, UnitMovement.class);

    Vector2 destination = unitMovement.getDirectionVelocity();
    if (destination == null) {
      return;
    }

    // TODO: vi tri spawn sai!
    Vector2 direction = destination.cpy().scl(-1).nor();
//    Vector2 foodPosition = playerPosition.add((new Vector2(playerRadius, playerRadius)).add(new Vector2(Constants.FOOD.DEFAULT_RADIUS, Constants.FOOD.DEFAULT_RADIUS)).scl(direction));
    Vector2 foodPosition = playerPosition.cpy().nor().scl(playerPosition.len() - playerRadius - Constants.FOOD.DEFAULT_RADIUS - 1).add(playerPosition);
    if (playerRadius <= Constants.FOOD.DEFAULT_RADIUS * 2) {
      return;
    }
    PhysicsUtil.setRadius(world, caster, playerRadius - Constants.FOOD.DEFAULT_RADIUS);
    Entity food = EntityFactory.createSteeringFood(world, foodPosition);
    BuffUtil.addBuff(world, caster, food, "Forced", duration, 1, "forceStrength", forceStrength, "direction", direction);
  }
}
