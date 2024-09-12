package com.github.kipperorigin.originapi.commands.parameters;

import java.util.Collections;
import java.util.List;

public class CommandParameterFloat extends CommandParameter {

    public CommandParameterFloat() {
        super("must be a float!");
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            Float.parseFloat(argument);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return Float.parseFloat(argument);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{float}");
    }
}
