/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Collision extends Component {

	private Array<UUID> collidedList;
	private Array<UUID> justCollidedList;
	private Array<UUID> lastCollidedList;

	public Collision() {
		collidedList = new Array<UUID>();
		justCollidedList = new Array<UUID>();
		lastCollidedList = new Array<UUID>();
	}

	public Array<UUID> getCollidedList() {
		return collidedList;
	}

	public void setCollidedList(Array<UUID> collidedList) {
		this.collidedList = collidedList;
	}

	public Array<UUID> getJustCollidedList() {
		return justCollidedList;
	}

	public void setJustCollidedList(Array<UUID> justCollidedList) {
		this.justCollidedList = justCollidedList;
	}

	public Array<UUID> getLastCollidedList() {
		return lastCollidedList;
	}

	public void setLastCollidedList(Array<UUID> lastCollidedList) {
		this.lastCollidedList = lastCollidedList;
	}

}
