package com.github.kipperorigin.originlib;

import com.github.kipperorigin.originlib.listeners.FlagDropEvent;
import com.github.kipperorigin.originlib.particles.ParticleEffectManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OriginLib extends JavaPlugin {

    private static ParticleEffectManager particleEffectManager;
    private static OriginLib instance;
    @Override
    public void onEnable() {
        instance = this;
        OriginLibrary.initialize(this);
        Bukkit.getPluginManager().registerEvents(new FlagDropEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ParticleEffectManager getParticleEffectManager() {
        if (particleEffectManager == null) {
            particleEffectManager = new ParticleEffectManager(instance);
            particleEffectManager.loadFromJson();
        }
        return particleEffectManager;
    }
}
