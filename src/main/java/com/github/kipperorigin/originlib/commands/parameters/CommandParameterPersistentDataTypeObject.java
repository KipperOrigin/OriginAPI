package com.github.kipperorigin.originlib.commands.parameters;

import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandParameterPersistentDataTypeObject extends CommandParameter {

    Set<PersistentDataType> typeSet;
    public CommandParameterPersistentDataTypeObject() {
        super("errorMessage");
        typeSet = new HashSet<>();
        typeSet.add(PersistentDataType.BOOLEAN);
        typeSet.add(PersistentDataType.BYTE);
        typeSet.add(PersistentDataType.DOUBLE);
        typeSet.add(PersistentDataType.FLOAT);
        typeSet.add(PersistentDataType.INTEGER);
        typeSet.add(PersistentDataType.LONG);
        typeSet.add(PersistentDataType.SHORT);
        typeSet.add(PersistentDataType.STRING);
    }

    @Override
    public boolean checkArgument(String argument) {
        return getPersistentDataTypeObject(argument) != null;
    }

    @Override
    public Object asObject(String argument) {
        return getPersistentDataTypeObject(argument);
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> tabCompletes = new ArrayList<>();
        for (PersistentDataType type: typeSet) {
            tabCompletes.add("{" + type.getComplexType().getSimpleName() + "}");
        }
        return tabCompletes;
    }

    public PersistentDataType getPersistentDataTypeObject(String dataType) {
        for (PersistentDataType type: typeSet) {
            if (type.getComplexType().getSimpleName().equalsIgnoreCase(dataType)) return type;
        }
        return null;
    }
}
