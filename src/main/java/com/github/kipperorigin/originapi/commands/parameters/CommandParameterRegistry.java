package com.github.kipperorigin.originapi.commands.parameters;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParameterRegistry extends CommandParameter {

    Registry registry;
    public CommandParameterRegistry(Registry registry, String title) {
        super("must belong to registry &4" + title + "&c!");
        this.registry = registry;
    }

    public CommandParameterRegistry(Registry registry) {
        super("must belong to registry!");
        this.registry = registry;
    }

    @Override
    public boolean checkArgument(String argument) {
        if (registry.get(NamespacedKey.minecraft(argument.toLowerCase())) == null) return false;
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return registry.get(NamespacedKey.minecraft(argument.toLowerCase()));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> tabCompletes = new ArrayList<>();
        for (Enchantment enchant : Registry.ENCHANTMENT.stream().collect(Collectors.toList())) {
            tabCompletes.add(enchant.getKey().toString().substring(10));
        }
        return tabCompletes;
    }
}
