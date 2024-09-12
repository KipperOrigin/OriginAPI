package com.github.kipperorigin.originapi.commands.parameters;

import java.util.Collections;
import java.util.List;

public class CommandParameterDouble extends CommandParameter{
    public CommandParameterDouble() {
        super("must be a double!");
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            Double.parseDouble(argument);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return Double.parseDouble(argument);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{double}");
    }
}
