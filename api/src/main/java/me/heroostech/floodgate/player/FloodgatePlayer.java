package me.heroostech.floodgate.player;

import me.heroostech.floodgate.FloodgateAPI;
import me.heroostech.floodgate.forms.Form;

import java.util.UUID;

public interface FloodgatePlayer {
    /**
     * Returns the Bedrock username that will be used as username on the server. This includes
     * replace spaces (if enabled), username shortened and prefix appended.
     */
    String getJavaUsername();

    /**
     * Returns the uuid that will be used as UUID on the server.
     */
    UUID getJavaUniqueId();

    /**
     * Returns the uuid that the server will use as uuid of that player. Will return
     * {@link #getJavaUniqueId()}
     */
    UUID getCorrectUniqueId();

    /**
     * Returns the username the server will as username for that player. Will return
     * {@link #getJavaUsername()}
     */
    String getCorrectUsername();

    /**
     * Returns the version of the Bedrock client
     */
    String getVersion();

    /**
     * Returns the real username of the Bedrock client. This username doesn't have a prefix, spaces
     * aren't replaced and the username hasn't been shortened.
     */
    String getUsername();

    /**
     * Returns the Xbox Unique Identifier of the Bedrock client
     */
    String getXuid();

    /**
     * Returns the language code of the Bedrock client
     */
    String getLanguageCode();


    /**
     * Returns if the Floodgate player is connected through a proxy
     */
    boolean isFromProxy();

    /**
     * Returns true if the player is linked to a Java account
     */
    boolean isLinked();

    default boolean sendForm(Form form) {
        return FloodgateAPI.getInstance().sendForm(getCorrectUniqueId(), form);
    }

    boolean hasProperty(PropertyKey key);

    boolean hasProperty(String key);

    <T> T getProperty(PropertyKey key);

    <T> T getProperty(String key);

    <T> T removeProperty(PropertyKey key);

    <T> T removeProperty(String key);

    <T> T addProperty(PropertyKey key, Object value);

    <T> T addProperty(String key, Object value);

    /**
     * Casts the FloodgatePlayer instance to a class that extends FloodgatePlayer.
     *
     * @param <T> The instance to cast to.
     * @return The FloodgatePlayer casted to the given class
     * @throws ClassCastException when it can't cast the instance to the given class
     */
    default <T extends FloodgatePlayer> T as(Class<T> clazz) {
        return clazz.cast(this);
    }
}
