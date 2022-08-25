package com.eaea54.skill;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

//***NOT USED***
@Deprecated
public class JoinEvent implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ItemEvent I = new ItemEvent();
        HashMap<UUID, Integer> map = new HashMap<>(I.map);
        map.put(player.getUniqueId(),0);
    }
}
