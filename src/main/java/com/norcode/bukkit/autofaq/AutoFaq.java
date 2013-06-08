package com.norcode.bukkit.autofaq;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AutoFaq extends JavaPlugin {

    private IPersistence persistence;

    @Override
    public void onDisable() {
        if (persistence != null) {
            persistence.onDisable();
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (getConfig().getBoolean("database", true)) {
            throw new UnsupportedOperationException("Database support doesn't exist yet.");
        } else {
            persistence = new YAMLPersistence(this);
        }
        persistence.onEnable();
        persistence.reload();
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginCommand("autofaq").setExecutor(new AutoFAQCommand(this));
        getServer().getPluginCommand("faq").setExecutor(new FAQCommand(this));
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(FAQ.class);
        return classes;
    }

    public IPersistence getPersistence() {
        return persistence;
    }
}
