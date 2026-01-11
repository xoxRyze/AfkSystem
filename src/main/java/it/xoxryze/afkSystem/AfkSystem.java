package it.xoxryze.afkSystem;

import it.xoxryze.afkSystem.commands.AfkCommand;
import it.xoxryze.afkSystem.commands.AfkTabCompleter;
import it.xoxryze.afkSystem.listeners.JoinListener;
import it.xoxryze.afkSystem.listeners.MoveListener;
import it.xoxryze.afkSystem.managers.AfkManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class AfkSystem extends JavaPlugin {
    public List<Player> inAfk = new ArrayList<>();
    public Map<UUID, Long> lastActivity = new HashMap<>();
    private AfkManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("AfkSystem has been enabled!");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        getCommand("afk").setExecutor(new AfkCommand(this));
        getCommand("afk").setTabCompleter(new AfkTabCompleter());
        manager = new AfkManager(this);
        manager.start();
    }

    @Override
    public void onDisable() {
        getLogger().info("AfkSystem has been disabled.");
    }
}
