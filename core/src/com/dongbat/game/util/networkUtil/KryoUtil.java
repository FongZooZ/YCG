/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import java.io.ByteArrayInputStream;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 *
 * @author Admin
 */
public class KryoUtil {

  private static Kryo kryo;

  public static Kryo getKryo() {
    if (kryo == null) {
      kryo = newKryo();
    }
    return kryo;
  }

  public static Kryo newKryo() {
    Kryo kryo = new Kryo();
    kryo.setAsmEnabled(true);
    kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    kryo.register(Body.class, new DefaultSerializers.VoidSerializer());
    kryo.register(DistanceJoint.class, new DefaultSerializers.VoidSerializer());
    return kryo;
  }

  public static <T> byte[] serialize(T data, Class<T> type) {
    Output output = new Output(2048, -1);
    getKryo().writeObjectOrNull(output, data, type);
    byte[] buffer = output.getBuffer();
    output.close();
    return buffer;
  }

  public static <T> T deserialize(byte[] data, Class<T> type) {
    Input input = new Input(new ByteArrayInputStream(data));
    T value = getKryo().readObjectOrNull(input, type);
    input.close();
    return value;
  }
}
