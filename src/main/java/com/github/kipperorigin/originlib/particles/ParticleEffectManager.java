package com.github.kipperorigin.originlib.particles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticleEffectManager {
    private final Gson gson;
    private Map<String, ParticleEffect> particleEffects;
    private Plugin plugin;

    public ParticleEffectManager(Plugin plugin) {
        this.plugin = plugin;
        this.particleEffects = new HashMap<>();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ParticleEffect.class, new ParticleEffectSerialization.ParticleEffectSerializer())
                .registerTypeAdapter(ParticleEffect.class, new ParticleEffectSerialization.ParticleEffectDeserializer())
                .setPrettyPrinting()
                .create();
    }

    public void addParticleEffect(ParticleEffect effect) {
        particleEffects.put(effect.getParticleEffectName(), effect);
    }

    public Map<String, ParticleEffect> getParticleEffects() {
        return particleEffects;
    }

    public void saveToJson() {
        File effectsFolder = new File(plugin.getDataFolder(), "effects");
        if (!effectsFolder.exists()) {
            effectsFolder.mkdirs();
        }

        for (ParticleEffect effect : particleEffects.values()) {
            String fileName = effect.getParticleEffectName() + ".json";
            File file = new File(effectsFolder, fileName);
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(effect, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFromJson() {
        File effectsFolder = new File(plugin.getDataFolder(), "effects");
        if (!effectsFolder.exists() || !effectsFolder.isDirectory()) {
            particleEffects = new HashMap<>();
            return;
        }

        File[] jsonFiles = effectsFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (jsonFiles == null) {
            particleEffects = new HashMap<>();
            return;
        }

        particleEffects = new HashMap<>();
        for (File file : jsonFiles) {
            try (Reader reader = new FileReader(file)) {
                ParticleEffect effect = gson.fromJson(reader, ParticleEffect.class);
                particleEffects.put(effect.getParticleEffectName(), effect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playParticleEffects(Entity enity, String... effectStrings) {
        List<ParticleEffect> effects = new ArrayList<>();
        for (String string: effectStrings) {
            if (particleEffects.keySet().stream().anyMatch(string::equalsIgnoreCase)) effects.add(particleEffects.get(string));
        }
        playParticleEffects(enity, effects.toArray(new ParticleEffect[0]));
    }

    public void playParticleEffects(Entity entity, ParticleEffect... effects) {
        List<ActiveParticleEffect> activeEffects = new ArrayList<>();
        for (ParticleEffect effect : effects) {
            activeEffects.add(new ActiveParticleEffect(effect));
        }
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Location entityLocation = entity.getLocation();

            for (ActiveParticleEffect effect : activeEffects) {

                List<Vector> currentLocations = effect.getCurrentLocations();
                for (Vector vec : currentLocations) {
                    Location particleLoc = entityLocation.clone().add(vec);
                    spawnParticle(effect.getBaseEffect(), particleLoc);
                }

                effect.nextStep();

                if (effect.getBaseEffect().getBehavior() == ParticleEffectBehavior.END &&
                        effect.getCurrentRepetition() >= effect.getBaseEffect().getRepetitionsBeforeBehavior()) {
                    activeEffects.remove(effect);
                }
            }

            if (activeEffects.isEmpty()) {
                //Bukkit.getScheduler().cancelTask();
            }
        }, effects[0].getStartDelay(), effects[0].getDelayBetweenEmits());
    }

    private void spawnParticle(ParticleEffect effect, Location particleLoc) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);

        packet.getNewParticles().write(0, effect.getWrappedParticle());
        packet.getDoubles()
                .write(0, particleLoc.getX())
                .write(1, particleLoc.getY())
                .write(2, particleLoc.getZ());
        packet.getFloat()
                .write(0, effect.getOffsetX())
                .write(1, effect.getOffsetY())
                .write(2, effect.getOffsetZ());
        packet.getIntegers().write(0, effect.getParticleCount());
        packet.getBooleans().write(0, effect.isLongDistance());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld().equals(particleLoc.getWorld()) && onlinePlayer.getLocation().distance(particleLoc) <= 50) {
                try {
                    protocolManager.sendServerPacket(onlinePlayer, packet);
                } catch (Exception e) {
                    plugin.getLogger().warning("Failed to send particle packet to player " + onlinePlayer.getName() + ". Error: " + e.getMessage());
                }
            }
        }
    }
}