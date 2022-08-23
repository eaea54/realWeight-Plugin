package com.eaea54.skill;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ItemEvent implements Listener {
    HashMap<UUID, Integer> map = new HashMap<>(); //몰라 어떻게 쓸지..
    FileConfiguration config = Skill.plugin.getConfig();

    public void slowness(Player player, int weight) {
        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if(weight>=6720) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 2));
        }
        else if(weight>=5130) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 1));
        }
        else if (weight>=3360 ) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 0));
        }
        else if (weight<=-2000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 180000, 3));
        }
        else if (weight<=-1000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 180000, 1));
        }
        else if (weight<=0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 180000, 0));
        }
        else {
            for(PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
    }
    public int weight(Player player) {

        ItemStack[] now = player.getInventory().getContents();
        int weight=1600;
        for(ItemStack item : player.getInventory().getContents()) {
            if (item!=null) {
                String Type = item.getType().toString();
                int stack = item.getAmount();
                weight += (config.getInt(Type)*stack);
            }
        }
        slowness(player,weight);
        return weight;
    }


    @EventHandler
    public void holdClock(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack[] inv = player.getInventory().getContents();
        int i = e.getNewSlot();
        if (Objects.equals(inv[i].getType().toString(), "CLOCK")) {

        }
    }
}
