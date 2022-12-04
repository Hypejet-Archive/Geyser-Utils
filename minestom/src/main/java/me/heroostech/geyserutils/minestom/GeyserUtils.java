package me.heroostech.geyserutils.minestom;

import lombok.Getter;
import me.heroostech.geyserutils.InstanceHolder;
import me.heroostech.geyserutils.minestom.listeners.FloodgatePlayerListener;
import me.heroostech.geyserutils.minestom.listeners.FormResponseListener;
import me.heroostech.geyserutils.minestom.listeners.PlayerDisconnectListener;
import me.heroostech.geyserutils.minestom.player.FloodgatePlayerImpl;
import me.heroostech.geyserutils.minestom.listeners.PlayerSpawnListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.*;
import net.minestom.server.extensions.Extension;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

import java.util.HashMap;

public class GeyserUtils extends Extension {

    @Getter private final HashMap<Player, FloodgatePlayerImpl> players = new HashMap<>();
    @Getter private static GeyserUtils instance;

    @Override
    public void preInitialize() {
        instance = this;
        InstanceHolder.setApi(new FloodgateApiImpl());
    }

    @Override
    public void initialize() {
        // Floodgate Stuff
        getEventNode().addListener(PlayerPluginMessageEvent.class, FloodgatePlayerListener::listener);
        getEventNode().addListener(PlayerSpawnEvent.class, PlayerSpawnListener::listener);
        getEventNode().addListener(PlayerPluginMessageEvent.class, FormResponseListener::listener);
        getEventNode().addListener(PlayerDisconnectEvent.class, PlayerDisconnectListener::listener);

        // Fix jumping with bedrock between instances & between servers on proxy
        if(!MinecraftServer.getDimensionTypeManager().isRegistered(NamespaceID.from("minecraft:the_nether")))
            MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("minecraft:the_nether")).build());
    }

    @Override
    public void terminate() {}
}
