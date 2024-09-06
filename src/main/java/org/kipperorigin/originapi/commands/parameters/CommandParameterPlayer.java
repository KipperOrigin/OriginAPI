package org.kipperorigin.originapi.commands.parameters;

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
            if (player.getDisplayName().equalsIgnoreCase(argument)) return true;
        }
        return false;
    }

    @Override
    public Object asObject(String argument) {
        for (Player player: playerList) {
            if (player.getName().equalsIgnoreCase(argument)) return player;
        }
        return null;
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
