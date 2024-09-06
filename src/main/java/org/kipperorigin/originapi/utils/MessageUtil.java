package org.kipperorigin.originapi.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtil {

    public static void sendCustomMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }

    public static void sendShortCommandMessage(CommandSender sender, String command, int length) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThe &6" + command + "&c command was too short the command must be at least &a" + length + "&c arguments long!"));
    }

    public static void sendLongCommandMessage(CommandSender sender, String command, int length) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThe &6" + command + "&c command was too long the command must be at no more than &a" + length + "&c arguments long!"));
    }

    public static void sendExactLengthMessage(CommandSender sender, String command, int length) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThe &6" + command + "&c command must have &a" + length + "&c arguments!"));
    }

    public static void sendTypeErrorMessage(CommandSender sender, String command, int argIndex, String type) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThe &e" + StringUtils.convertToOrdinal(argIndex) + "&c argument for command &6" + command + "&c must be: &a" + type + "&c!"));
    }

    public static void sendParameterErrorMessage(CommandSender sender, String command, int index, String error) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cError performing command &6" + '"' + command + '"' + "&c the &e" + StringUtils.convertToOrdinal(index) + "&c argument " + error));
    }

    public static void sendOptionalParameterErrorMessage(CommandSender sender, String command, String parameterTitle, String error) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cError performing command &6" + '"' + command + '"' + "&c the optional argument &e" + parameterTitle + " &c" + error));
    }

    public static void sendNoHeldItemMessage(CommandSender sender, String command) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou must be holding an item to perform the command &6\"" + command + "\""));
    }
    public static void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cHelp command To-Do"));
    }

    public static void sendPlayerMessage(CommandSender sender, String command) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou must be a player to perform the command &6\"" + command + "\""));
    }


}
