/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dongbat.game.util.objectUtil.Constants;

/**
 * @author Admin
 */
public class PhysicsCameraUtil {

	private static OrthographicCamera camera;

	public static OrthographicCamera getCamera() {
		if (camera == null) {
			float ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			int viewportX;
			int viewportY;
			if (ratio <= 1) {
				viewportX = Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
				viewportY = (int) (viewportX / ratio);
			} else {
				viewportY = Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
				viewportX = (int) (viewportY * ratio);
			}
			camera = new OrthographicCamera(viewportX / 20, viewportY / 20);
//      camera.position.set(Constants.PHYSIC_CAMERA.DEFAULT_CAMERA_X, Constants.PHYSIC_CAMERA.DEFAULT_CAMERA_Y, 0);
		}
		return camera;
	}

	public static float getRatio() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		return (float) (width <= height ? width : height) / Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
	}
}
