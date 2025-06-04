package io.github.irishgreencitrus.inactivitystop;

import org.bukkit.plugin.java.JavaPlugin;

public final class InactivityStop extends JavaPlugin {
    boolean shouldLog = true;
    int sleepForMinutes = 0;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        sleepForMinutes = getConfig().getInt("sleep-for-minutes");
        shouldLog = getConfig().getBoolean("log");
        getServer().getPluginManager().registerEvents(new InactivityListener(this), this);

    }

    public void debugLog(String s) {
        if (!shouldLog) return;
        getLogger().info(s);
    }
}
