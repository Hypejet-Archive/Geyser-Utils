package me.heroostech.geyserutils.minestom;

import me.heroostech.geyserutils.FloodgateApi;
import me.heroostech.geyserutils.forms.Form;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import me.heroostech.geyserutils.minestom.unsafe.UnsafeImpl;
import me.heroostech.geyserutils.unsafe.Unsafe;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class FloodgateApiImpl implements FloodgateApi {

    private final Unsafe unsafe;

    public FloodgateApiImpl() {
        unsafe = new UnsafeImpl();
    }

    /**
     * Returns all the online Floodgate players.
     */
    @Override
    public Collection<FloodgatePlayer> getPlayers() {
        var list = new ArrayList<FloodgatePlayer>();
        GeyserUtils.getInstance().getPlayers().forEach((player, floodgatePlayer) -> list.add(floodgatePlayer));
        return list;
    }

    /**
     * Returns the number of Floodgate players who are currently online.
     */
    @Override
    public int getPlayerCount() {
        return GeyserUtils.getInstance().getPlayers().size();
    }

    /**
     * Method to determine if the given <b>online</b> player is a bedrock player
     *
     * @param uuid The uuid of the <b>online</b> player
     * @return true if the given <b>online</b> player is a Bedrock player
     */
    @Override
    public boolean isFloodgatePlayer(UUID uuid) {
        var player = MinecraftServer.getConnectionManager().getPlayer(uuid);
        return GeyserUtils.getInstance().getPlayers().containsKey(player);
    }

    /**
     * Get info about the given Bedrock player
     *
     * @param uuid the uuid of the <b>online</b> Bedrock player
     * @return FloodgatePlayer if the given uuid is a Bedrock player
     */
    @Override
    public FloodgatePlayer getPlayer(UUID uuid) {
        var player = MinecraftServer.getConnectionManager().getPlayer(uuid);
        return GeyserUtils.getInstance().getPlayers().get(player);
    }

    /**
     * Checks if the uuid of the player has the createJavaPlayerId(long) format. This
     * method can't validate a linked player uuid, since that doesn't equal the format. Use
     * {@link #isFloodgatePlayer(UUID)} if you want to include linked accounts.
     *
     * @param uuid the uuid to check
     * @return true if the given uuid has the correct format.
     */
    @Override
    public boolean isFloodgateId(UUID uuid) {
        return uuid.getMostSignificantBits() == 0;
    }

    @Override
    public boolean sendForm(Form form) {
        try {
            var player = MinecraftServer.getConnectionManager().getPlayer(form.player());
            var byteStream = new ByteArrayOutputStream();
            var stream = new ObjectOutputStream(byteStream);

            if (player == null) return false;

            stream.writeObject(form);
            stream.close();

            var bytes = byteStream.toByteArray();
            byteStream.close();

            player.sendPluginMessage("geyserutils:form", bytes);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public @NotNull Unsafe unsafe() {
        return unsafe;
    }
}
