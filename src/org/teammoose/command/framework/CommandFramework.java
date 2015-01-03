package org.teammoose.command.framework;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Command Framework - CommandFramework <br>
 * The main command framework class used for controlling the framework.
 * 
 * @author minnymin3
 */
public class CommandFramework
{

	private final Map<String, Entry<Method, Object>> commandMap = new HashMap<String, Entry<Method, Object>>();
	private CommandMap map;
	private final Plugin plugin;

	/**
	 * Initializes the command framework and sets up the command maps
	 * 
	 * @param plugin
	 */
	public CommandFramework(Plugin plugin)
	{
		this.plugin = plugin;
		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager)
		{
			SimplePluginManager manager = (SimplePluginManager) plugin
					.getServer().getPluginManager();
			try
			{
				Field field = SimplePluginManager.class
						.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap) field.get(manager);
			} catch (IllegalArgumentException | NoSuchFieldException
					| IllegalAccessException | SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles commands. Used in the onCommand method in your JavaPlugin class
	 * 
	 * @param sender
	 *            The {@link org.bukkit.command.CommandSender} parsed from
	 *            onCommand
	 * @param label
	 *            The label parsed from onCommand
	 * @param cmd
	 *            The {@link org.bukkit.command.Command} parsed from onCommand
	 * @param args
	 *            The arguments parsed from onCommand
	 * @return Always returns true for simplicity's sake in onCommand
	 */
	public boolean handleCommand(CommandSender sender, String label,
			org.bukkit.command.Command cmd, String[] args)
	{
		for (int i = args.length; i >= 0; i--)
		{
			StringBuilder buffer = new StringBuilder();
			buffer.append(label.toLowerCase());
			for (int x = 0; x < i; x++)
			{
				buffer.append(".").append(args[x].toLowerCase());
			}
			String cmdLabel = buffer.toString();
			if (commandMap.containsKey(cmdLabel))
			{
				Entry<Method, Object> entry = commandMap.get(cmdLabel);
				Command command = entry.getKey().getAnnotation(Command.class);
				if (!sender.hasPermission(command.permission()))
				{
					sender.sendMessage(command.noPerm());
					return true;
				}
				try
				{
					entry.getKey().invoke(
							entry.getValue(),
							new CommandArgs(sender, cmd, label, args, cmdLabel
									.split("\\.").length - 1));
				} catch (IllegalArgumentException | InvocationTargetException
						| IllegalAccessException e)
				{
					e.printStackTrace();
				}
				return true;
			}
		}
		defaultCommand(new CommandArgs(sender, cmd, label, args, 0));
		return true;
	}

	/**
	 * Registers all command and completer methods inside of the object. Similar
	 * to Bukkit's registerEvents method.
	 * 
	 * @param obj
	 *            The object to register the commands of
	 */
	public void registerCommands(Object obj)
	{
		for (Method m : obj.getClass().getMethods())
		{
			if (m.getAnnotation(Command.class) != null)
			{
				Command command = m.getAnnotation(Command.class);
				if (m.getParameterTypes().length > 1
						|| m.getParameterTypes()[0] != CommandArgs.class)
				{
					System.out.println("Unable to register command "
							+ m.getName() + ". Unexpected method arguments");
					continue;
				}
				registerCommand(command, command.command(), m, obj);
				for (String alias : command.aliases())
				{
					registerCommand(command, alias, m, obj);
				}
			} else if (m.getAnnotation(Completer.class) != null)
			{
				Completer comp = m.getAnnotation(Completer.class);
				if (m.getParameterTypes().length > 1
						|| m.getParameterTypes().length == 0
						|| m.getParameterTypes()[0] != CommandArgs.class)
				{
					System.out.println("Unable to register tab completer "
							+ m.getName() + ". Unexpected method arguments");
					continue;
				}
				if (m.getReturnType() != List.class)
				{
					System.out.println("Unable to register tab completer "
							+ m.getName() + ". Unexpected return type");
					continue;
				}
				registerCompleter(comp.command(), m, obj);
				for (String alias : comp.aliases())
				{
					registerCompleter(alias, m, obj);
				}
			}
		}
	}

	/**
	 * Dynamically registers all commands in project. Credit put where credit
	 * due :)
	 * 
	 * @author Not2EXceL
	 * @see Not2EXceL's CommandAPI
	 */
	public void registerCommands()
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
				if (CommandListener.class.isAssignableFrom(c)
						&& !c.isInterface() && !c.isEnum() && !c.isAnnotation())
				{
					if (JavaPlugin.class.isAssignableFrom(c))
					{
						if (plugin.getClass().equals(c))
						{
							plugin.getLogger().log(Level.INFO,
									"Searching class: " + c.getSimpleName());
							registerCommands(plugin);
						}
					} else
					{
						plugin.getLogger().log(Level.INFO,
								"Searching class: " + c.getSimpleName());
						registerCommands(c.newInstance());
					}
				}
			} catch (InstantiationException e)
			{
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");

				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				plugin.getLogger().log(
						Level.INFO,
						c.getSimpleName()
								+ " does not use the default constructor");

				e.printStackTrace();
			}
		}
	}

	/**
	 * Registers all the commands under the plugin's help
	 */
	public void registerHelp()
	{
		Set<HelpTopic> help = new TreeSet<HelpTopic>(
				HelpTopicComparator.helpTopicComparatorInstance());
		for (String s : commandMap.keySet())
		{
			if (!s.contains("."))
			{
				org.bukkit.command.Command cmd = map.getCommand(s);
				HelpTopic topic = new GenericCommandHelpTopic(cmd);
				help.add(topic);
			}
		}
		IndexHelpTopic topic = new IndexHelpTopic(plugin.getName(),
				"All commands for " + plugin.getName(), null, help,
				"Below is a list of all " + plugin.getName() + " commands:");
		Bukkit.getServer().getHelpMap().addTopic(topic);
	}

	private void registerCommand(Command command, String label, Method m,
			Object obj)
	{
		Entry<Method, Object> entry = new AbstractMap.SimpleEntry<Method, Object>(
				m, obj);
		commandMap.put(label.toLowerCase(), entry);
		String cmdLabel = label.replace(".", ",").split(",")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null)
		{
			org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, plugin);
			map.register(plugin.getName(), cmd);
		}
		if (!command.description()
				.equalsIgnoreCase("Much Description. So Wow.")
				&& cmdLabel == label)
		{
			map.getCommand(cmdLabel).setDescription(command.description());
		}
		if (!command.usage().equalsIgnoreCase("Much Usage. So Wow.")
				&& cmdLabel == label)
		{
			map.getCommand(cmdLabel).setUsage(command.usage());
		}
	}

	private void registerCompleter(String label, Method m, Object obj)
	{
		String cmdLabel = label.replace(".", ",").split(",")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null)
		{
			org.bukkit.command.Command command = new BukkitCommand(cmdLabel,
					plugin);
			map.register(plugin.getName(), command);
		}
		if (map.getCommand(cmdLabel) instanceof BukkitCommand)
		{
			BukkitCommand command = (BukkitCommand) map.getCommand(cmdLabel);
			if (command.completer == null)
			{
				command.completer = new BukkitCompleter();
			}
			command.completer.addCompleter(label, m, obj);
		} else if (map.getCommand(cmdLabel) instanceof PluginCommand)
		{
			try
			{
				Object command = map.getCommand(cmdLabel);
				Field field = command.getClass().getDeclaredField("completer");
				field.setAccessible(true);
				if (field.get(command) == null)
				{
					BukkitCompleter completer = new BukkitCompleter();
					completer.addCompleter(label, m, obj);
					field.set(command, completer);
				} else if (field.get(command) instanceof BukkitCompleter)
				{
					BukkitCompleter completer = (BukkitCompleter) field
							.get(command);
					completer.addCompleter(label, m, obj);
				} else
				{
					System.out
							.println("Unable to register tab completer "
									+ m.getName()
									+ ". A tab completer is already registered for that command!");
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void defaultCommand(CommandArgs args)
	{
		args.getSender().sendMessage(
				args.getLabel() + " is not handled! Oh noes!");
	}
}