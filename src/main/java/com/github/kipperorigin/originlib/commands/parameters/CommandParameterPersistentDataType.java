package com.github.kipperorigin.originlib.commands.parameters;

import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandParameterPersistentDataType extends CommandParameter {

    PersistentDataType dataType;
    public CommandParameterPersistentDataType(PersistentDataType dataType) {
        super("errorMessage");

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
        for (Field field: PersistentDataType.class.getFields()) {
            if (PersistentDataType.class.isAssignableFrom(field.getType())) {
                try {
                    PersistentDataType type = (PersistentDataType) field.get(null);
                    tabCompletes.add(type.getComplexType().getTypeName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return tabCompletes;
    }

    public PersistentDataType getPersistentDataTypeObject(String dataType) {
        for (Field field: PersistentDataType.class.getFields()) {
            if (PersistentDataType.class.isAssignableFrom(field.getType())) {
                try {
                    PersistentDataType type = (PersistentDataType) field.get(null);
                    if (type.getComplexType().getTypeName().equalsIgnoreCase(dataType)) return type;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
