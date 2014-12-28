/*
 * Author: 598Johnn897
 * 
 * Date: Dec 27, 2014
 * Package: org.teammoose
 */
package org.teammoose;

import java.util.logging.Level;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.teammoose.lib.References;

/**
 * TeamMoose Main class for TeamMoose plugin.
 * This is just an example to show the coding style 
 * and such. yolo. sweg. moose. kthnxbai. I hope
 * no one reads this.
 * 
 * @author 598Johnn897
 * @since 0.0.1-SNAPSHOT
 * @see JavaPlugin
 */
public class TeamMooseMain extends JavaPlugin
{
	/**
	 * The instance for the class.
	 * 
	 * @author 598Johnn897
	 * @since 0.0.1-SNAPSHOT
	 */
	private static TeamMooseMain instance;
	
	/**
	 * Gets the instance for the class and returns
	 * it if it is not null.
	 * 
	 * @author 598Johnn897
	 * @since 0.0.1-SNAPSHOT
	 * @return returns the instance if it is not null
	 */
	public static TeamMooseMain get()
	{
		Validate.notNull(instance);
		return instance;
	}
	
	/**
	 * The logger for TeamMoose plugin.
	 * 
	 * @author 598Johnn897
	 * @since 0.0.1-SNAPSHOT
	 * @return the instance for the logger.
	 */
	@Getter private TMLogger tMLogger;
	
	@Override public void onLoad()
	{
		log(References.LOADING);
		instance = this;
	}
	
	@Override public void onEnable()
	{
		log(References.ENABLING);
		try 
		{
			tMLogger = new TMLogger(get());
		} 
		catch (Exception e)
		{
			error(References.ERROR);
			e.printStackTrace();
		} 
		finally 
		{
			log(References.ENABLED);
		}
	}
	
	@Override public void onDisable()
	{
		log(References.DISABLING);
		try
		{
			
		}
		catch (Exception e)
		{
			error(References.ERROR);
			e.printStackTrace();
		}
		finally
		{
			log(References.DISABLED);
		}
	}
	
	/**
	 * to lazy to write getTMLogger.log(Level.INFO, "sweg") everytime. :3
	 * 
	 * @author 598Johnn897
	 * @since 0.0.1-SNAPSHOT
	 * @see TMLogger
	 */
	private void log(Object msg) { getTMLogger().log(Level.INFO, msg.toString());}
	
	/**
	 * to lazy to write getTMLogger.log(Level.SEVERE, "oh no") everytime. :3
	 * 
	 * @author 598Johnn897
	 * @since 0.0.1-SNAPSHOT
	 * @see TMLogger
	 */
	private void error(Object msg) { getTMLogger().log(Level.SEVERE, msg.toString());}
	
}
