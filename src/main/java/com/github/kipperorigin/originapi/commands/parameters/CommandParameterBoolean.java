package com.github.kipperorigin.originapi.commands.parameters;

import java.util.Arrays;
import java.util.List;

public class CommandParameterBoolean extends CommandParameter {

    private String yes;
    private String no;

    public CommandParameterBoolean() {
        super("must be true or false!");
        yes = "true";
        no = "false";
    }

    public CommandParameterBoolean(String trueString, String falseString) {
        super("must be " + trueString + " or " + falseString + "!");
        yes = trueString;
        no = falseString;
    }

    @Override
    public boolean checkArgument(String argument) {
        return (argument.equalsIgnoreCase(yes) || argument.equalsIgnoreCase(no));
    }

    @Override
    public Object asObject(String argument) {
        if (argument.equalsIgnoreCase(yes)) return true;
        if (argument.equalsIgnoreCase(no)) return false;
        return null;
    }

    @Override
    public List<String> getTabCompletes() {
        return Arrays.asList(yes,no);
    }
}
