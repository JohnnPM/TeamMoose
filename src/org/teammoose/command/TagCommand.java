/*
 * Author: 598Johnn897
 * 
 * Date: Dec 29, 2014
 * Package: org.teammoose.command
 */
package org.teammoose.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.teammoose.command.CommandFramework.Command;
import org.teammoose.command.CommandFramework.CommandArgs;
import org.teammoose.command.CommandFramework.CommandListener;
import org.teammoose.lib.References;
import org.teammoose.util.ColorUtil;
import org.teammoose.util.TagUtil;

/**
 * 
 * @author 598Johnn897
 * @since 0.0.1-SNAPSHOT
 */
public class TagCommand implements CommandListener
{
	@Command(command = "tag", permission = "tm.tag", aliases =
	{ "nameplate", "nametag", "nameabovehead" })
	public void tag(CommandArgs info)
	{
		info.getSender().sendMessage(
				ColorUtil.formatColors("<red>Usage: /tag <set|remove>"));
	}

	@Command(command = "tag.set", permission = "tm.tag.set")
	public void settag(CommandArgs info)
	{
		info.getSender().sendMessage(
				ColorUtil.formatColors("<red>Usage: /tag set <prefix|suffix>"));
	}

	@Command(command = "tag.set.prefix", permission = "tm.tag.set.prefix")
	public void settagprefix(CommandArgs info)
	{
		if (info.getArgs().length == 0)
		{
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Usage: /tag set prefix <player> <prefix...>"));
		} else
		{
			if (Bukkit.getPlayer(info.getArgs()[0]) != null)
			{
				Player player = Bukkit.getPlayer(info.getArgs()[0]);
				TagUtil.setTagPrefix(player, info.getFinalArg(1));
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatString(
												"%s<green>Prefix \"%s\"<green> has been added to player: <aqua>%s<white>.",
												References.SUCCESS_PREFIX,
												info.getFinalArg(1),
												player.getName()));
			} else
			{
				info.getSender().sendMessage(
						ColorUtil.formatString(
								"%s<red>Player \"%s\" could not be found",
								References.ERROR_PREFIX, info.getArgs()[0]));
			}
		}
	}

	@Command(command = "tag.set.suffix", permission = "tm.tag.set.suffix")
	public void settagsuffix(CommandArgs info)
	{
		if (info.getArgs().length == 0)
		{
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Usage: /tag set suffix <player> <suffix...>"));
		} else
		{
			if (Bukkit.getPlayer(info.getArgs()[0]) != null)
			{
				Player player = Bukkit.getPlayer(info.getArgs()[0]);
				TagUtil.setTagSuffix(player,
						info.getFinalArg(1).replaceAll("_", " "));
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatString(
												"%s<green>Suffix \"%s\" <green>has been added to player: <aqua>%s<white>.",
												References.SUCCESS_PREFIX,
												info.getFinalArg(1),
												player.getName()));
			} else
			{
				info.getSender().sendMessage(
						ColorUtil.formatString(
								"%s<red>Player \"%s\" could not be found",
								References.ERROR_PREFIX, info.getArgs()[0]));
			}
		}
	}

	@Command(command = "tag.remove", permission = "tm.tag.remove")
	public void tagremove(CommandArgs info)
	{
		info.getSender()
				.sendMessage(
						ColorUtil
								.formatColors("<red>Usage: /tag remove <prefix|suffix|all> <player>"));
	}

	@Command(command = "tag.remove.prefix", permission = "tm.tag.remove.prefix")
	public void tagremoveprefix(CommandArgs info)
	{
		if (info.getArgs().length == 0)
		{
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Usage: /tag remove prefix <player>"));
		} else
		{
			if (Bukkit.getPlayer(info.getArgs()[0]) != null)
			{
				Player player = Bukkit.getPlayer(info.getArgs()[0]);
				TagUtil.removeTagPrefix(player);
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatString(
												"%s<green>Prefix has been removed from player: <aqua>%s<white>.",
												References.SUCCESS_PREFIX,
												player.getName()));
			} else
			{
				info.getSender().sendMessage(
						ColorUtil.formatString(
								"%s<red>Player \"%s\" could not be found",
								References.ERROR_PREFIX, info.getArgs()[0]));
			}
		}
	}

	@Command(command = "tag.remove.suffix", permission = "tm.tag.remove.suffix")
	public void tagremovesuffix(CommandArgs info)
	{
		if (info.getArgs().length == 0)
		{
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Usage: /tag remove suffix <player>"));
		} else
		{
			if (Bukkit.getPlayer(info.getArgs()[0]) != null)
			{
				Player player = Bukkit.getPlayer(info.getArgs()[0]);
				TagUtil.removeTagSuffix(player);
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatString(
												"%s<green>Suffix has been removed from player: <aqua>%s<white>.",
												References.SUCCESS_PREFIX,
												player.getName()));
			} else
			{
				info.getSender().sendMessage(
						ColorUtil.formatString(
								"%s<red>Player \"%s\" could not be found",
								References.ERROR_PREFIX, info.getArgs()[0]));
			}
		}
	}

	@Command(command = "tag.remove.all", permission = "tm.tag.remove.all")
	public void tagremoveall(CommandArgs info)
	{
		if (info.getArgs().length == 0)
		{
			info.getSender()
					.sendMessage(
							ColorUtil
									.formatColors("<red>Usage: /tag remove all <player>"));
		} else
		{
			if (Bukkit.getPlayer(info.getArgs()[0]) != null)
			{
				Player player = Bukkit.getPlayer(info.getArgs()[0]);
				TagUtil.removeAllTags(player);
				info.getSender()
						.sendMessage(
								ColorUtil
										.formatString(
												"%s<green>All tags have been removed from player: <aqua>%s<white>.",
												References.SUCCESS_PREFIX,
												player.getName()));
			} else
			{
				info.getSender().sendMessage(
						ColorUtil.formatString(
								"%s<red>Player \"%s\" could not be found",
								References.ERROR_PREFIX, info.getArgs()[0]));
			}
		}
	}

	// TODO vv Pretty buggy and doesn't work how intended vv
/*	@Completer(command = "tag")
	public List<String> tagCompleter(CommandArgs args)
	{
		List<String> list = new ArrayList<String>();
		list.add("set");
		list.add("remove");

		return list;
	}
	
	@Completer(command = "tag.set")
	public List<String> settagCompleter(CommandArgs args)
	{
		List<String> list = new ArrayList<String>();
		list.add("prefix");
		list.add("suffix");

		return list;
	}
	
	@Completer(command = "tag.remove")
	public List<String> removetagCompleter(CommandArgs args)
	{
		List<String> list = new ArrayList<String>();
		list.add("prefix");
		list.add("suffix");
		list.add("all");
		
		return list;
	}
	*/
}
