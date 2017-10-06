package com.github.games647.scoreboardstats.variables.defaults;

import com.github.games647.scoreboardstats.variables.ReplaceEvent;
import com.github.games647.scoreboardstats.variables.ReplaceManager;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.event.PlayerPointsChangeEvent;
import org.black_ixx.playerpoints.event.PlayerPointsResetEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a replacer for the Plugin PlayerPoints
 *
 * https://dev.bukkit.org/bukkit-plugins/playerpoints/
 */
public class PlayerPointsVariables extends DefaultReplaceAdapter<PlayerPoints> implements Listener {

    private final ReplaceManager replaceManager;

    public PlayerPointsVariables(ReplaceManager replaceManager) {
        super(JavaPlugin.getPlugin(PlayerPoints.class), "points");

        this.replaceManager = replaceManager;
    }

    @Override
    public void onReplace(Player player, String variable, ReplaceEvent replaceEvent) {
        int balance = getPlugin().getAPI().look(player.getUniqueId());
        replaceEvent.setScore(balance);
        replaceEvent.setConstant(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPointsReset(PlayerPointsResetEvent resetEvent) {
        Player player = Bukkit.getPlayer(resetEvent.getPlayerId());
        if (player != null) {
            replaceManager.updateScore(player, "points", 0);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPoints(PlayerPointsChangeEvent changeEvent) {
        Player player = Bukkit.getPlayer(changeEvent.getPlayerId());
        if (player != null) {
            int lastBal = getPlugin().getAPI().look(changeEvent.getPlayerId());
            replaceManager.updateScore(player, "points", lastBal + changeEvent.getChange());
        }
    }
}
