/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.artemis.World;
import java.util.UUID;

/**
 * @author Admin
 */
public class LocalPlayerUtil {

  private static UUID localId;

  /**
   * Get player that user are controlling if player is not create, create one
   *
   * @param world artemis world
   * @return player entity
   */
  public static UUID getLocalPlayer(World world) {
    return localId;
  }

  public static void setLocalPlayer(UUID id) {
    localId = id;
  }

}
