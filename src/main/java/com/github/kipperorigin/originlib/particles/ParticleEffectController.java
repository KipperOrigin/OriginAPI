package com.github.kipperorigin.originlib.particles;

import com.github.kipperorigin.originlib.particles.ParticleEffect;
import com.github.kipperorigin.originlib.particles.ParticleEffectManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleEffectController {
    private final Plugin plugin;
    private final ParticleEffectManager particleEffectManager;
    private final Map<UUID, BossBar> playerBossBars;

    public ParticleEffectController(Plugin plugin) {
        this.plugin = plugin;
        this.particleEffectManager = new ParticleEffectManager(plugin);
        this.playerBossBars = new HashMap<>();
    }

    public BukkitTask startEffectsWithBossBar(Entity entity, BarColor color, ParticleEffect... effects) {
        return new BukkitRunnable() {
            private int counter = 0;
            @Override
            public void run() {
                // Boss bar logic
                for (Player player : entity.getWorld().getPlayers()) {
                    if (player.getLocation().distance(entity.getLocation()) <= 3) {
                        updateBossBar(player, true);
                    } else {
                        updateBossBar(player, false);
                    }
                }

                // Increase boss bar progress every 5 counters
                if (counter % 5 == 0) {
                    playerBossBars.values().forEach(bossBar -> {
                        double newProgress = Math.min(1.0, bossBar.getProgress() + 0.05);
                        bossBar.setProgress(newProgress);
                    });
                }

                counter++;
            }

            @Override
            public void cancel() {
                super.cancel();
                // Remove all boss bars
                playerBossBars.values().forEach(BossBar::removeAll);
                playerBossBars.clear();
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void updateBossBar(Player player, boolean inRange) {
        UUID playerUUID = player.getUniqueId();
        if (inRange) {
            if (!playerBossBars.containsKey(playerUUID)) {
                BossBar bossBar = Bukkit.createBossBar("Boss Bar", BarColor.PURPLE, BarStyle.SOLID);
                bossBar.addPlayer(player);
                bossBar.setProgress(0.0);
                playerBossBars.put(playerUUID, bossBar);
            }
        } else {
            BossBar bossBar = playerBossBars.remove(playerUUID);
            if (bossBar != null) {
                bossBar.removePlayer(player);
            }
        }
    }
}