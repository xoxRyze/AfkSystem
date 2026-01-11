package it.xoxryze.afkSystem.managers;

import it.xoxryze.afkSystem.AfkSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AfkManager {
    private final AfkSystem module;

    public AfkManager(AfkSystem module) {
        this.module = module;
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(module, () -> {
            long now = System.currentTimeMillis();

            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID uuid = p.getUniqueId();

                if (!module.lastActivity.containsKey(uuid)) continue;

                long last = module.lastActivity.get(uuid);

                Integer minutes = module.getConfig().getInt("max-afk-time", 20);
                if (now - last >= minutes * 60 * 1000 && !module.inAfk.contains(uuid)) {
                    module.inAfk.add(p);
                    p.sendMessage(Component.text(module.getConfig().getString("afk-message",
                            "\n §aᴀꜰᴋ \n §7You have entered afk mode. Move to get out.\n")));
                    p.setDisplayName(p.getName() + " §7[AFK]");
                }
            }
        }, 20L, 20L * 30);
    }

}
