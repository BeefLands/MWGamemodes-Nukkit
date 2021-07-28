package io.github.jaronz.mwgamemodes.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.UpdatePlayerGameTypePacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import io.github.jaronz.mwgamemodes.MWGamemodes;

import java.util.ArrayList;
import java.util.Map;

public class WorldGamemodeCommand extends BaseCommand {
    public WorldGamemodeCommand(){
        super(
            "worldgamemode",
            "Set the gamemode of a world",
            new String[]{"wgm"},
            "mwgamemodes.command.gamemode",
            new CommandParameter[]{
                CommandParameter.newType("gamemode", CommandParamType.STRING),
                CommandParameter.newType("world", true, CommandParamType.STRING)
            }
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!this.testCommand(commandSender, commandArgs)) return false;
        if(!(commandSender instanceof Player) && commandArgs.length < 2){
            commandSender.sendMessage(TextFormat.RED + "Please specify a world when using this command in the console!");
            return false;
        }

        MWGamemodes plugin = MWGamemodes.instance;
        int gamemode;
        switch (commandArgs[0]){
            case "0":
            case "s":
            case "survival":
                gamemode = 0;
                break;
            case "1":
            case "c":
            case "creative":
                gamemode = 1;
                break;
            case "2":
            case "a":
            case "adventure":
                gamemode = 2;
                break;
            case "3":
            case "sp":
            case "spectator":
                gamemode = 3;
                break;
            default:
                commandSender.sendMessage(TextFormat.RED + "Invalid gamemode");
                return false;
        }

        Level world = commandArgs.length < 2 ? ((Player) commandSender).getLevel() :
            plugin.getServer().getLevelByName(commandArgs[1]);

        if(world == null) {
            commandSender.sendMessage(TextFormat.RED + "Unknown world " + commandArgs[1] + "!");
            return false;
        }

        // TODO save world gamemode
        Config config = plugin.getConfig();
        Map<String, Integer> worlds = (Map<String, Integer>) config.get("worlds");
        worlds.put(world.getName(), gamemode);
        config.set("worlds", worlds);
        config.save();

        UpdatePlayerGameTypePacket.GameType gamemodeEnum = UpdatePlayerGameTypePacket.GameType.from(gamemode);
        String gamemodeName = gamemodeEnum == UpdatePlayerGameTypePacket.GameType.SURVIVAL_VIEWER ?
            "spectator" : gamemodeEnum.name().toLowerCase();
        commandSender.sendMessage("Changed default gamemode of world " + world.getName() + " to " + gamemodeName);
        return true;
    }
}
