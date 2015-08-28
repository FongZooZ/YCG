/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.util.TimeUtil;

/**
 *
 * @author Admin
 */
public class BuffInfo {

	private BuffEffect effect;
	private long frame; // int
	private long endTime; // frame
	private int duration; // miliseconds
	private Entity source;

	public BuffInfo() {
	}

	public BuffInfo(World world, BuffEffect effect, long frame, int duration, Entity source) {
		this.effect = effect;
		this.frame = frame;
		this.duration = duration;
		this.source = source;
		long convertMilisToFrame = TimeUtil.convertMillisToFrame(world, this.duration);
		this.endTime = this.frame + convertMilisToFrame;
	}

	public BuffEffect getEffect() {
		return effect;
	}

	public void setEffect(BuffEffect effect) {
		this.effect = effect;
	}

	public long getStartTime() {
		return frame;
	}

	public void setStartTime(long startTime) {
		this.frame = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}
}
