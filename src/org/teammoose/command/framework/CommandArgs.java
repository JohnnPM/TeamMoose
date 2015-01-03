package org.teammoose.command.framework;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Framework - CommandArgs <br>
 * This class is passed to the command methods and contains various
 * utilities as well as the command info.
 * 
 * @author minnymin3
 */
public class CommandArgs
{

	private final CommandSender sender;
	private final org.bukkit.command.Command command;
	private final String label, description, permission, no_permission,
			usage, name;
	private final String[] args;

	protected CommandArgs(CommandSender sender,
			org.bukkit.command.Command command, String label,
			String[] args, int subCommand)
	{
		String[] modArgs = new String[args.length - subCommand];
		System.arraycopy(args, 0 + subCommand, modArgs, 0, args.length
				- subCommand);

		StringBuilder buffer = new StringBuilder();
		buffer.append(label);
		for (int x = 0; x < subCommand; x++)
		{
			buffer.append(".").append(args[x]);
		}
		String cmdLabel = buffer.toString();
		this.sender = sender;
		this.command = command;
		this.label = cmdLabel;
		this.args = modArgs;
		this.description = command.getDescription();
		this.permission = command.getPermission();
		this.no_permission = command.getPermissionMessage();
		this.usage = command.getUsage();
		this.name = command.getName();
	}

	/**
	 * Gets the command sender
	 * 
	 * @return sender
	 */
	public CommandSender getSender()
	{
		return sender;
	}

	/**
	 * Gets the original command object
	 * 
	 * @return
	 */
	public org.bukkit.command.Command getCommand()
	{
		return command;
	}

	/**
	 * Gets the label including sub command labels of this command
	 * 
	 * @return Something like 'test.subcommand'
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Gets all the arguments after the command's label. ie. if the command
	 * label was test.subcommand and the arguments were subcommand foo foo,
	 * it would only return 'foo foo' because 'subcommand' is part of the
	 * command
	 * 
	 * @return
	 */
	public String[] getArgs()
	{
		return args;
	}

	/**
	 * The description of the command
	 * 
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Permission of the command
	 * 
	 * @return the permission
	 */
	public String getPermission()
	{
		return permission;
	}

	/**
	 * No Permission Message of command
	 * 
	 * @return the no_permission
	 */
	public String getNoPermission()
	{
		return no_permission;
	}

	/**
	 * Usage of the command
	 * 
	 * @return the usage
	 */
	public String getUsage()
	{
		return usage;
	}

	/**
	 * Name of command
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * True if is player command sender
	 * 
	 * @return
	 */
	public boolean isPlayer()
	{
		return sender instanceof Player;
	}

	/**
	 * @return Player if commandSender is a player else returns false
	 */
	public Player getPlayer()
	{
		if (sender instanceof Player)
		{
			return (Player) sender;
		} else
		{
			return null;
		}
	}
	
	public String getFinalArg(final int start)
	{
		final StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++)
		{
			if (i != start)
			{
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
}
