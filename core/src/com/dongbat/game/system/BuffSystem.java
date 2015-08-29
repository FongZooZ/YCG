/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.buff.BuffInfo;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.TimeUtil;

/**
 *
 * @author Admin
 */
public class BuffSystem extends EntityProcessingSystem {

  public BuffSystem() {
    super(Aspect.all(BuffComponent.class));
  }

  @Override
  protected void process(Entity entity) {
    BuffComponent buffComponent = EntityUtil.getComponent(world, entity, BuffComponent.class);
    ObjectMap<String, BuffInfo> buffs = buffComponent.getBuffs();
    Array<String> toRemove = new Array<String>();

    for (ObjectMap.Entry<String, BuffInfo> buff : buffs) {
      BuffEffect effect = buff.value.getEffect();
      Entity source = buff.value.getSource();
      if (buff.value.getEndTime() <= TimeUtil.getCurrentFrame(world) && !buff.value.isPermanent()) {
        effect.durationEnd(world, source, entity);
        toRemove.add(buff.key);
        continue;
      }
      effect.update(world, source, entity);
    }

    for (String name : toRemove) {
      buffs.remove(name);
    }
  }

}
