package com.mengcraft.dexp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

public class DEMain extends JavaPlugin implements Listener {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        DEListener listener = new DEListener();
        getServer().getPluginManager().registerEvents(listener, plugin);
        boolean isBroadcast = getConfig().getBoolean("config.broadcast.use");
        if (isBroadcast) {
            DEThread deThread = new DEThread();
            deThread.start();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "梦梦家高性能服务器出租");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "淘宝店 http://shop105595113.taobao.com");
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "梦梦家高性能服务器出租");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "淘宝店 http://shop105595113.taobao.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
        return true;
    }


}
