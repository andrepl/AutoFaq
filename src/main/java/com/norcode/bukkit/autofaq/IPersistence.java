package com.norcode.bukkit.autofaq;

import java.util.List;

public interface IPersistence {
    public void onEnable();
    public void reload();
    public List<FAQ> getAll();
    public void saveFaq(FAQ faq);
    public FAQ getFaq(String name);
    public void onDisable();
}
