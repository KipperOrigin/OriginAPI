package org.kipperorigin.originapi.commands.parameters;

import org.bukkit.NamespacedKey;

import java.util.Collections;
import java.util.List;

public class CommandParameterNamespacedKey extends CommandParameter {

    String key;
    public CommandParameterNamespacedKey() {
        super("must be a NamespacedKey! NamespacedKeys can only contain a-z, 0-9, \"/._-\"!");
    }

    public CommandParameterNamespacedKey(String key) {
        super("must be a NamespacedKey! NamespacedKeys can only contain a-z, 0-9, \"/._-\"!");
        this.key = key;
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            NamespacedKey.minecraft(argument.toLowerCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            if (key != null) return new NamespacedKey(key, argument);
            else return NamespacedKey.minecraft(argument.toLowerCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{NamespacedKey}");
    }
}
