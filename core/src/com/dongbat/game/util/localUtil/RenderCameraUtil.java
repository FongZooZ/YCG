/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.util.objectUtil.Constants;

/**
 * @author Admin
 */
public class RenderCameraUtil {

  private static OrthographicCamera camera = null;

  public static OrthographicCamera getCamera() {
    if (camera == null) {
      camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      Vector2 cameraPos = physicsToRenderCoords(Constants.PHYSIC_CAMERA.DEFAULT_CAMERA_POSITION);
      camera.position.set(cameraPos, 0);
    }
    return camera;
  }

  public static Vector2 physicsToRenderCoords(Vector2 physicsCoords) {
    return physicsCoords.cpy().scl(PhysicsCameraUtil.getRatio());
  }
}
