package me.heroostech.geyserutils.minestom.listeners;

import net.minestom.server.event.player.PlayerSpawnEvent;

public final class PlayerSpawnListener {
    public static void listener(PlayerSpawnEvent event) {
        if(event.isFirstSpawn())
            event.getPlayer().sendPluginMessage("geyserutils:join", new byte[0]);
    }
}
