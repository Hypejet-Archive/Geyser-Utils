package me.heroostech.geyserutils.player;

import java.io.Serializable;
import java.util.UUID;

public record RawFloodgatePlayer(UUID player, String username, String xuid, boolean isLinked, String version, String langCode, String linkUsername, boolean isFromProxy, UUID linkUUID, String os) implements Serializable {}
