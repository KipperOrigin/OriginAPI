package com.github.kipperorigin.originlib;

import org.bukkit.plugin.java.JavaPlugin;

public final class OriginLib extends JavaPlugin {

    @Override
    public void onEnable() {
        OriginLibrary.initializeLibrary(this);
        OriginLibrary.getParticleEffectManager().loadFromJson();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
