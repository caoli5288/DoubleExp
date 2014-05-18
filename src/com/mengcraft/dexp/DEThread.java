package com.mengcraft.dexp;

import org.bukkit.ChatColor;

public class DEThread extends Thread
{
	@Override
	public void run()
	{
		while (DEMain.plugin.isEnabled()) {
			try {
				sleep(DEMain.plugin.getConfig().getInt("config.broadcast.time") * 60000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!DEMain.plugin.isEnabled()) {
				return;
			}
			if (DEMain.plugin.getConfig().getBoolean("server.exp.use")) {
				long currentTime = System.currentTimeMillis();
				long setTime = DEMain.plugin.getConfig().getLong("server.exp.time");
				if (currentTime <= setTime) {
					long lastTime = (setTime - currentTime) / 60000;
					double expMulti = DEMain.plugin.getConfig().getDouble("server.exp.multi");
					String string = expMulti + "倍经验活动持续中! 剩余时间 " + Math.floor(lastTime) + " 分钟!";
					DEMain.plugin.getServer().broadcastMessage(ChatColor.GREEN + string);
					DEMain.plugin.getServer().broadcastMessage(ChatColor.GREEN + string);
					DEMain.plugin.getServer().broadcastMessage(ChatColor.GREEN + string);
				}
				else {
					DEMain.plugin.getConfig().set("server.exp", null);
				}
			}
		}
	}
}
