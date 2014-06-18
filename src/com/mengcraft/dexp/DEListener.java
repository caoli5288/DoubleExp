package com.mengcraft.dexp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class DEListener implements Listener {

    FileConfiguration getConfig = DEMain.plugin.getConfig();

    @EventHandler
    public void dropEXP(PlayerDeathEvent event) {

        Player player = event.getEntity();
        boolean status = player.hasPermission("dexp.vip");
        if (status) {
            double multi = getConfig.getDouble("config.vip.multi");
            int getDroppedExp = event.getDroppedExp();
            event.setDroppedExp((int) (getDroppedExp / multi));
        }
        double v = DEMain.plugin.getConfig().getDouble("server.exp.multi");
        if (v > 0) {
            int getDroppedExp = event.getDroppedExp();
            event.setDroppedExp((int) (getDroppedExp / v));
        }
    }

    @EventHandler
    public void onServerExp(PlayerExpChangeEvent event) {
        if (getConfig.getBoolean("server.exp.use")) {
            long currentTime = System.currentTimeMillis();
            long setTime = getConfig.getLong("server.exp.time");
            if (currentTime <= setTime) {
                float expMulti = (float) getConfig.getDouble("server.exp.multi");
                int expBefore = event.getAmount();
                float expAfter = expBefore * expMulti;
                String string = expMulti + "倍经验活动中! 你获得 " + expAfter + " 点经验";
                event.setAmount(Math.round(expAfter));
                event.getPlayer().sendMessage(ChatColor.GREEN + string);
            } else {
                getConfig.set("server.exp", null);
                String string = "多倍经验活动已结束!";
                DEMain.plugin.getServer().broadcastMessage(ChatColor.RED + string);
                DEMain.plugin.getServer().broadcastMessage(ChatColor.RED + string);
                DEMain.plugin.getServer().broadcastMessage(ChatColor.RED + string);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onVIPExp(PlayerExpChangeEvent event) {
        if (event.getPlayer().hasPermission("dexp.vip")) {
            int expBefore = event.getAmount();
            double expMulti = getConfig.getDouble("config.vip.multi");
            int extAfter = (int) (expBefore * expMulti);
            event.setAmount(extAfter);
        }
    }

}
