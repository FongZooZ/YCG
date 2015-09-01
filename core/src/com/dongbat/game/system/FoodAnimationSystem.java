package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Food;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.RenderUtil;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by FongZooZ on 8/31/2015.
 * Render animation of Food
 */
public class FoodAnimationSystem extends EntityProcessingSystem {

    private static SpriteBatch batch;

    /**
     * Creates a new EntityProcessingSystem.
     */
    public FoodAnimationSystem() {
        super(Aspect.all(Food.class, Display.class));
    }

    /**
     * Called after the systems has finished processing.
     */
    @Override
    protected void end() {
        batch.end();
    }

    /**
     * Override to implement code that gets executed when systems are
     * initialized.
     */
    @Override
    protected void initialize() {
        batch = RenderUtil.getBatch();
    }

    /**
     * Called before system processing begins.
     * <p>
     * <b>Nota Bene:</b> Any entities created in this method
     * won't become active until the next system starts processing
     * or when a new processing rounds begins, whichever comes first.
     * </p>
     */
    @Override
    protected void begin() {
        batch.begin();
    }

    /**
     * Process a entity this system is interested in.
     *
     * @param e the entity to process
     */
    @Override
    protected void process(Entity e) {
        Food food = EntityUtil.getComponent(world, e, Food.class);
        Display display = EntityUtil.getComponent(world, e, Display.class);

        if (!food.isToxic()) {
            Animation animation = new Animation(0.1f, new TextureRegion(AssetUtil.cold));
            animation.setPlayMode(Animation.PlayMode.LOOP);
            display.setDefaultAnimation(new AnimatedSprite(animation));
        }
    }
}
