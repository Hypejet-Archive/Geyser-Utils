package me.heroostech.floodgate.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.floodgate.VelocityFloodgate;
import me.heroostech.floodgate.player.RawFloodgatePlayer;
import org.geysermc.floodgate.api.FloodgateApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FloodgatePlayerSetup {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) throws IOException {
        var target = event.getTarget();
        var channel = event.getIdentifier();

        if(!channel.equals(MinecraftChannelIdentifier.from("minestomfloodgate:setupplayer"))) return;
        if(!(target instanceof Player player)) return;

        var byteOutput = new ByteArrayOutputStream();
        var output = new ObjectOutputStream(byteOutput);

        var isFloodgatePlayer = FloodgateApi.getInstance().isFloodgateId(player.getUniqueId());

        if(!isFloodgatePlayer) {
            output.close();
            byteOutput.close();
            return;
        }


        var pl = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
        var rawPlayer = new RawFloodgatePlayer(player.getUniqueId(), pl.getUsername(), pl.getXuid(), pl.isLinked(), pl.getVersion(), pl.getLanguageCode(), pl.getCorrectUsername(), pl.isFromProxy(), pl.getCorrectUniqueId());

        output.writeObject(rawPlayer);
        output.close();

        var server = player.getCurrentServer();

        server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(
                MinecraftChannelIdentifier.from("minestomfloodgate:postsetupplayer"),
                byteOutput.toByteArray()));

        byteOutput.close();
    }
}
