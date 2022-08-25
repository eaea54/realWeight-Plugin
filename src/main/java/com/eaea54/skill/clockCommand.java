package com.eaea54.skill;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class clockCommand implements CommandExecutor {
    HashSet<UUID> pClock = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            ItemEvent I = new ItemEvent();
            if(Objects.equals(args[0], "true")) {
                pClock.add(player.getUniqueId());
            }
            else {
                pClock.remove(player.getUniqueId());
            }
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Skill.plugin, new Runnable() {
                public void run() {
                    if (pClock.contains(player.getUniqueId())) {
                        ItemEvent I = new ItemEvent();
                        int weight=I.weight(player);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(I.setFormat(player,weight)));
                    }
                }
            }, 20L, 20*3);
            return true;
        }
        else if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Error. You cannot run this command from the console.");
            return false;
        }
        return false;
    }
}