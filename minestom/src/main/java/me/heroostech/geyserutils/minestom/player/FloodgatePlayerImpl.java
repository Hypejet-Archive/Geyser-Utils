package me.heroostech.geyserutils.minestom.player;

import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import net.minestom.server.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public final class FloodgatePlayerImpl implements FloodgatePlayer {
    private final Player player;
    private final String username;
    private final String xuid;
    private final boolean isLinked;
    private final String version;
    private final String langCode;
    private final boolean isFromProxy;
    private final String linkUsername;
    private final String linkUUID;
    private final String os;

    /**
     * Returns the Bedrock username that will be used as username on the server. This includes
     * replace spaces (if enabled), username shortened and prefix appended.
     */
    @Override
    public String getJavaUsername() {
        return player.getUsername();
    }

    /**
     * Returns the uuid that will be used as UUID on the server.
     */
    @Override
    public UUID getJavaUniqueId() {
        return player.getUuid();
    }

    /**
     * Returns the uuid that the server will use as uuid of that player. Will return
     * {@link #getJavaUniqueId()}
     */
    @Override
    public UUID getCorrectUniqueId() {
        return isLinked ? UUID.fromString(linkUUID) : getJavaUniqueId();
    }

    /**
     * Returns the username the server will as username for that player. Will return
     * {@link #getJavaUsername()}
     */
    @Override
    public String getCorrectUsername() {
        return isLinked ? linkUsername : getJavaUsername();
    }

    /**
     * Returns the version of the Bedrock client
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * Returns the real username of the Bedrock client. This username doesn't have a prefix, spaces
     * aren't replaced and the username hasn't been shortened.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Returns the Xbox Unique Identifier of the Bedrock client
     */
    @Override
    public String getXuid() {
        return xuid;
    }

    /**
     * Returns the language code of the Bedrock client
     */
    @Override
    public String getLanguageCode() {
        return langCode;
    }

    /**
     * Returns if the Floodgate player is connected through a proxy
     */
    @Override
    public boolean isFromProxy() {
        return isFromProxy;
    }

    /**
     * Returns true if the player is linked to a Java account
     */
    @Override
    public boolean isLinked() {
        return isLinked;
    }

    @Override
    public String getOs() {
        return os;
    }
}
