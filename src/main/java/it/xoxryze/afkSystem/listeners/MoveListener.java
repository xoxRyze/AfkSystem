package it.xoxryze.afkSystem.listeners;

import it.xoxryze.afkSystem.AfkSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private final AfkSystem module;

    public MoveListener(AfkSystem module) {
        this.module = module;
    }

    @EventHandler
    private void onPLayerMove (PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (module.inAfk.contains(player)) {
            module.inAfk.remove(player);
            module.lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
            player.setDisplayName(player.getName());
            return;
        }

    }
}
