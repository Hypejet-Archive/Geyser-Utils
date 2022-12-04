package me.heroostech.geyserutils.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.geyserutils.velocity.listener.FloodgatePlayerSetup;
import me.heroostech.geyserutils.velocity.listener.FormListener;
import org.slf4j.Logger;

@Plugin(id = "minestomfloodgate", name = "MinestomFloodgate",
        version = "v1.0.0", dependencies = {@Dependency(id = "floodgate")})
public record VelocityFloodgate(ProxyServer server, Logger logger) {

    public static VelocityFloodgate INSTANCE;

    @Inject
    public VelocityFloodgate {
        INSTANCE = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("geyserutils", "form"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("geyserutils", "join"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("geyserutils", "player"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("geyserutils", "response"));
        this.server.getEventManager().register(this, new FormListener());
        this.server.getEventManager().register(this, new FloodgatePlayerSetup());
    }
}
