package com.github.kipperorigin.originapi.commands.parameters;

public class CommandParameterInteger extends CommandParameter {

    public CommandParameterInteger() {
        super("must be an integer!","{integer}");
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
}
