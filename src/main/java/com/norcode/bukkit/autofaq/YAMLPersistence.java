package com.norcode.bukkit.autofaq;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 6/7/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class YAMLPersistence implements IPersistence {

    AutoFaq plugin;
    ConfigAccessor accessor;
    HashMap<String, FAQ> faqs = new HashMap<String, FAQ>();

    public YAMLPersistence(AutoFaq plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        this.accessor = new ConfigAccessor(this.plugin, "faqs.yml");
        this.accessor.getConfig();
        this.accessor.saveDefaultConfig();
    }

    @Override
    public void reload() {
        faqs.clear();
        this.accessor.reloadConfig();
        for (String key: this.accessor.getConfig().getKeys(false)) {
            FAQ faq = new FAQ();
            faq.setName(key.toLowerCase());
            faq.setPattern(this.accessor.getConfig().getString(key + ".pattern"));
            faq.setResponse(this.accessor.getConfig().getString(key + ".response"));
            faq.compile();
            this.faqs.put(key.toLowerCase(), faq);
        }
    }

    @Override
    synchronized public List<FAQ> getAll() {
        return new ArrayList<FAQ>(this.faqs.values());
    }

    @Override
    synchronized public void saveFaq(FAQ faq) {
        ConfigurationSection sect = this.accessor.getConfig().getConfigurationSection(faq.getName().toLowerCase());
        if (sect == null) {
            sect = this.accessor.getConfig().createSection(faq.getName().toLowerCase());
        }
        sect.set("pattern", faq.getPattern());
        sect.set("response", faq.getResponse());
    }

    @Override
    public synchronized FAQ getFaq(String name) {
        return this.faqs.get(name.toLowerCase());
    }

    @Override
    public void onDisable() {
        this.accessor.saveConfig();
    }
}
