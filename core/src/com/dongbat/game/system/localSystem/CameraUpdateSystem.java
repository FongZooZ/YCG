/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CameraUpdateSystem extends BaseSystem {

  @Override
  protected void initialize() {
  }

  @Override
  protected void processSystem() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
    Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
    //TODO: not check here
    if (e == null) {
      return;
    }

    PhysicsCameraUtil.getCamera().position.set(new Vector3(PhysicsUtil.getPosition(world, e), 0));
    PhysicsCameraUtil.getCamera().zoom = PhysicsCameraUtil.getZoomScale(world, e);
    PhysicsCameraUtil.getCamera().update();

  }

}
