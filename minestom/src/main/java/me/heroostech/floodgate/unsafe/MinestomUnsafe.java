package me.heroostech.floodgate.unsafe;

import lombok.SneakyThrows;
import net.minestom.server.MinecraftServer;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class MinestomUnsafe implements Unsafe {
    /**
     * Send a raw Bedrock packet to the given online Bedrock player.
     *
     * @param bedrockPlayer the uuid of the online Bedrock player
     * @param packetId      the id of the packet to send
     * @param packetData    the raw packet data
     */
    @SneakyThrows
    @Override
    public void sendPacket(UUID bedrockPlayer, int packetId, byte[] packetData) {
        //TODO: HANDLE ON PROXY
        var player = MinecraftServer.getConnectionManager().getPlayer(bedrockPlayer);
        if(player == null) return;

        var byteStream = new ByteArrayOutputStream();
        var stream = new ObjectOutputStream(byteStream);

        stream.writeInt(packetId);
        stream.writeObject(packetData);

        stream.close();

        player.sendPluginMessage("minestomfloodgate:sendpacket", byteStream.toByteArray());

        byteStream.close();
    }
}
