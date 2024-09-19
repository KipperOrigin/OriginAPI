package com.github.kipperorigin.originlib.commands.parameters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandParameterPlayer extends CommandParameter {

    List<Player> playerList;
    public CommandParameterPlayer() {
        super("Must be an active player!");
        playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    @Override
    public boolean checkArgument(String argument) {
        for (Player player: playerList) {
            if (player.getDisplayName().equalsIgnoreCase(argument) || player.getName().equalsIgnoreCase(argument)) return true;
        }
        return false;
    }

    @Override
    public Object asObject(String argument) {
        //Backup will be used if no name matches, it will use display name. I.E. Bob is renamed Joe,
        //it will choose Bob if Joe is searched and there is no Joe online.
        Player backup = null;
        for (Player player: playerList) {
            if (player.getName().equalsIgnoreCase(argument)) return player;
            if (player.getDisplayName().equalsIgnoreCase(argument)) backup = player;
        }
        return backup;
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> playerNames = new ArrayList<>();
        for (Player player: playerList) {
            playerNames.add(player.getDisplayName());
        }
        return null;
    }
}
