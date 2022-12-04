package me.heroostech.geyserutils.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.geysermc.floodgate.api.FloodgateApi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PacketListener {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) throws IOException, ClassNotFoundException {
        ChannelMessageSink player = event.getTarget();
        byte[] message = event.getData();
        ChannelIdentifier channel = event.getIdentifier();

        if (!channel.equals(MinecraftChannelIdentifier.from("geyserutils:packet"))) return;
        if (!(player instanceof Player pl)) return;

        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(message));

        byte[] packetData = (byte[]) stream.readObject();
        int packetId = stream.readInt();

        stream.close();

        boolean isFloodgatePlayer = FloodgateApi.getInstance().isFloodgatePlayer(pl.getUniqueId());
        if (!isFloodgatePlayer) return;

        FloodgateApi.getInstance().unsafe().sendPacket(pl.getUniqueId(), packetId, packetData);
    }
}
