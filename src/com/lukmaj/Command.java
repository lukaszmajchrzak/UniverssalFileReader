package com.lukmaj;

public class Command {
    private final String command;
    private final String commandDescription;

    public Command(String command, String commandDescription) {
        this.command = command;
        this.commandDescription = commandDescription;
    }

    public String getCommand() {
        return command;
    }

    public String getCommandDescription() {
        return commandDescription;
    }
}
