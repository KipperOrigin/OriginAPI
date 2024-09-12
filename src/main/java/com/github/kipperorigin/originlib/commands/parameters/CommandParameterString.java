package com.github.kipperorigin.originlib.commands.parameters;

import java.util.Collections;
import java.util.List;

public class CommandParameterString extends CommandParameter {

    public CommandParameterString() {
        super("must be a string!");
    }

    @Override
    public boolean checkArgument(String argument) {
        return true;
    }

    @Override
    public Object asObject(String argument) {
        return argument;
    }

    @Override
    public List<String> getTabCompletes() {
        return Collections.singletonList("{string}");
    }
}
