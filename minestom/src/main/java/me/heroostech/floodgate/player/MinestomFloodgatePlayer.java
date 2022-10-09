package me.heroostech.floodgate.player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minestom.server.entity.Player;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class MinestomFloodgatePlayer implements FloodgatePlayer {

    private final Player player;
    private final String username;
    private final String xuid;
    private final boolean isLinked;
    private final String version;
    private final String langCode;
    private final boolean isFromProxy;
    private final String linkUsername;
    private final String linkUUID;

    @Getter(AccessLevel.PRIVATE)
    private Map<PropertyKey, Object> propertyKeyToValue;
    @Getter(AccessLevel.PRIVATE)
    private Map<String, PropertyKey> stringToPropertyKey;

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
    public boolean hasProperty(PropertyKey key) {
        return false;
    }

    @Override
    public boolean hasProperty(String key) {
        return false;
    }

    @Override
    public <T> T getProperty(PropertyKey key) {
        return null;
    }

    @Override
    public <T> T getProperty(String key) {
        return null;
    }

    @Override
    public <T> T removeProperty(PropertyKey key) {
        return null;
    }

    @Override
    public <T> T removeProperty(String key) {
        return null;
    }

    @Override
    public <T> T addProperty(PropertyKey key, Object value) {
        return null;
    }

    @Override
    public <T> T addProperty(String key, Object value) {
        return null;
    }

    /*@Override
    public boolean hasProperty(PropertyKey key) {
        if (propertyKeyToValue == null) {
            return false;
        }
        return propertyKeyToValue.get(key) != null;
    }

    @Override
    public boolean hasProperty(String key) {
        if (stringToPropertyKey == null) {
            return false;
        }
        return hasProperty(stringToPropertyKey.get(key));
    }

    @Override
    public <T> T getProperty(PropertyKey key) {
        if (propertyKeyToValue == null) {
            return null;
        }
        return (T) propertyKeyToValue.get(key);
    }

    @Override
    public <T> T getProperty(String key) {
        if (stringToPropertyKey == null) {
            return null;
        }
        return getProperty(stringToPropertyKey.get(key));
    }

    @Override
    public <T> T removeProperty(String key) {
        if (stringToPropertyKey == null) {
            return null;
        }

        PropertyKey propertyKey = stringToPropertyKey.get(key);

        if (propertyKey == null || !propertyKey.isRemovable()) {
            return null;
        }

        return (T) propertyKeyToValue.remove(propertyKey);
    }

    @Override
    public <T> T removeProperty(PropertyKey key) {
        if (stringToPropertyKey == null) {
            return null;
        }

        PropertyKey propertyKey = stringToPropertyKey.get(key.getKey());

        if (propertyKey == null || !propertyKey.equals(key) || !propertyKey.isRemovable()) {
            return null;
        }

        stringToPropertyKey.remove(key.getKey());

        return (T) propertyKeyToValue.remove(key);
    }

    @Override
    public <T> T addProperty(PropertyKey key, Object value) {
        if (stringToPropertyKey == null) {
            stringToPropertyKey = new HashMap<>();
            propertyKeyToValue = new HashMap<>();

            stringToPropertyKey.put(key.getKey(), key);
            propertyKeyToValue.put(key, value);
            return null;
        }

        PropertyKey propertyKey = stringToPropertyKey.get(key.getKey());

        if (propertyKey != null && propertyKey.isAddAllowed(key) == PropertyKey.Result.ALLOWED) {
            stringToPropertyKey.put(key.getKey(), key);
            return (T) propertyKeyToValue.put(key, value);
        }

        return (T) stringToPropertyKey.computeIfAbsent(key.getKey(), (keyString) -> {
            propertyKeyToValue.put(key, value);
            return key;
        });
    }

    @Override
    public <T> T addProperty(String key, Object value) {
        PropertyKey propertyKey = new PropertyKey(key, true, true);

        if (stringToPropertyKey == null) {
            stringToPropertyKey = new HashMap<>();
            propertyKeyToValue = new HashMap<>();

            stringToPropertyKey.put(key, propertyKey);
            propertyKeyToValue.put(propertyKey, value);
            return null;
        }

        PropertyKey currentPropertyKey = stringToPropertyKey.get(key);

        // key is always changeable if it passes this if statement
        if (currentPropertyKey != null && currentPropertyKey.isAddAllowed(key) == PropertyKey.Result.ALLOWED) {
            stringToPropertyKey.put(key, propertyKey);
            return (T) propertyKeyToValue.put(propertyKey, value);
        }

        return (T) stringToPropertyKey.computeIfAbsent(key, (keyString) -> {
            propertyKeyToValue.put(propertyKey, value);
            return propertyKey;
        });
    }*/
}
