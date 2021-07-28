package io.github.jaronz.mwgamemodes.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import io.github.jaronz.mwgamemodes.MWGamemodes;

import java.util.HashMap;
import java.util.Map;

public class EntityLevelChangeListener implements Listener {
    @EventHandler
    public void onEntityLevelChange(EntityLevelChangeEvent event){
        MWGamemodes plugin = MWGamemodes.instance;
        Config config = plugin.getConfig();
        new NukkitRunnable() {
            @Override
            public void run() {
                Level target = event.getTarget();
                if(!(event.getEntity() instanceof Player)) return;
                Player player = (Player) event.getEntity();
                String worldName = target.getName();
                Map<String, Integer> worlds = (Map<String, Integer>) config.get("worlds");
                Map<String, Map<String, Integer>> players = (Map<String, Map<String, Integer>>) config.get("players");
                Map<String, Integer> playerMap = players.getOrDefault(player.getUniqueId().toString(), new HashMap());
                int gamemode;
                if(playerMap.containsKey(worldName)){
                    gamemode = playerMap.get(worldName);
                } else if(worlds.containsKey(worldName)){
                    gamemode = worlds.get(worldName);
                } else return;
                player.setGamemode(gamemode);
            }
        }.runTaskLater(plugin, 1);
    }
}
