package io.github.jaronz.mwgamemodes.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerGameModeChangeEvent;
import cn.nukkit.utils.Config;
import io.github.jaronz.mwgamemodes.MWGamemodes;

import java.util.HashMap;
import java.util.Map;

public class PlayerGamemodeChangeListener implements Listener {
    @EventHandler
    public void onPlayerGamemodeChange(PlayerGameModeChangeEvent event){
        Config config = MWGamemodes.instance.getConfig();
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        Map<String, Map<String, Integer>> players = (Map<String, Map<String, Integer>>) config.get("players");
        Map<String, Integer> playerMap = players.getOrDefault(uuid, new HashMap());
        playerMap.put(player.getLevel().getName(), event.getNewGamemode());
        players.put(uuid, playerMap);
        config.set("players", players);
        config.save();
    }
}
