package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.Constants;
import com.rahul.libgdx.parallax.ParallaxBackground;

/**
 * Created by FongZooZ on 8/30/2015.
 * Create Parrallax Background Layer system
 */
public class ParallaxBackgroundSystem extends BaseSystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ParallaxBackground background;

    private final float worldWidth = Constants.GAME.FRAME_WIDTH;
    private final float worldHeight = Constants.GAME.FRAME_HEIGHT;

    public ParallaxBackgroundSystem() {
        batch = RenderUtil.getBatch();
        camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.update();
        background = new ParallaxBackground();

//        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg"))), worldWidth / 5, worldHeight / 5, new Vector2(.5f, .5f)));
    }

    /**
     * Any implementing entity system must implement this method.
     */
    @Override
    protected void processSystem() {
        camera.position.add(0.3f * world.delta, 0, 0);
        batch.begin();
        background.draw(camera, batch);
        batch.end();
    }
}
