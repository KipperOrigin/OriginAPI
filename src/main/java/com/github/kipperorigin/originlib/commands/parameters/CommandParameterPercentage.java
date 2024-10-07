package com.github.kipperorigin.originlib.commands.parameters;

import java.util.Collections;
import java.util.List;

public class CommandParameterPercentage extends CommandParameter{
    public CommandParameterPercentage() {
        super("must be a double!");
    }

    @Override
    public boolean checkArgument(String argument) {
        return (asObject(argument) != null);
    }

    @Override
    public Object asObject(String argument) {
        if (!(argument.charAt(argument.length()-1) == '%')) return false;
        String trimmed = argument.substring(0,argument.length()-1);

        try {
            return Double.parseDouble(trimmed)/100;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{double}");
    }
}
