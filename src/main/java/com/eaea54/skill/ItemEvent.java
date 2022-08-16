package com.eaea54.skill;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class ItemEvent implements Listener {
    HashMap<UUID, Integer> map = new HashMap<>();
    FileConfiguration config = Skill.plugin.getConfig();

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        map.put(player.getUniqueId(), 0);
    }

    public void slowness(Player player, int weight) {
        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if(weight>=5000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 2));
        }
        else if(weight>=3000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 1));
        }
        else if (weight>=2000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 0));
        }
        else if (weight<=-200) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 180000, 0));
        }
        else if (weight<=400) {
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
        int weight=1000;
        for(ItemStack item : player.getInventory().getContents()) {
            if (item!=null) {
                String Type = item.getType().toString();
                int stack = item.getAmount();
                weight += (config.getInt(Type)*stack);
            }
        }
        System.out.println(weight);
        slowness(player,weight);
        return weight;
    }


    @EventHandler
    public void getItem(PlayerAttemptPickupItemEvent e) {
        Player player = e.getPlayer();
        map.replace(player.getUniqueId(),weight(player)) ;
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        map.replace(player.getUniqueId(),weight(player)) ;
    }

    /*public int[] getData(String item, int stack, Player player) {
        int weight = (config.getInt(item)*stack);
        map.putIfAbsent(player.getUniqueId(), 0);
        int value = map.get(player.getUniqueId());
        return new int[] {value,weight};
    }*/

    /*

    @EventHandler
    public void  ItemMove(InventoryMoveItemEvent e) {
        Player player = e.
    }*/
}
