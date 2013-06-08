package com.norcode.bukkit.autofaq;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.LinkedList;
import java.util.List;

public class AutoFAQCommand implements TabExecutor {

    AutoFaq plugin;

    public AutoFAQCommand(AutoFaq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getPersistence().reload();
            sender.sendMessage(ChatColor.AQUA + "[AutoFAQ] Config Reloaded");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> results = new LinkedList<String>();
        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase())) {
                results.add("reload");
            }
        }
        return results;
    }
}
