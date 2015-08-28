/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.dongbat.game.util.objectUtil.Constants;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Collision;
import java.util.UUID;

/**
 * @author Admin
 */
public class PhysicsUtil {

	private static final ObjectMap<com.artemis.World, World> physicsWorldMap = new ObjectMap<com.artemis.World, World>();
	private static final ObjectMap<World, com.artemis.World> artemisWorldMap = new ObjectMap<World, com.artemis.World>();

	/**
	 * Initialize a box2d world and put it into libGdx ObjectMap
	 *
	 * @param world artemis world
	 */
	public static void init(com.artemis.World world) {
		if (physicsWorldMap.get(world) != null) {
			return;
		}
		World physicsWorld = new World(new Vector2(0, Constants.PHYSICS.DEFAULT_GRAVITY), false);
		physicsWorldMap.put(world, physicsWorld);
		artemisWorldMap.put(physicsWorld, world);

	}

	/**
	 * Get artemis world of a box2d world
	 *
	 * @param world artemis world
	 * @return artemis world
	 */
	public static World getPhysicsWorld(com.artemis.World world) {
		if (!physicsWorldMap.containsKey(world)) {
			init(world);
		}
		return physicsWorldMap.get(world);
	}

	public static com.artemis.World getArtemisWorld(World world) {
		return artemisWorldMap.get(world);
	}

	public static void setPhysicsWorld(com.artemis.World world, World physicsWorld) {
		//TODO may be leak because of not dispose

		physicsWorldMap.put(world, physicsWorld);
		artemisWorldMap.put(physicsWorld, world);

	}

	/**
	 * Get radius of an entity
	 *
	 * @param world artemis world
	 * @param e entity that you want to get radius
	 * @return radius of entity in float
	 */
	public static float getRadius(com.artemis.World world, Entity e) {
		if (getBody(world, e).getFixtureList().size == 0) {
			return 0;
		}
		return getBody(world, e).getFixtureList().get(0).getShape().getRadius();
	}

	/**
	 * Set radius for an entity
	 *
	 * @param world artemis world
	 * @param e entity that you want to set radius
	 * @param r radius that you want to set for entity
	 */
	public static void setRadius(com.artemis.World world, Entity e, float r) {
		Body body = PhysicsUtil.getBody(world, e);
		if (!body.isActive()) {
			return;
		}
		getBody(world, e).getFixtureList().get(0).getShape().setRadius(r);
	}

	/**
	 * Get box2d Body of an entity from artemis world
	 *
	 * @param world artemis world
	 * @param entity entity that you want to get Body
	 * @return box2d body
	 */
	public static Body getBody(com.artemis.World world, Entity entity) {
		Physics component = EntityUtil.getComponent(world, entity, Physics.class);
		if (component == null) {
			return null;
		}
		return component.getBody();
	}

	/**
	 * Set position for an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to set position
	 * @param position position that you want to set for entity
	 */
	public static void setPosition(com.artemis.World world, Entity entity, Vector2 position) {
		Body body = getBody(world, entity);
		body.setTransform(position, body.getAngle());
	}

	/**
	 * Get position of an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to get position
	 * @return return position of entity in Vector2
	 */
	public static Vector2 getPosition(com.artemis.World world, Entity entity) {
		return getBody(world, entity).getPosition();
	}

	/**
	 * Immediately set Velocity for an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to set velocity
	 * @param velocity velocity that you want to set for entity
	 */
	public static void setVelocity(com.artemis.World world, Entity entity, Vector2 velocity) {
		getBody(world, entity).setLinearVelocity(velocity);
	}

	/**
	 * Get current velocity of an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to get velocity
	 * @return velocity in Vector2
	 */
	public static Vector2 getVelocity(com.artemis.World world, Entity entity) {
		return getBody(world, entity).getLinearVelocity();
	}

	/**
	 * Apply Linear Impulse to an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to apply
	 * @param impulse impulse amount
	 */
	public static void applyImpulse(com.artemis.World world, Entity entity, Vector2 impulse) {
		Body body = getBody(world, entity);
		body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
	}

	/**
	 * Apply Froce to an entity
	 *
	 * @param world artemis world
	 * @param entity entity that you want to apply
	 * @param force force amount
	 */
	public static void applyForce(com.artemis.World world, Entity entity, Vector2 force) {
		Body body = getBody(world, entity);
		body.applyForce(force, body.getWorldCenter(), true);
	}

	/**
	 * Check that entity a is contain entity b or not When radius of entity a
	 * greater than radius of entity b, it's called a contain b
	 *
	 * @param world artemis world
	 * @param a entity a
	 * @param b entity b
	 * @return true if a contain b
	 */
	public static boolean isBodyContain(com.artemis.World world, Entity a, Entity b) {
		if (getBody(world, b) == null || getBody(world, a) == null) {
			return false;
		}
		float len = getPosition(world, a).cpy().sub(getPosition(world, b).cpy()).len();

		return (getRadius(world, a) - getRadius(world, b)) > len;
	}

	/**
	 * Check that entity a is collide with entity b or not
	 *
	 * @param world artemis world
	 * @param a entity a
	 * @param b entity b
	 * @return true if a collide with b
	 */
	public static boolean isBodyCollided(com.artemis.World world, Entity a, Entity b) {
		Collision component = EntityUtil.getComponent(world, a, Collision.class);
		if (component == null) {
			return false;
		}
		return component.getCollidedList().contains(b.getUuid(), true);
	}

	/**
	 * Calculate radius of entity A when A eat B
	 *
	 * @param radiusA entity A
	 * @param radiusB entity B
	 * @param rate increase rate
	 * @return radius after eating
	 */
	public static float increaseRadius(float radiusA, float radiusB, float rate) {
		float volumeA = (float) (radiusA * radiusA * 3.14);
		float volumeB = (float) (radiusB * radiusB * 3.14) * rate;
		double newRadius = Math.sqrt((volumeA + volumeB) / 3.14);

		return (float) newRadius;
	}

	/**
	 * Create a box2d Body
	 *
	 * @param physicsWorld box2d world
	 * @param position body position
	 * @param radius body radius
	 * @param e set user data to entity e
	 * @return Body that was just created
	 */
	public static Body createBody(World physicsWorld, Vector2 position, float radius, Entity e) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position);

		Body body = physicsWorld.createBody(bodyDef);
		body.setUserData(UuidUtil.getUuid(e));

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1;
		fixtureDef.isSensor = true;
//    fixtureDef.filter.maskBits = 1;
//    fixtureDef.filter.categoryBits = 2;
		body.createFixture(fixtureDef);
		circle.dispose();

		return body;
	}

	/**
	 * Find food in radius, centre is an point on map
	 *
	 * @param world artemis world
	 * @param location centre of circle
	 * @param radius radius to find
	 * @return Food entity list
	 */
	public static Array<Entity> findFoodInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
		final Array<Entity> food = new Array<Entity>();

		QueryCallback callback = new QueryCallback() {

			@Override
			public boolean reportFixture(Fixture fixture) {
				Body body = fixture.getBody();
				Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
				if (entity != null) {
					if (EntityUtil.isFood(world, entity.getId())) {
						float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
						if (distanceSq <= radius * radius) {
							food.add(entity);
						}
					}
				}
				return true;
			}
		};
		Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
		Vector2 upperRight = new Vector2(location).add(radius, radius);
		getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

		return food;
	}

	/**
	 * Find enemy in radius, centre is an point on map
	 *
	 * @param world artemis world
	 * @param location centre of circle
	 * @param radius radius to find
	 * @return Enemy entity list
	 */
	public static Array<Entity> findEnemyInRaius(final com.artemis.World world, final Vector2 location, final float radius) {
		// TODO: to be fixed
		final Array<Entity> enemy = new Array<Entity>();

		QueryCallback callback = new QueryCallback() {

			@Override
			public boolean reportFixture(Fixture fixture) {
				Body body = fixture.getBody();
				Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
				boolean isEnemy = true;
				if (isEnemy) {
					float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
					if (distanceSq <= radius * radius) {
						enemy.add(entity);
					}
				}
				return true;
			}
		};

		Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
		Vector2 upperRight = new Vector2(location).add(radius, radius);
		getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

		return enemy;
	}

	/**
	 * Find nearest entity in list from a location
	 *
	 * @param world artemis world
	 * @param location location
	 * @param entityList entity list that you want to find
	 * @return one entity
	 */
	public static Entity findNearestEntityInList(com.artemis.World world, Vector2 location, Array<Entity> entityList) {
		Entity nearest = null;
		float closestDistanceSq = Float.MAX_VALUE;

		for (Entity e : entityList) {
			Vector2 position = getPosition(world, e);
			float distanceSq = new Vector2(position).sub(location).len2();
			if (distanceSq < closestDistanceSq) {
				nearest = e;
				closestDistanceSq = distanceSq;
			}
		}

		return nearest;
	}

	/**
	 * Find player in radius from a location
	 *
	 * @param world artemis world
	 * @param location location that you want to find
	 * @param radius radius to find
	 * @return Player entity list
	 */
	public static Array<Entity> findPlayerInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
		final Array<Entity> food = new Array<Entity>();

		QueryCallback callback = new QueryCallback() {

			@Override
			public boolean reportFixture(Fixture fixture) {
//				Body body = fixture.getBody();
//				Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
//				if (EntityUtil.isPlayer(world, entity.getId())) {
//					float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
//					if (distanceSq <= radius * radius) {
//						food.add(entity);
//					}
//				}
				return true;
			}
		};
		Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
		Vector2 upperRight = new Vector2(location).add(radius, radius);
		getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

		return food;
	}

	/**
	 * Create box2d Edge, use for making world, floor, ...
	 *
	 * @param world artemis world
	 * @param type Body type
	 * @param x1 x start
	 * @param y1 x finish
	 * @param x2 y start
	 * @param y2 y finish
	 * @param density densisty of edge
	 * @return Edge that was just created
	 */
	public static Body createEdge(com.artemis.World world, BodyDef.BodyType type, float x1, float y1, float x2,
					float y2, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = getPhysicsWorld(world).createBody(def);

		EdgeShape poly = new EdgeShape();
		poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));
//    box.createFixture(poly, density);
		box.setTransform(x1, y1, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
//    fixtureDef.filter.maskBits = 2;
//    fixtureDef.filter.categoryBits = 2 | 1;
		box.createFixture(fixtureDef);
//    box.setUserData(new Box2dSteeringEntity(box, true, 0.1f));
		poly.dispose();
		return box;
	}

	/**
	 * Create box2d world like a box
	 *
	 * @param world artemis world
	 */
	public static void createWorld(com.artemis.World world) {

		Body wallLeft = createEdge(world, BodyDef.BodyType.StaticBody, 0, 0, 0, 10000, 20);
//    wallLeft.setUserData(new Box2dSteeringEntity(wallLeft, true, 0));
		wallLeft.setTransform(-Constants.GAME.FRAME_WIDTH, -Constants.GAME.FRAME_HEIGHT, 0);

		Body wallRight = createEdge(world, BodyDef.BodyType.StaticBody, 0, 0, 0, 10000, 20);
		wallRight.setTransform(Constants.GAME.FRAME_WIDTH, -Constants.GAME.FRAME_HEIGHT, 0);

		Body floor = createEdge(world, BodyDef.BodyType.StaticBody, 0, 0, 10000, 0, 20);
		floor.setTransform(-Constants.GAME.FRAME_WIDTH, -Constants.GAME.FRAME_HEIGHT, 0);

		Body ceiling = createEdge(world, BodyDef.BodyType.StaticBody, 0, 0, 102000, 0, 20);
		ceiling.setTransform(-Constants.GAME.FRAME_WIDTH, Constants.GAME.FRAME_HEIGHT, 0);
	}

	/**
	 * Create a dummy box to test
	 *
	 * @param world artemis world
	 * @param type Body type
	 * @param width box width
	 * @param height box height
	 * @param density box density
	 * @return Box that was just created
	 */
	public static Body createBox(com.artemis.World world, BodyDef.BodyType type, float width, float height, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = getPhysicsWorld(world).createBody(def);

		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width, height);
		box.createFixture(poly, density);
		poly.dispose();

		return box;
	}

	public static boolean isBodyTouch(com.artemis.World world, Entity a, Entity b) {
		if (getBody(world, b) == null || getBody(world, a) == null) {
			return false;
		}
		float len = getPosition(world, a).cpy().sub(getPosition(world, b).cpy()).len();

		return (getRadius(world, a) + getRadius(world, b)) > len;
	}

}
