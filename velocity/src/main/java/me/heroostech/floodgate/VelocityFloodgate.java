package me.heroostech.floodgate;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import lombok.Getter;
import me.heroostech.floodgate.listener.FloodgatePlayerSetup;
import me.heroostech.floodgate.listener.FormListener;
import org.slf4j.Logger;

@Plugin(id = "minestomfloodgate", name = "MinestomFloodgate",
        version = "v1.0.0", dependencies = {@Dependency(id = "floodgate")})
public class VelocityFloodgate {

    public static VelocityFloodgate INSTANCE;

    @Getter private final Logger logger;
    @Getter private final ProxyServer server;

    @Inject
    public VelocityFloodgate(ProxyServer server, Logger logger) {
        INSTANCE = this;
        this.logger = logger;
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.logger.info("MinestomFloodgate :: Enabled");
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("minestomfloodgate", "sendform"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("minestomfloodgate", "setupplayer"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("minestomfloodgate", "postsetupplayer"));
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("minestomfloodgate", "formresponse"));
        this.server.getEventManager().register(this, new FormListener());
        this.server.getEventManager().register(this, new FloodgatePlayerSetup());
    }
}
