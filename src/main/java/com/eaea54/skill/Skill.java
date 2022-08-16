package com.eaea54.skill;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skill extends JavaPlugin {

    public static Skill plugin;
    @Override
    public void onEnable() {
        plugin =this;
        getLogger().info("[plugin start!]");
        Bukkit.getPluginManager().registerEvents(new ItemEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
