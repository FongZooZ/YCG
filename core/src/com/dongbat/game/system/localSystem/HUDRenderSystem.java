/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffInfo;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Stats;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class HUDRenderSystem extends BaseSystem {

	private final BitmapFont bitmapFont;
	private final SpriteBatch batch;

	public HUDRenderSystem() {
		bitmapFont = new BitmapFont();
		batch = new SpriteBatch();
	}

	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	protected void processSystem() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
		Entity localPlayer = UuidUtil.getEntityByUuid(world, localPlayerId);
		if (localPlayer == null) {
			return;
		}
		Stats stat = EntityUtil.getComponent(world, localPlayer, Stats.class);
		bitmapFont.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 100, 100);
		bitmapFont.draw(batch, "body count: " + PhysicsUtil.getPhysicsWorld(world).getBodyCount(), 100, 75);
		if (stat != null) {
			bitmapFont.draw(batch, "base speed: " + stat.getBaseMovementSpeed(), 100, 25);
			bitmapFont.draw(batch, "modifier speed: " + stat.getModifierSpeed(), 100, 50);
		}
		bitmapFont.draw(batch, "frame " + ECSUtil.getFrame(world), 100, 125);
		BuffComponent buffComponent = EntityUtil.getComponent(world, localPlayer, BuffComponent.class);
		ObjectMap<String, BuffInfo> buffs = buffComponent.getBuffs();
		for (ObjectMap.Entry<String, BuffInfo> buff : buffs) {
			bitmapFont.draw(batch, "buff " + buff.value.getDuration() + "", 100, 425);
			return;
		}
	}

	@Override
	protected void end() {
		batch.end();
	}

}
