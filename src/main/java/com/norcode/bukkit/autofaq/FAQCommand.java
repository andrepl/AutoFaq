package com.norcode.bukkit.autofaq;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FAQCommand implements TabExecutor {

    AutoFaq plugin;

    public FAQCommand(AutoFaq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] params) {
        LinkedList<String> args = new LinkedList<String>(Arrays.asList(params));
        if (args.size() == 0) {
            return false;
        }
        CommandSender targetPlayer = sender;
        if (args.size() == 2) {
            if (!sender.hasPermission("autofaq.use.others")) {
                sender.sendMessage("You don't have permission to send FAQ Responses to other players.");
                return true;
            }
            List<Player> matches = plugin.getServer().matchPlayer(args.peek());
            if (matches.size() != 1) {
                sender.sendMessage("unknown player: " + args.pop());
                return true;
            }
            args.pop();
            targetPlayer = matches.get(0);
        }

        FAQ faq = plugin.getPersistence().getFaq(args.peekFirst());
        if (faq == null) {
            sender.sendMessage("unknown FAQ: " + args.peekFirst());
            return true;
        }
        targetPlayer.sendMessage(ChatColor.AQUA + "[AutoFAQ] " + ChatColor.WHITE + faq.getResponse());
        if (targetPlayer.getName() != sender.getName()) {
            sender.sendMessage("sent FAQ:" + faq.getName() + " to " + targetPlayer.getName());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
