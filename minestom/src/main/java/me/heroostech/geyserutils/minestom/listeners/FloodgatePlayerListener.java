package me.heroostech.geyserutils.minestom.listeners;

import me.heroostech.geyserutils.minestom.GeyserUtils;
import me.heroostech.geyserutils.minestom.player.FloodgatePlayerImpl;
import me.heroostech.geyserutils.player.RawFloodgatePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerPluginMessageEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class FloodgatePlayerListener {
    public static void listener(PlayerPluginMessageEvent event) {
        try {
            String channel = event.getIdentifier();
            Player player = event.getPlayer();
            byte[] message = event.getMessage();

            if (!channel.equals("geyserutils:player")) return;

            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(message));

            RawFloodgatePlayer raw = (RawFloodgatePlayer) stream.readObject();
            FloodgatePlayerImpl pl = new FloodgatePlayerImpl(player, raw.username(), raw.xuid(), raw.isLinked(), raw.version(), raw.langCode(), raw.isFromProxy(), raw.linkUsername(), raw.linkUUID().toString(), raw.os());

            GeyserUtils.getInstance().getPlayers().put(player, pl);

            stream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
