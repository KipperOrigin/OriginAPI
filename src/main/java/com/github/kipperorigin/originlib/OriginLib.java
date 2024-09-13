package com.github.kipperorigin.originlib;

import com.github.kipperorigin.originlib.listeners.FlagDropEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OriginLib extends JavaPlugin {

    @Override
    public void onEnable() {
        OriginLibrary.initialize(this);
        Bukkit.getPluginManager().registerEvents(new FlagDropEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
