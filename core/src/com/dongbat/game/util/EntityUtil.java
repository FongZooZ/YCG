/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Queen;
import com.dongbat.game.util.objectUtil.MapperCache;

import java.util.UUID;

import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;

/**
 * @author Admin
 */
public class EntityUtil {

  /**
   * Caching Component Mapper, avoiding create too much new instance. It reduce
   * memory leak
   */
  private static ObjectMap<World, MapperCache> mapperCaches = new ObjectMap<World, MapperCache>();

  /**
   * Get Mapper of an Component in artemis
   *
   * @param world artemis world
   * @param type  type of Component you want to get Mapper
   * @param <T>   class type
   * @return ComponentMapper
   */
  public static <T extends Component> ComponentMapper<T> getMapper(World world, Class<T> type) {
    if (!mapperCaches.containsKey(world)) {
      mapperCaches.put(world, new MapperCache(world));
    }
    MapperCache cache = mapperCaches.get(world);
    return cache.getMapper(type);
  }

  public static IntBag getAllEntities(World world) {
    //TODO: world is not update, entities bag is not update when world progress 
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all()).getEntities();
    return entities;
  }

  public static boolean isFood(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Food.class)).getEntities();
    return entities.contains(id);
  }

  public static boolean isPlayer(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Player.class)).getEntities();
    return entities.contains(id);
  }

  public static boolean isAiUnit(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(AiControl.class)).getEntities();
    return entities.contains(id);
  }

  public static boolean isQueen(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Queen.class)).getEntities();
    return entities.contains(id);
  }

  /**
   * Get specific Component class of an entity in the artemis world
   *
   * @param world artemis world
   * @param e     entity that you want to get Component class
   * @param type  type of Component you want to get, example: Stats.class,
   *              Food.class
   * @param <T>   class type
   * @return Component class
   */
  public static <T extends Component> T getComponent(World world, Entity e, Class<T> type) {
    return getMapper(world, type).getSafe(e);
  }

  public static Array<Entity> findUnitAndPlayerInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isPlayer(world, entity.getId()) || isAiUnit(world, entity.getId())) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            entities.add(entity);
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

  /**
   * Find any entity in radius
   *
   * @param world    artemis world
   * @param location location to find
   * @param radius   radius to find
   * @return Array of nearest Entity in radius
   */
  public static Array<Entity> findAnyInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
        if (distanceSq <= radius * radius) {
          entities.add(entity);
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

  public static Vector2 getQueenPosition(final com.artemis.World world) {
    final Vector2 queenPosition = null;
    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isQueen(world, entity.getId())) {
          queenPosition.x = body.getPosition().x;
          queenPosition.y = body.getPosition().y;
          return false;
        }
        return true;
      }

    };
    PhysicsUtil.getPhysicsWorld(world)
            .QueryAABB(callback, -scaleX, -scaleY, scaleX, scaleY);

    return queenPosition;
  }

  public static Array<Entity> findFood(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isFood(world, entity.getId())) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            entities.add(entity);
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

}
