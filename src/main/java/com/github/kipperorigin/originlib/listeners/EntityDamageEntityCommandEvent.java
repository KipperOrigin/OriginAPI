package com.github.kipperorigin.originlib.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class EntityDamageEntityCommandEvent {

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        Player damager = null;
        PersistentDataContainer container = null;
        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            Projectile projectile = (Projectile) event.getEntity();
            damager = (Player) projectile.getShooter();
            container = projectile.getPersistentDataContainer();
        } else if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
            container = damager.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        }

        if (damager == null || container.isEmpty()) return;

        List<String> commands = new ArrayList<>();

        for (NamespacedKey key : container.getKeys()) {

            if (!key.getNamespace().startsWith("command") ) continue;
            String command = container.get(key, PersistentDataType.STRING);
            if (command.contains("{player}") && event.getEntity() instanceof Player) {
                command.replace("{player}",((Player) event.getEntity()).getName());
            } else if (command.contains("{entity}")) {
                command.replace("{entity}","entity:" + String.valueOf(event.getEntity().getEntityId()));
            } else continue;
            commands.add(command);
        }

        for (String command: commands) {
            Bukkit.dispatchCommand(damager,command);
        }

    }

}
