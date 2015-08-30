/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.UnitFactory;

/**
 *
 * @author Admin
 */
public class SplitAndJoin implements Ability {
  
  @Override
  public String getTooltip() {
    return "split and join";
  }
  
  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    Vector2 position = PhysicsUtil.getPosition(world, caster);
    UnitMovement ms = EntityUtil.getComponent(world, caster, UnitMovement.class);
    Vector2 direction = ms.getDirectionVelocity();
    float radius = PhysicsUtil.getRadius(world, caster);
    float newRadius = radius / 1.4121f;
    
    BuffUtil.addBuff(world, caster, caster, "Forced", 1000, 1, "forceStrength", 100, "direction", direction.cpy().scl(-1));
    PhysicsUtil.setRadius(world, caster, newRadius);
    
    Entity bullet = UnitFactory.createProjectileUnit(world, caster, direction.cpy().nor().scl(radius + newRadius).add(position.cpy()), newRadius, true, false);
    BuffUtil.addBuff(world, caster, bullet, "Forced", 3000, 1, "forceStrength", 100, "direction", direction.cpy().scl(1));
    BuffUtil.addBuff(world, bullet, bullet, "Feed", -1, 1, "feedPerSecond", 0.55f);
  }
  
}
