package com.github.kipperorigin.originlib.commands.parameters;

import org.bukkit.Keyed;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CommandParameterKeyed extends CommandParameter {
    private final Class<? extends Keyed> keyedClass;

    public CommandParameterKeyed(Class<? extends Keyed> keyedClass) {
        super(keyedClass.getSimpleName().toLowerCase());
        this.keyedClass = keyedClass;
    }

    @Override
    public boolean checkArgument(String argument) {
        return getKeyedObject(argument) != null;
    }

    @Override
    public Object asObject(String argument) {
        return getKeyedObject(argument);
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> tabCompletes = new ArrayList<>();
        for (Field field : keyedClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Keyed.class.isAssignableFrom(field.getType())) {
                try {
                    Keyed keyedObject = (Keyed) field.get(null);
                    tabCompletes.add(keyedObject.getKey().getKey());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return tabCompletes;
    }

    private Keyed getKeyedObject(String key) {
        for (Field field : keyedClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Keyed.class.isAssignableFrom(field.getType())) {
                try {
                    Keyed keyedObject = (Keyed) field.get(null);
                    if (keyedObject.getKey().getKey().equalsIgnoreCase(key)) {
                        return keyedObject;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}