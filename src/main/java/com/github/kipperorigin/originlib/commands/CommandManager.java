package com.github.kipperorigin.originlib.commands;


import com.github.kipperorigin.originlib.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandManager implements TabCompleter {

    private JavaPlugin plugin;
    private List<BaseCommand> commands;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        commands = new ArrayList<>();
    }

    public abstract void registerCommands();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletes = new ArrayList<>();

        // Sort commands by length (descending) to prioritize longer, more specific commands
        List<BaseCommand> sortedCommands = new ArrayList<>(commands);
        sortedCommands.sort((c1, c2) -> c2.getCommand().split(" ").length - c1.getCommand().split(" ").length);

        for (BaseCommand command : sortedCommands) {
            String[] tabs = command.getCommand().split(" ");

            if (matchesArgs(tabs, args)) {
                if (args.length <= tabs.length) {
                    // If we're still within the main command parts
                    tabCompletes.add(tabs[args.length - 1]);
                    //return tabCompletes; // Return immediately to prioritize the longer command
                } else {
                    // We've exhausted the main command parts, check for additional tab completes
                    List<List<String>> additionalCompletes = command.getTabCompletes();
                    int additionalIndex = args.length - tabs.length - 1;
                    if (additionalIndex >= 0 && additionalIndex < additionalCompletes.size()) {
                        List<String> currentAdditionalCompletes = additionalCompletes.get(additionalIndex);
                        for (String complete : currentAdditionalCompletes) {
                            if (args[args.length - 1].isEmpty() || complete.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                                tabCompletes.add(complete);
                            }
                        }
                        return tabCompletes; // Return immediately after adding additional completes
                    }
                }
            }
        }

        return tabCompletes;
    }

    public void executeCommand(CommandSender sender, String[] args) {
        BaseCommand calledCommand = getCalledCommand(args);
        if (calledCommand != null) {
            String[] passedArgs = Arrays.copyOfRange(args,calledCommand.getCommand().split(" ").length,args.length);
            calledCommand.execute(sender,passedArgs);
        } else {
            MessageUtil.sendCustomMessage(sender, "&cCommand does not exist!");
        }

        getCalledCommand(args);
    }

    public BaseCommand getCalledCommand(String[] args) {
        BaseCommand calledCommand = null;
        for (BaseCommand command:commands) {
            String[] arguments = command.getCommand().split(" ");
            int commandLength = arguments.length; //Gets length of command
            if (args.length < commandLength) continue; //Skips to next loop if length is not long enough
            for (int i = 0; i < commandLength; i++) {
                if (arguments[i].equalsIgnoreCase(args[i])) {
                    if (i == commandLength - 1) {
                        if (calledCommand == null) {
                            calledCommand = command;
                        } else if (calledCommand.getCommand().split(" ").length < commandLength) { //This should choose the most "accurate" command. The longest that matches.
                            calledCommand = command;
                        }
                    }
                } else break;
            }
        }
        return calledCommand;
    }
    // Helper method to check if the command matches the current args
    private boolean matchesArgs(String[] tabs, String[] args) {
        int minLength = Math.min(tabs.length, args.length - 1);
        for (int i = 0; i < minLength; i++) {
            if (!tabs[i].equalsIgnoreCase(args[i])) {
                return false;
            }
        }
        return true;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }
}
