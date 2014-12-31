/*
 * Author: 598Johnn897
 * 
 * Date: Dec 29, 2014
 * Package: org.teammoose.handler
 */
package org.teammoose.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.teammoose.util.ColorUtil;
import org.teammoose.util.TagUtil;

/**
 * TODO Make a more dynamic system
 * @author 598Johnn897
 * @since
 */
public class ChatEvent implements Listener
{
	@EventHandler public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		event.setFormat(
				ColorUtil.formatString(
						"<gray>%s%s<white>: %s", 
						TagUtil.getPlayerPrefix(player),
						player.getName(),
						event.getMessage()));
	}
}
