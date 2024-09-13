package com.github.kipperorigin.originlib;

import com.github.kipperorigin.originlib.particles.ParticleEffectManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OriginLibrary {
    private static OriginLibrary instance;
    private final ParticleEffectManager particleEffectManager;

    private OriginLibrary(JavaPlugin plugin) {
        this.particleEffectManager = new ParticleEffectManager(plugin);
    }

    public static void initialize(JavaPlugin plugin) {
        if (instance == null) {
            instance = new OriginLibrary(plugin);
        }
    }

    public static OriginLibrary getInstance() {
        if (instance == null) {
            throw new IllegalStateException("OriginLibrary has not been initialized. Call initialize() first.");
        }
        return instance;
    }

    public static ParticleEffectManager getParticleEffectManager() {
        return getInstance().particleEffectManager;
    }
}