package me.heroostech.geyserutils.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.geyserutils.player.RawFloodgatePlayer;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class FloodgatePlayerSetup {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) throws IOException {
        ChannelMessageSink target = event.getTarget();
        ChannelIdentifier channel = event.getIdentifier();

        if(!channel.equals(MinecraftChannelIdentifier.from("geyserutils:join"))) return;
        if(!(target instanceof Player player)) return;

        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(byteOutput);

        boolean isFloodgatePlayer = FloodgateApi.getInstance().isFloodgateId(player.getUniqueId());

        if(!isFloodgatePlayer) {
            output.close();
            byteOutput.close();
            return;
        }


        FloodgatePlayer pl = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
        RawFloodgatePlayer rawPlayer = new RawFloodgatePlayer(player.getUniqueId(), pl.getUsername(), pl.getXuid(), pl.isLinked(), pl.getVersion(), pl.getLanguageCode(), pl.getCorrectUsername(), pl.isFromProxy(), pl.getCorrectUniqueId(), pl.getDeviceOs().name());

        output.writeObject(rawPlayer);
        output.close();

        Optional<ServerConnection> server = player.getCurrentServer();

        server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(
                MinecraftChannelIdentifier.from("geyserutils:player"),
                byteOutput.toByteArray()));

        byteOutput.close();
    }
}
