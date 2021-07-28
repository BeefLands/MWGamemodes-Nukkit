package io.github.jaronz.mwgamemodes;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.TextFormat;
import io.github.jaronz.mwgamemodes.command.WorldGamemodeCommand;
import io.github.jaronz.mwgamemodes.listener.EntityLevelChangeListener;
import io.github.jaronz.mwgamemodes.listener.PlayerGamemodeChangeListener;

import java.util.ArrayList;

public class MWGamemodes extends PluginBase {
    private PluginLogger logger;
    public static MWGamemodes instance;

    @Override
    public void onLoad() {
        logger = this.getLogger();
        logger.info(TextFormat.WHITE + this.getName() + " has been loaded!");
    }

    @Override
    public void onEnable() {
        logger.info(TextFormat.DARK_GREEN + this.getName() + " has been enabled!");
        instance = this;

        saveDefaultConfig();

        Server server = this.getServer();

        server.getCommandMap().registerAll(this.getName().toLowerCase(), new ArrayList<Command>(){{
            add(new WorldGamemodeCommand());
        }});

        PluginManager pluginManager = server.getPluginManager();
        pluginManager.registerEvents(new EntityLevelChangeListener(), this);
        pluginManager.registerEvents(new PlayerGamemodeChangeListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info(TextFormat.DARK_RED + this.getName() + " has been disabled!");
    }
}
