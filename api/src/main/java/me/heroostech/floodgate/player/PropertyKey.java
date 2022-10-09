package me.heroostech.floodgate.player;

public record PropertyKey(String key, boolean changeable, boolean removable) {
    /**
     * Socket Address returns the InetSocketAddress of the Bedrock player
     */
    public static final PropertyKey SOCKET_ADDRESS =
            new PropertyKey("socket_address", false, false);

    /**
     * Skin Uploaded returns a SkinData object containing the value and signature of the Skin
     */
    public static final PropertyKey SKIN_UPLOADED =
            new PropertyKey("skin_uploaded", false, false);

    public Result isAddAllowed(Object obj) {
        if (obj instanceof PropertyKey propertyKey) {

            if (key.equals(propertyKey.key)) {
                if ((propertyKey.changeable == changeable || propertyKey.changeable) &&
                        (propertyKey.removable == removable || propertyKey.removable)) {
                    return Result.ALLOWED;
                }
                return Result.INVALID_TAGS;
            }
            return Result.NOT_EQUALS;
        }

        if (obj instanceof String) {
            if (key.equals(obj)) {
                if (changeable) {
                    return Result.ALLOWED;
                }
                return Result.NOT_ALLOWED;
            }
            return Result.INVALID_TAGS;
        }
        return Result.NOT_EQUALS;
    }

    public enum Result {
        NOT_EQUALS,
        INVALID_TAGS,
        NOT_ALLOWED,
        ALLOWED
    }
}