package com.github.kipperorigin.originlib.listeners;

import com.github.kipperorigin.originlib.OriginLibrary;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class FlagDropEvent implements Listener {

    Plugin plugin;

    public FlagDropEvent(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onFlagDrop(PlayerDropItemEvent event) {
        if (!event.getItemDrop().getItemStack().getType().toString().toLowerCase().contains("banner")) return;
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey("originnbt","ctfflag"))) return;
        String particleName = meta.getPersistentDataContainer().get(new NamespacedKey("originnbt","ctfflag"), PersistentDataType.STRING);


        //Block block = null;
        Location loc = event.getPlayer().getLocation();

        /*while (block == null) {

        }
         */

        ArmorStand stand = (ArmorStand) event.getItemDrop().getWorld().spawnEntity(loc.add(.5,-.8,.75), EntityType.ARMOR_STAND);
        stand.getPersistentDataContainer().set(new NamespacedKey("originnbt","ctfflag"),PersistentDataType.STRING,particleName);
        stand.setVisible(false);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.getEquipment().setHelmet(item);
        stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.BODY, ArmorStand.LockType.ADDING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);

        OriginLibrary.getParticleEffectManager().playParticleEffects(stand,particleName);

        event.setCancelled(true);
        item.setAmount(0);
    }
}
