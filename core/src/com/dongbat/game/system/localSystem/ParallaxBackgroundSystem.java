package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.Constants;
import com.rahul.libgdx.parallax.ParallaxBackground;
import com.rahul.libgdx.parallax.TextureRegionParallaxLayer;

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
//        camera.setToOrtho(false, worldWidth, worldHeight);
        camera.update();
        background = new ParallaxBackground();

        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/0.png"))), worldWidth, worldHeight, new Vector2(.1f, .1f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/1.png"))), worldWidth, worldHeight, new Vector2(.2f, .2f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/2.png"))), worldWidth, worldHeight, new Vector2(.3f, .3f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/3.png"))), worldWidth, worldHeight, new Vector2(.4f, .4f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/4.png"))), worldWidth, worldHeight, new Vector2(.5f, .5f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("background/5.png"))), worldWidth, worldHeight, new Vector2(.6f, .6f)));

    }

    /**
     * Any implementing entity system must implement this method.
     */
    @Override
    protected void processSystem() {
//        camera.position.set(Phy)
        camera.position.add(0.3f * world.delta, 0, 0);
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        background.draw(camera, batch);
        batch.end();
    }
}
