package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import com.rahul.libgdx.parallax.ParallaxBackground;
import com.rahul.libgdx.parallax.TextureRegionParallaxLayer;
import com.rahul.libgdx.parallax.Utils;

import java.util.UUID;

/**
 * Created by FongZooZ on 8/30/2015.
 * Create Parrallax Background Layer system
 */
public class ParallaxBackgroundSystem extends BaseSystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ParallaxBackground background;

    private float worldWidth = 10;
    private float worldHeight;

    public ParallaxBackgroundSystem() {
        batch = RenderUtil.getBatch();

        worldHeight = Utils.calculateOtherDimension(Utils.WH.width, worldWidth, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        camera = new OrthographicCamera(worldHeight, worldWidth);
        background = new ParallaxBackground();
        worldWidth = camera.viewportWidth;
        worldHeight = camera.viewportHeight;

        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg00), worldWidth, worldHeight, new Vector2(.1f, .1f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg01), worldWidth, worldHeight, new Vector2(.2f, .2f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg02), worldWidth, worldHeight, new Vector2(.3f, .3f)));
        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg03), worldWidth, worldHeight, new Vector2(.4f, .4f)));

    }

    /**
     * Any implementing entity system must implement this method.
     */
    @Override
    protected void processSystem() {
        UUID uuid = LocalPlayerUtil.getLocalPlayer(world);
        Entity e = UuidUtil.getEntityByUuid(world, uuid);
        camera.position.set(PhysicsUtil.getPosition(world, e), 0);
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        background.draw(camera, batch);
        batch.end();

    }
}
