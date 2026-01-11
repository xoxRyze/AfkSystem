package it.xoxryze.afkSystem.utils;

import org.bukkit.entity.Player;

public enum Permission {
    ;

    public static boolean hasCMDPermission(Player player, String str) {
        return player.hasPermission("afksystem.command." + str);
    }

}
