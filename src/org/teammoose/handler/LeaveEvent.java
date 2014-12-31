/*
 * Author: 598Johnn897
 * 
 * Date: Dec 30, 2014
 * Package: org.teammoose.handler
 */
package org.teammoose.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.teammoose.util.ColorUtil;

/**
 * 
 * @author 598Johnn897
 * @since
 */
public class LeaveEvent implements Listener
{
	@EventHandler public void onLeave(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		event.setQuitMessage(ColorUtil.formatString("<yellow>%s left.", player.getName()));
	}
}
