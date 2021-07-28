package io.github.jaronz.mwgamemodes.command;

import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import io.github.jaronz.jzlib.command.Command;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class BaseCommand extends Command {
    public BaseCommand(
        String name,
        String description,
        String[] aliases,
        String permission,
        CommandParameter[] commandParameters
    ){
        super(name, description);
        String usageMessage = "/" + name;
        for(CommandParameter commandParameter : commandParameters){
            usageMessage += " " + (commandParameter.optional ? "[" : "<") + commandParameter.name + ": " +
                commandParameter.type.name().toLowerCase() + (commandParameter.optional ? "]" : ">");
        }
        this.setUsage(usageMessage);
        this.setAliases(aliases);
        this.setCommandParameters(commandParameters);
        this.setPermission(permission);
        this.setPermissionMessage(TextFormat.RED + "You do not have permission to use this command!");
        this.setArgsRange(Arrays.stream(commandParameters)
            .filter(parameter -> !parameter.optional).collect(Collectors.toList()).size(), commandParameters.length);
    }
}
