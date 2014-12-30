/*
 * Author: 598Johnn897
 * 
 * Date: Dec 29, 2014
 * Package: org.teammoose.util
 */
package org.teammoose.util;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author 598Johnn897
 * @since
 */
public class RegisterUtil
{
	public static void registerEvents(Plugin plugin)
	{
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0)
		{
			return;
		}
		for (Class<?> c : classes)
		{
			try
			{
				if (Listener.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation())
				{
					if (JavaPlugin.class.isAssignableFrom(c))
					{
						if (plugin.getClass().equals(c))
						{
							Bukkit.getPluginManager().registerEvents(
									(Listener) plugin, plugin);
						}
					} else
					{
						Bukkit.getPluginManager().registerEvents(
								(Listener) c.newInstance(), plugin);
					}
				}
			} catch (InstantiationException e)
			{
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor.");
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor.");
				e.printStackTrace();
			}
		}
	}

}
