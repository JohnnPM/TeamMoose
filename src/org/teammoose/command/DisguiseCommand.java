/*
 * Author: 598Johnn897
 * 
 * Date: Dec 31, 2014
 * Package: org.teammoose.command
 */
package org.teammoose.command;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.teammoose.command.framework.Command;
import org.teammoose.command.framework.CommandArgs;
import org.teammoose.command.framework.CommandListener;
import org.teammoose.disguises.Disguise;
import org.teammoose.disguises.Disguise.DisguiseType;
import org.teammoose.lib.References;
import org.teammoose.util.ColorUtil;

/**
 * 
 * @author 598Johnn897
 * @since
 */
public class DisguiseCommand implements CommandListener
{
	public HashMap<String, Disguise> disguises = new HashMap<String, Disguise>();
	
	@Command(command = "disguise", permission = "tm.disguise", aliases = {"d", "becomenotamoosebutclose"})
	public void disguise(CommandArgs info) 
	{
		if (!info.isPlayer())
		{
			info.getSender().sendMessage("This is a player only command!");
			return;
		}
		else
		{
			Player player = info.getPlayer();
			if (info.getArgs().length == 0)
			{
				player.sendMessage(ColorUtil.formatColors("<red>Usage: /disguise <mob> [player]"));
			}
			else if (info.getArgs().length == 1)
			{
				DisguiseType d = DisguiseType.valueOf(info.getArgs()[0].toUpperCase());
				Disguise disguise = new Disguise(d, player.getName());
				disguise.changeDisguise(d);
				player.sendMessage(ColorUtil.formatString(
						"%s<green>You have been disguised as: %s", 
						References.SUCCESS_PREFIX, 
						d.toString()));

			}
		}
	}
}
