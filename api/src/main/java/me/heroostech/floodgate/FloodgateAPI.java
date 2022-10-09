package me.heroostech.floodgate;

import lombok.NonNull;
import me.heroostech.floodgate.forms.Form;
import me.heroostech.floodgate.player.FloodgatePlayer;
import me.heroostech.floodgate.unsafe.Unsafe;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface FloodgateAPI {
    /**
     * Returns the Floodgate API instance.
     */
    static FloodgateAPI getInstance() {
        return InstanceHolder.getApi();
    }

    /**
     * Returns all the online Floodgate players.
     */
    Collection<FloodgatePlayer> getPlayers();

    /**
     * Returns the number of Floodgate players who are currently online.
     */
    int getPlayerCount();

    /**
     * Method to determine if the given <b>online</b> player is a bedrock player
     *
     * @param uuid The uuid of the <b>online</b> player
     * @return true if the given <b>online</b> player is a Bedrock player
     */
    boolean isFloodgatePlayer(UUID uuid);

    /**
     * Get info about the given Bedrock player
     *
     * @param uuid the uuid of the <b>online</b> Bedrock player
     * @return FloodgatePlayer if the given uuid is a Bedrock player
     */
    @Nullable FloodgatePlayer getPlayer(UUID uuid);

    /**
     * Checks if the uuid of the player has the createJavaPlayerId(long) format. This
     * method can't validate a linked player uuid, since that doesn't equal the format. Use
     * {@link #isFloodgatePlayer(UUID)} if you want to include linked accounts.
     *
     * @param uuid the uuid to check
     * @return true if the given uuid has the correct format.
     */
    boolean isFloodgateId(UUID uuid);

    boolean sendForm(UUID uuid, Form form);

    @NonNull Unsafe unsafe();
}
