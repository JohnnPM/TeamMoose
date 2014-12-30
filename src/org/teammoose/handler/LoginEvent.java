/*
 * Author: 598Johnn897
 * 
 * Date: Dec 29, 2014
 * Package: org.teammoose.handler
 */
package org.teammoose.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 
 * @author 598Johnn897
 * @since
 */
public class LoginEvent implements Listener
{
	@EventHandler public void onLogin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
	}
}
