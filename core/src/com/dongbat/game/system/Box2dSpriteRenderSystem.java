package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by FongZooZ on 8/29/2015.
 */
public class Box2dSpriteRenderSystem extends BaseSystem {

  private Batch batch;
  private World b2World;
  private OrthographicCamera camera;

  /**
   * Override to implement code that gets executed when systems are
   * initialized.
   */
  @Override
  protected void initialize() {
    batch = RenderUtil.getBatch();
    b2World = PhysicsUtil.getPhysicsWorld(world);
    camera = PhysicsCameraUtil.getCamera();
  }

  /**
   * Any implementing entity system must implement this method.
   */
  @Override
  protected void processSystem() {
    batch.begin();
    batch.setProjectionMatrix(camera.combined);
    Box2DSprite.draw(batch, b2World);
    batch.end();
  }
}
