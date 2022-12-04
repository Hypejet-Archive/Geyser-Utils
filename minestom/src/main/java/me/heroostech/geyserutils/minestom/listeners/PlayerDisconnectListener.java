package me.heroostech.geyserutils.minestom.listeners;

import me.heroostech.geyserutils.FloodgateApi;
import me.heroostech.geyserutils.minestom.GeyserUtils;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

public final class PlayerDisconnectListener {
    public static void listener(PlayerDisconnectEvent event) {
        Player player = event.getPlayer();
        if(FloodgateApi.getInstance().isFloodgatePlayer(player.getUuid()))
            GeyserUtils.getInstance().getPlayers().remove(player);
    }
}
