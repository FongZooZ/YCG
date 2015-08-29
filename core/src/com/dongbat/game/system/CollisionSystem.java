/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.dongbat.game.component.Collision;
import com.dongbat.game.component.Detection;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CollisionSystem extends BaseSystem {

  private World physicWorld;

  public CollisionSystem() {
  }

  @Override
  protected void processSystem() {
    physicWorld = PhysicsUtil.getPhysicsWorld(world);
    for (Contact contact : physicWorld.getContactList()) {
      if (!(contact.getFixtureA().getBody().getUserData() instanceof UUID)) {
        continue;
      }
      if (!(contact.getFixtureB().getBody().getUserData() instanceof UUID)) {
        continue;
      }
      if ("collision".equals(contact.getFixtureA().getUserData()) && "collision".equals(contact.getFixtureB().getUserData())) {
        UUID idA = (UUID) contact.getFixtureA().getBody().getUserData();
        UUID idB = (UUID) contact.getFixtureB().getBody().getUserData();

        Entity a = UuidUtil.getEntityByUuid(world, idA);
        Entity b = UuidUtil.getEntityByUuid(world, idB);

        addCollidedEntity(a, b);
        addCollidedEntity(b, a);
      }
    }
  }

  private void addCollidedEntity(Entity a, Entity b) {
    if (a == null || b == null) {
      return;
    }
    if (!a.isActive() || !b.isActive()) {
      return;
    }
    Collision collision = EntityUtil.getComponent(world, a, Collision.class);

    if (collision != null) {
      collision.getCollidedList().add(UuidUtil.getUuid(b));
      if (!collision.getLastCollidedList().contains(UuidUtil.getUuid(b), true)) {
        collision.getJustCollidedList().add(UuidUtil.getUuid(b));
      }
    }
  }

  private void addDetectionEntity(Entity a, Entity b) {
    if (a == null || b == null) {
      return;
    }
    if (!a.isActive() || !b.isActive()) {
      return;
    }
    Detection detection = EntityUtil.getComponent(world, a, Detection.class);

    if (detection != null) {
      detection.getDetectionList().add(UuidUtil.getUuid(b));
      if (!detection.getLastDetectionList().contains(UuidUtil.getUuid(b), true)) {
        detection.getJustDetectionList().add(UuidUtil.getUuid(b));
      }
    }
  }
}
