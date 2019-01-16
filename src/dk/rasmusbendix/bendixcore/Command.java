package dk.rasmusbendix.bendixcore;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Command {

    public Command(JavaPlugin plugin, String trigger, CommandExecutor executor) {
        plugin.getCommand(trigger).setExecutor(executor);
    }

}
