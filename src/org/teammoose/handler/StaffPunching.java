/*
 * Author: 598Johnn897
 * 
 * Date: Jan 1, 2015
 * Package: org.teammoose.handler
 */
package org.teammoose.handler;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.teammoose.util.ColorUtil;
import org.teammoose.util.TagUtil;

/**
 * TODO make method cleaner
 * @author 598Johnn897
 * @since
 */
public class StaffPunching implements Listener
{
	@EventHandler
	public void onPlayerClick(EntityDamageByEntityEvent e)
	{
		if (e.getDamager() != null && e.getEntity() != null
				&& !(e.getDamager() instanceof Player)
				&& !(e.getEntity() instanceof Player))
			return;

		final Player player = (Player) e.getDamager();
		Player playerPuawnched = (Player) e.getEntity();

		if (!hasCooldown(player))
		{
			e.setCancelled(true);

			playerPuawnched.getLocation().getBlock().getWorld()
					.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
			playerPuawnched.getLocation().getBlock().getWorld()
					.playEffect(player.getLocation(), Effect.PARTICLE_SMOKE, 5);

			playerPuawnched.setVelocity(new Vector(0, 10, 0));

			Bukkit.broadcastMessage(ColorUtil.formatString(
					"%s%s <gray>punched %s%s <gray>into the sky!",
					TagUtil.getPlayerPrefix(player), player.getName(),
					TagUtil.getPlayerPrefix(playerPuawnched),
					playerPuawnched.getName()));
			
			activateCooldown(player);
		} else
		{
			player.sendMessage(ColorUtil
					.formatColors("<red>Your punch is in cooldown!"));
		}

	}

	HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	final int seconds = 60;

	public boolean hasCooldown(Player player)
	{
		if (!cooldowns.containsKey(player.getName()))
			return false;
		if (cooldowns.get(player.getName()) < (System.currentTimeMillis() - seconds * 1000))
			return false;
		else
			return true;
	}

	public void activateCooldown(Player player)
	{
		cooldowns.put(player.getName(), System.currentTimeMillis());
	}
}
