package me.heroostech.floodgate.unsafe;

import me.heroostech.floodgate.player.FloodgatePlayer;

import java.util.UUID;

public interface Unsafe {
    /**
     * Send a raw Bedrock packet to the given online Bedrock player.
     *
     * @param bedrockPlayer the uuid of the online Bedrock player
     * @param packetId      the id of the packet to send
     * @param packetData    the raw packet data
     */
    void sendPacket(UUID bedrockPlayer, int packetId, byte[] packetData);

    /**
     * Send a raw Bedrock packet to the given online Bedrock player.
     *
     * @param player     the Bedrock player to send the packet to
     * @param packetId   the id of the packet to send
     * @param packetData the raw packet data
     */
    default void sendPacket(FloodgatePlayer player, int packetId, byte[] packetData) {
        sendPacket(player.getCorrectUniqueId(), packetId, packetData);
    }
}