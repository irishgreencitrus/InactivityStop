package io.github.irishgreencitrus.inactivitystop;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Optional;

public class InactivityListener implements Listener {
    private final InactivityStop plugin;
    private Optional<BukkitTask> quitTask = Optional.empty();
    public InactivityListener(InactivityStop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (plugin.getServer().getOnlinePlayers().size() <= 1) {
            plugin.debugLog("No players are online. The server will shutdown in "+plugin.sleepForMinutes+" minutes.");
            quitTask = Optional.of(Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.getServer().shutdown();
            }, (long) plugin.sleepForMinutes * 20 * 60));
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        quitTask.ifPresent((task) -> {
            task.cancel();
            plugin.debugLog("Server shutdown cancelled");
        });
        quitTask = Optional.empty();
    }
}
