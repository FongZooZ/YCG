package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.ability.AbilityInfo;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Vacuum implements Ability {

  private int duration;

  /**
   * Get tooltip of ability
   *
   * @return tooltip in String
   */
  @Override
  public String getTooltip() {
    return "Suck anything smaller in radius.";
  }

  /**
   * Cast ability to the target when entity caster cast by using input on screen
   *
   * @param world artemis world
   * @param caster entity which use ability
   * @param target target of ability
   */
  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    AbilityComponent playerAbilityList = EntityUtil.getComponent(world, caster, AbilityComponent.class);
    AbilityInfo info = playerAbilityList.getAbility("Vacuum");
    if (info == null) {
      return;
    }
    BuffUtil.addBuff(world, caster, caster, "Vacuum", duration, 1);
  }
}
