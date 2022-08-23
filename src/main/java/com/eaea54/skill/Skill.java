package com.eaea54.skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skill extends JavaPlugin {

    public static Skill plugin;
    @Override
    public void onEnable() {
        plugin =this;
        getLogger().info("[plugin start!]");
        Bukkit.getPluginManager().registerEvents(new ItemEvent(), this);
        this.getServer().getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                for(Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    ItemEvent I = new ItemEvent();
                    int weight=I.weight(player);
                }
            }
        },20L,1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
