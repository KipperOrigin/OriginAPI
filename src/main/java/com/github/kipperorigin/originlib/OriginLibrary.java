package com.github.kipperorigin.originlib;

import com.github.kipperorigin.originlib.particles.ParticleEffectManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class OriginLibrary {

    private static Plugin plugin;
    private static ParticleEffectManager particleEffectManager;

    public static void initializeLibrary(Plugin p) {
        plugin = p;
    }

    public static ParticleEffectManager getParticleEffectManager() {
        return particleEffectManager;
    }
}
