package org.kipperorigin.originapi.commands.parameters;

import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;

public class CommandParameterEquipmentSlotGoup extends CommandParameter {
    public CommandParameterEquipmentSlotGoup() {
        super("must be an equipment slot group!");
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            EquipmentSlotGroup.getByName(argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return EquipmentSlotGroup.getByName(argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return null;
    }
}
