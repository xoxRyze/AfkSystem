package it.xoxryze.afkSystem.listeners;

import it.xoxryze.afkSystem.AfkSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final AfkSystem module;

    public JoinListener(AfkSystem module) {
        this.module = module;
    }

    @EventHandler
    private void onPlayerJoin (PlayerJoinEvent e) {
        module.lastActivity.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        module.inAfk.remove(e.getPlayer());
        e.getPlayer().setDisplayName(e.getPlayer().getName());
    }

}
