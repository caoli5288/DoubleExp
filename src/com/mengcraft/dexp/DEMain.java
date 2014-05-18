package com.mengcraft.dexp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DEMain extends JavaPlugin implements Listener
{
	public static Plugin plugin = null;

	@Override
	public void onEnable()
	{
		plugin = this;
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		if (getConfig().getBoolean("config.broadcast.use")) {
			DEThread deThread = new DEThread();
			deThread.start();
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "梦梦家高性能服务器出租");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "淘宝店 http://shop105595113.taobao.com");
	}

	@Override
	public void onDisable()
	{
		saveConfig();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "梦梦家高性能服务器出租");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "淘宝店 http://shop105595113.taobao.com");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("dexp")) {
			if (sender.hasPermission("dexp.admin")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("setserver")) {
						float multiLevel = 2;
						long multiTime = 60;
						long currentTime = System.currentTimeMillis();
						if (args.length > 1) {
							multiLevel = Float.valueOf(args[1]);
							if (args.length > 2) {
								multiTime = Long.valueOf(args[2]);
							}
						}
						long setTime = multiTime * 60000 + currentTime;
						getConfig().set("server.exp.use", true);
						getConfig().set("server.exp.multi", multiLevel);
						getConfig().set("server.exp.time", setTime);
						String string = "为服务器设置 " + multiLevel + " 倍经验 " + multiTime + " 分钟成功 ";
						String string2 = "服务器" + multiLevel + "倍经验活动已开启! 持续时间" + multiTime + "分钟!";
						sender.sendMessage(ChatColor.GREEN + string);
						getServer().broadcastMessage(ChatColor.GREEN + string2);
						getServer().broadcastMessage(ChatColor.GREEN + string2);
						getServer().broadcastMessage(ChatColor.GREEN + string2);
					}
				}
			}
		}
		return true;
	}

	@EventHandler
	public void onServerExp(PlayerExpChangeEvent event)
	{
		if (getConfig().getBoolean("server.exp.use")) {
			long currentTime = System.currentTimeMillis();
			long setTime = getConfig().getLong("server.exp.time");
			if (currentTime <= setTime) {
				float expMulti = (float) getConfig().getDouble("server.exp.multi");
				int expBefore = event.getAmount();
				float expAfter = expBefore * expMulti;
				String string = expMulti + "倍经验活动中! 你获得 " + expAfter + " 点经验";
				event.setAmount(Math.round(expAfter));
				event.getPlayer().sendMessage(ChatColor.GREEN + string);
			}
			else {
				getConfig().set("server.exp", null);
				String string = "多倍经验活动已结束!";
				getServer().broadcastMessage(ChatColor.RED + string);
				getServer().broadcastMessage(ChatColor.RED + string);
				getServer().broadcastMessage(ChatColor.RED + string);
			}
		}
	}

	@EventHandler
	public void onVIPExp(PlayerExpChangeEvent event)
	{
		if (event.getPlayer().hasPermission("dexp.vip")) {
			int expBefore = event.getAmount();
			float expMulti = (float) getConfig().getDouble("config.vip.multi");
			float extAfter = expBefore * expMulti;
			event.setAmount(Math.round(extAfter));
		}

	}
}
