package org.teammoose.disguises;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.teammoose.reflections.ReflectionUtil;
import org.teammoose.reflections.ReflectionUtil.RefClass;
import org.teammoose.reflections.ReflectionUtil.RefConstructor;
import org.teammoose.reflections.ReflectionUtil.RefField;
import org.teammoose.reflections.ReflectionUtil.RefMethod;

public class Disguise
{

	private DisguiseType disguise;
	private String player;
	private RefClass entity;
	private Class<?> entityObject;
	private Object thisObject;

	public Disguise(DisguiseType d, String p)
	{
		disguise = d;
		player = p;
		Location location = Bukkit.getServer().getPlayer(p).getLocation();
		switch (disguise)
		{
		case ZOMBIE:
			entity = getEntity("EntityZombie", p);
			break;
		case WITHER_SKELETON:
			entity = getEntity("EntitySkeleton", p);

			RefMethod methodSkeleton = entity
					.findMethodByName("setSkeletonType");

			methodSkeleton.call(1);
			break;
		case SKELETON:
			entity = getEntity("EntitySkeleton", p);
			break;
		case ZOMBIEPIG:
			entity = getEntity("EntityPigZombie", p);
			break;
		case BLAZE:
			entity = getEntity("EntityBlaze", p);
			break;
		case ENDERMAN:
			entity = getEntity("EntityEnderman", p);
			break;
		case CREEPER:
			entity = getEntity("EntityCreeper", p);
			break;
		case SPIDER:
			entity = getEntity("EntitySpider", p);
			break;
		case WITCH:
			entity = getEntity("EntityWitch", p);
			break;
		case WITHER_BOSS:
			entity = getEntity("EntityWither", p);
			break;
		case GHAST:
			entity = getEntity("EntityGhast", p);
			break;
		case GIANT:
			entity = getEntity("EntityGiant", p);
			break;
		}
		if (d != null)
		{

			Method m;
			Method mm;
			try
			{
				m = entityObject.getMethod("setPosition", double.class,
						double.class, double.class);
				mm = entityObject.getMethod("d", int.class);

				m.invoke(thisObject, location.getX(), location.getY(),
						location.getZ());
				mm.invoke(thisObject, Bukkit.getServer().getPlayer(p)
						.getEntityId());
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			RefField rf = entity.getField("locX");

			rf.of(thisObject).set(location.getX());

			RefField rf1 = entity.getField("locY");

			rf1.of(thisObject).set(location.getY());

			RefField rf2 = entity.getField("locZ");

			rf2.of(thisObject).set(location.getZ());

			RefField rf3 = entity.getField("yaw");

			rf3.of(thisObject).set(location.getYaw());

			RefField rf4 = entity.getField("pitch");

			rf4.of(thisObject).set(location.getPitch());

		}
	}

	public Player getPlayer()
	{
		return Bukkit.getPlayer(player);
	}

	public void removeDisguise()
	{
		this.disguise = null;

		RefClass p29 = ReflectionUtil
				.getRefClass("{nms}.PacketPlayOutEntityDestroy");

		RefClass p20 = ReflectionUtil
				.getRefClass("{nms}.PacketPlayOutNamedEntitySpawn");

		RefConstructor pp20 = p20.getConstructor(ReflectionUtil
				.getRefClass("{nms}.EntityHuman"));

		RefConstructor pp29 = p29.getConstructor(int[].class);

		int[] entityId;

		entityId = new int[1];

		entityId[0] = Bukkit.getPlayer(player).getEntityId();

		Object packetEntityDestroy = pp29.create(entityId);

		Object packetNamedEntitySpawn = pp20.create((ReflectionUtil
				.getRefClass("{cb}.entity.CraftPlayer")).getMethod("getHandle")
				.of(getPlayer()).call());

		RefClass classCraftPlayer = ReflectionUtil
				.getRefClass("{cb}.entity.CraftPlayer");
		RefMethod methodGetHandle = classCraftPlayer.getMethod("getHandle");
		RefClass classEntityPlayer = ReflectionUtil
				.getRefClass("{nms}.EntityPlayer");
		RefField fieldPlayerConnection = classEntityPlayer
				.getField("playerConnection");
		RefClass classPlayerConnection = ReflectionUtil
				.getRefClass("{nms}.PlayerConnection");
		RefMethod methodSendPacket = classPlayerConnection
				.findMethodByName("sendPacket");

		for (Player player : Bukkit.getOnlinePlayers())
		{

			if (player != getPlayer())
			{
				Object handle = methodGetHandle.of(player).call();
				Object connection = fieldPlayerConnection.of(handle).get();

				methodSendPacket.of(connection).call(packetEntityDestroy);
				methodSendPacket.of(connection).call(packetNamedEntitySpawn);
			}
		}

	}

	public void changeDisguise(DisguiseType d)
	{
		removeDisguise();
		this.disguise = d;
		Disguise dis = new Disguise(d, player);
		dis.disguiseToAll();
	}

	public void disguiseToAll()
	{

		RefClass p29 = ReflectionUtil
				.getRefClass("{nms}.PacketPlayOutEntityDestroy");

		RefClass p20 = ReflectionUtil
				.getRefClass("{nms}.PacketPlayOutSpawnEntityLiving");

		RefConstructor pp20 = p20.getConstructor(ReflectionUtil
				.getRefClass("{nms}.EntityLiving"));

		RefConstructor pp29 = p29.getConstructor(int[].class);

		int[] entityId;

		entityId = new int[1];

		entityId[0] = Bukkit.getPlayer(player).getEntityId();

		Object packetEntityDestroy = pp29.create(entityId);

		Object packetNamedEntitySpawn = pp20.create(thisObject);

		RefClass classCraftPlayer = ReflectionUtil
				.getRefClass("{cb}.entity.CraftPlayer");
		RefMethod methodGetHandle = classCraftPlayer.getMethod("getHandle");
		RefClass classEntityPlayer = ReflectionUtil
				.getRefClass("{nms}.EntityPlayer");
		RefField fieldPlayerConnection = classEntityPlayer
				.getField("playerConnection");
		RefClass classPlayerConnection = ReflectionUtil
				.getRefClass("{nms}.PlayerConnection");
		RefMethod methodSendPacket = classPlayerConnection
				.findMethodByName("sendPacket");

		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all != Bukkit.getPlayer(player))
			{
				Object handle = methodGetHandle.of(all).call();
				Object connection = fieldPlayerConnection.of(handle).get();

				methodSendPacket.of(connection).call(packetEntityDestroy);
				methodSendPacket.of(connection).call(packetNamedEntitySpawn);
			}
		}
	}

	public static enum DisguiseType
	{
		ZOMBIE(Type.BIPED), 
		WITHER_SKELETON(Type.BIPED), 
		SKELETON(Type.BIPED), 
		ZOMBIEPIG(Type.BIPED), 
		BLAZE(Type.MOB), 
		ENDERMAN(Type.MOB), 
		CREEPER(Type.MOB), 
		SPIDER(Type.MOB), 
		WITCH(Type.MOB), 
		WITHER_BOSS(Type.MOB), 
		GHAST(Type.MOB), 
		GIANT(Type.MOB);

		private Type type;

		DisguiseType(Type type)
		{
			this.type = type;
		}

		public Type getType()
		{
			return type;
		}

		public boolean isBiped()
		{
			if (type == Type.BIPED)
			{
				return true;
			}
			return false;
		}

		public static enum Type
		{
			BIPED, MOB;
		}
	}

	private RefClass getEntity(String entity, String p)
	{
		RefClass ent = ReflectionUtil.getRefClass("{nms}." + entity);

		RefConstructor entConstructor = ent.getConstructor(ReflectionUtil
				.getRefClass("{nms}.World"));

		RefClass classCraftWorld = ReflectionUtil
				.getRefClass("{cb}.CraftWorld");
		RefMethod methodGetHandle = classCraftWorld.getMethod("getHandle");

		Object handle = methodGetHandle.of(
				Bukkit.getServer().getPlayer(p).getWorld()).call();

		Object fin = entConstructor.create(handle);

		this.thisObject = fin;
		this.entityObject = fin.getClass();

		return ReflectionUtil.getRefClass(entityObject);
	}

}