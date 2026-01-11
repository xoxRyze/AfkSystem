package it.xoxryze.afkSystem.commands;

import it.xoxryze.afkSystem.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AfkTabCompleter implements TabCompleter {
    private List<String> completions = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return completions;
        }
        if (Permission.hasCMDPermission(player, "tabcomplete")) {

            if (args.length == 1) {
                completions.add("info");
                completions.add("change");
                completions.add("reload");
                return completions;
            }
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("info") ||
                        args[1].equalsIgnoreCase("change")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        completions.add(p.getName());
                    }
                    return completions;
                }
            }
            return completions;
        }
        return completions;
    }
}
