package org.kipperorigin.originapi.commands.parameters;

import java.util.Collections;
import java.util.List;

public class CommandParameterInteger extends CommandParameter {

    public CommandParameterInteger() {
        super("must be an integer!");
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{integer}");
    }
}
