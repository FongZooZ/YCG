/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import static com.dongbat.game.util.networkUtil.KryoUtil.getKryo;
import com.dongbat.game.util.msg.WorldState;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Admin
 */
public class WorldStateUtil {

  public static WorldState copy(WorldState state) {
    return getKryo().copy(state);
  }

  public static byte[] serialize(WorldState state) {
    Output output = new Output(2048, -1);
    getKryo().writeObjectOrNull(output, state, WorldState.class);
    byte[] buffer = output.getBuffer();
    output.close();
    return buffer;
  }

  public static WorldState deserialize(byte[] data) {
    Input input = new Input(new ByteArrayInputStream(data));
    WorldState worldState = getKryo().readObjectOrNull(input, WorldState.class);
    input.close();
    return worldState;
  }
}
