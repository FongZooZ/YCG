package com.dongbat.game.system.localSystem;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Player;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;

/**
 * Created by FongZooZ on 8/30/2015.
 * Render Entities with Sprite
 */
public class SpriteRenderSystem extends EntityProcessingSystem {

    private Sprite texture;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    /**
     * Override to implement code that gets executed when systems are
     * initialized.
     */
    @Override
    protected void initialize() {
        batch = RenderUtil.getBatch();
        texture = new Sprite(new Texture(Gdx.files.internal("circle.png")));
        camera = PhysicsCameraUtil.getCamera();
    }

    /**
     * Creates a new EntityProcessingSystem.
     */
    public SpriteRenderSystem() {
        super(Aspect.all(Player.class));
    }

    /**
     * Process a entity this system is interested in.
     *
     * @param e the entity to process
     */
    @Override
    protected void process(Entity e) {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        float radius = PhysicsUtil.getRadius(world, e);
        Vector2 position = PhysicsUtil.getPosition(world, e);
        texture.setSize(2 * radius, 2 * radius);
        texture.setPosition(position.x - radius, position.y - radius);
        texture.draw(batch);
        batch.end();
    }
}
