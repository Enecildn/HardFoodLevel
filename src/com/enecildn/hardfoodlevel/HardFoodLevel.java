package com.enecildn.hardfoodlevel;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HardFoodLevel extends JavaPlugin implements Listener
{
	private HashMap<Player, Integer> PlayerFoodLevel = new HashMap<>();
	private HashMap<Player, Integer> PlayerTask = new HashMap<>();
	
	public void onEnable()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		PlayerFoodLevel.put(event.getEntity(), event.getEntity().getFoodLevel());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		PlayerTask.put(event.getPlayer(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				if (!event.getPlayer().isDead())
				{
					if (event.getPlayer().getFoodLevel() != PlayerFoodLevel.get(event.getPlayer()))
					{
						event.getPlayer().setFoodLevel(PlayerFoodLevel.get(event.getPlayer()));
					}
					else
					{
						PlayerFoodLevel.remove(event.getPlayer());
						Bukkit.getServer().getScheduler().cancelTask(PlayerTask.get(event.getPlayer()));
						PlayerTask.remove(event.getPlayer());
					}
				}
			}
		}, 0, 20));
	}
}
