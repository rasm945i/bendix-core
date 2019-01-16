package dk.rasmusbendix.bendixcore;

import java.util.HashSet;

public class CommandBuilder {

    private HashSet<SubCommand> subCommands;

    public CommandBuilder() {
        subCommands = new HashSet<>();
    }

}
