package org.kipperorigin.originapi.commands.parameters;

import java.util.List;

public abstract class CommandParameter {

    private String errorMessage;
    public CommandParameter(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract boolean checkArgument(String argument);
    public abstract Object asObject(String argument);

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public abstract List<String> getTabCompletes();
}
