package org.kipperorigin.originapi.commands.parameters;

import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CommandParameterPersistentDataType extends CommandParameter {

    PersistentDataType dataType;
    public CommandParameterPersistentDataType(PersistentDataType dataType) {
        super("errorMessage");

    }

    @Override
    public boolean checkArgument(String argument) {
        return false;
    }

    @Override
    public Object asObject(String argument) {
        try {
            
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<String> getTabCompletes() {
        return null;
    }
}
