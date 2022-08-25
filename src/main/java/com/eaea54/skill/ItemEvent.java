package com.eaea54.skill;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ItemEvent implements Listener {
    HashMap<UUID, Integer> map = new HashMap<>(); //몰라 어떻게 쓸지..
    FileConfiguration config = Skill.plugin.getConfig();
    HashSet<UUID> cMode = new HashSet<>();
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        map.put(player.getUniqueId(), 0);
        int weight=weight(player);
        map.put(player.getUniqueId(), weight);
    }

    public void slowness(Player player, int weight) {
        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if(weight>=7000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 2));
        }
        else if(weight>=5000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180000, 1));
        }
        else if (weight>=3000 ) {
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
        int weight=1000;
        for(ItemStack item : player.getInventory().getContents()) {
            if (item!=null) {
                String Type = item.getType().toString();
                int stack = item.getAmount();
                weight += (config.getInt(Type)*stack);
            }
        }
        ItemStack chest = player.getInventory().getChestplate();
        if(chest != null && chest.getType().toString() == "ELYTRA") {
            weight /= 2;
        }
        slowness(player,weight);
        return weight;
    }

    public String setFormat(Player player ,int weight) {
        String format = "§l";
        if(weight>=7000) {
            format += "§0";
        }
        else if(weight>=5000) {
            format += "§4";
        }
        else if (weight>=3000 ) {
            format += "§6";
        }
        else if (weight<=-2000) {
            format += "§1";
        }
        else if (weight<=-1000) {
            format += "§b";
        }
        else if (weight<=0) {
            format += "§f";
        }
        else {
            format += "§a";
        }
        if (cMode.contains(player.getUniqueId())) {
            if (weight>0) {
                format += "■■"+ "■".repeat((weight+1000)/2000);
            }
            else {
                format += "■";
            }
        }
        else {
            format += weight;
        }
        return format;
    }
    @EventHandler
    public void holdClock(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack[] inv = player.getInventory().getContents();
        int i = e.getNewSlot();
        if (inv[i]==null) {
            return;
        }
        if (Objects.equals(inv[i].getType().toString(), "CLOCK")) {
            ItemMeta m = inv[i].getItemMeta();
            int weight = weight(player);
            String format = setFormat(player,weight);
            m.setDisplayName(format);
            inv[i].setItemMeta(m);
        }
    }

    @EventHandler
    public void clockChange(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack i = e.getItem();
            if (i==null) {
                return;
            }
            if (Objects.equals(i.getType().toString(), "CLOCK")) {
                if (cMode.contains(player.getUniqueId())) {
                    cMode.remove(player.getUniqueId());
                }
                else {
                    cMode.add(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void elytraEquip(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            if (Objects.requireNonNull(e.getCurrentItem()).getType().toString()
                    .equals("ELYTRA")) {
                e.setCancelled(true);
            }
        }
    }
}
