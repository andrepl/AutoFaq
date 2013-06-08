package com.norcode.bukkit.autofaq;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    AutoFaq plugin;

    public ChatListener(AutoFaq plugin) {
        this.plugin = plugin;
    }

    private class Matcher implements Runnable {
        private String message;
        private String playerName;
        public Matcher(String playerName, String message) {
            this.playerName = playerName;
            this.message = message;
        }

        public void run() {
            for (FAQ faq: plugin.getPersistence().getAll()) {
                if (faq.compiledPattern.matcher(message).matches()) {
                    sendResponse(faq.getResponse());
                    break;
                }
            }
        }

        private void sendResponse(final String response) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getServer().getPlayer(playerName).sendMessage(ChatColor.AQUA + "[AutoFAQ Automated Response] " + ChatColor.WHITE + "@" + playerName + ChatColor.GRAY + " " + response);
                }
            },0);
        }
    }
    @EventHandler(ignoreCancelled=false, priority=EventPriority.MONITOR)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Matcher(event.getPlayer().getName(), event.getMessage()));
        return;
    }
}
