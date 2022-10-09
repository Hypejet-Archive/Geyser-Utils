package me.heroostech.floodgate;

import lombok.Getter;
import me.heroostech.floodgate.component.ButtonComponent;
import me.heroostech.floodgate.component.Component;
import me.heroostech.floodgate.event.CustomFormResponseEvent;
import me.heroostech.floodgate.event.ModalFormResponseEvent;
import me.heroostech.floodgate.event.SimpleFormResponseEvent;
import me.heroostech.floodgate.forms.CustomForm;
import me.heroostech.floodgate.forms.Form;
import me.heroostech.floodgate.forms.ModalForm;
import me.heroostech.floodgate.forms.SimpleForm;
import me.heroostech.floodgate.player.MinestomFloodgatePlayer;
import me.heroostech.floodgate.player.RawFloodgatePlayer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.player.*;
import net.minestom.server.extensions.Extension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;

public class MinestomFloodgate extends Extension {

    @Getter private final HashMap<Player, MinestomFloodgatePlayer> players = new HashMap<>();

    public static MinestomFloodgate INSTANCE;

    @Override
    public void preInitialize() {
        INSTANCE = this;
        InstanceHolder.setApi(new MinestomFloodgateAPI());
    }

    @Override
    public void initialize() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerPluginMessageEvent.class, event -> {
            try {
                var channel = event.getIdentifier();
                var player = event.getPlayer();
                var message = event.getMessage();

                if (!channel.equals("minestomfloodgate:postsetupplayer")) return;

                var stream = new ObjectInputStream(new ByteArrayInputStream(message));

                var raw = (RawFloodgatePlayer) stream.readObject();
                var pl = new MinestomFloodgatePlayer(player, raw.username(), raw.xuid(), raw.isLinked(), raw.version(), raw.langCode(), raw.isFromProxy(), raw.linkUsername(), raw.linkUUID().toString());

                getPlayers().put(player, pl);

                stream.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        getEventNode().addListener(PlayerSpawnEvent.class, event -> {
            var player = event.getPlayer();
            player.sendPluginMessage("minestomfloodgate:setupplayer", new byte[0]);
        });

        getEventNode().addListener(PlayerPluginMessageEvent.class, event -> {
            try {
                var channel = event.getIdentifier();
                var player = event.getPlayer();
                var message = event.getMessage();

                if (!channel.equals("minestomfloodgate:formresponse")) return;

                var stream = new ObjectInputStream(new ByteArrayInputStream(message));

                var form = (Form) stream.readObject();

                var eventToCall = (Event) null;

                switch (form.type()) {
                    case "custom" -> eventToCall = new CustomFormResponseEvent(getPlayers().get(player), (CustomForm) form, (List<Component>) stream.readObject());
                    case "modal" -> eventToCall = new ModalFormResponseEvent(getPlayers().get(player), (ModalForm) form, (String) stream.readObject());
                    case "simple" -> eventToCall = new SimpleFormResponseEvent(getPlayers().get(player), (SimpleForm) form, (ButtonComponent) stream.readObject());
                }

                if(eventToCall != null) {
                    getEventNode().call(eventToCall);
                }

                stream.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        getEventNode().addListener(PlayerDisconnectEvent.class, event -> {
            var player = event.getPlayer();
            if(FloodgateAPI.getInstance().isFloodgatePlayer(player.getUuid()))
                getPlayers().remove(player);
        });
    }

    @Override
    public void terminate() {

    }
}
