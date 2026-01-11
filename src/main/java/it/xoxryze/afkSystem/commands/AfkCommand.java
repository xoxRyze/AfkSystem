package it.xoxryze.afkSystem.commands;

import it.xoxryze.afkSystem.AfkSystem;
import it.xoxryze.afkSystem.utils.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCommand implements CommandExecutor {

    private final AfkSystem module;
    private String NO_PERM;

    public AfkCommand(AfkSystem module) {
        this.module = module;
        NO_PERM = module.getConfig().getString("no-permission", "§cYou don't have the permission to do that.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!Permission.hasCMDPermission(player, "reload")) {
                    player.sendMessage(Component.text(NO_PERM));
                    return true;
                }
                module.reloadConfig();
                return true;
            }
            sendHelp(player);
            return true;
        }

        if (args.length != 2) {
            sendHelp(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (!Permission.hasCMDPermission(player, "info")) {
                player.sendMessage(NO_PERM);
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) return true;
            if (!target.isOnline()) {
               player.sendMessage(Component.text(String.format(" §cPlayer §n%s§c is not online!", target.getName())));
                return true;
            }
            Boolean afk = false;
            if (module.inAfk.contains(target)) {
                afk = true;
            }
            player.sendMessage(Component.text(String.format("\n §cᴀꜰᴋ ɪɴꜰᴏʀᴍᴀᴛɪᴏɴ\n§7 Player: §f%s\n§7 Afk: §f%s\n",
                    target.getName(), afk.toString()
                            .replace("true", "Yes")
                            .replace("false", "Nope"))));
            return true;
        }

        if (args[0].equalsIgnoreCase("change")) {
            if (!Permission.hasCMDPermission(player, "change")) {
                player.sendMessage(NO_PERM);
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) return true;
            if (!target.isOnline()) {
                player.sendMessage(Component.text(String.format(" §cPlayer §n%s§c is not online!", target.getName())));
                return true;
            }
            if (module.inAfk.contains(target)) {
                module.inAfk.remove(target);
                player.sendMessage(Component.text(String.format("\n §aYou removed §2%s§a from the afk mode.",
                        target.getName())));
                module.lastActivity.put(target.getUniqueId(), System.currentTimeMillis());
                player.setDisplayName(player.getName());
                return true;
            }
            module.inAfk.add(target);
            player.sendMessage(Component.text(String.format("\n §aYou add §2%s§a to the afk mode.",
                    target.getName())));
            player.setDisplayName(player.getName() + " §7[AFK]");
            return true;
        }
        sendHelp(player);
        return true;
    }

    private void sendHelp (Player player) {
        player.sendMessage(Component.empty());
        player.sendMessage(Component.text("§6§l Afk System"));
        player.sendMessage(Component.text("§e /afk change <player>").hoverEvent(HoverEvent.showText(
                Component.text("§7Put or remove a player from afk mode")
        )));
        player.sendMessage(Component.text(" §e/afk info <player>").hoverEvent(HoverEvent.showText(
                Component.text("§7View if a player is or isn't in afk mode")
        )));
        player.sendMessage(Component.text(" §e/afk reload").hoverEvent(HoverEvent.showText(
                Component.text("§7Reload the plugin config.yml file")
        )));
        player.sendMessage(Component.empty());
    }

}
