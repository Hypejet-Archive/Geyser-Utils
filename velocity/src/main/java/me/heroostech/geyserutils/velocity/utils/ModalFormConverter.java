package me.heroostech.geyserutils.velocity.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.geyserutils.velocity.VelocityFloodgate;
import org.geysermc.cumulus.form.ModalForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class ModalFormConverter {
    public static ModalForm convert(me.heroostech.geyserutils.forms.ModalForm form) {
        var f = ModalForm.builder();
        f.title(form.title());
        f.content(form.content());
        f.button1(form.button1());
        f.button2(form.button2());
        f.validResultHandler((aForm, response) -> {
            try {
                Optional<Player> player = VelocityFloodgate.INSTANCE.server().getPlayer(form.player());
                if (player.isPresent()) {
                    Player pl = player.get();

                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    ObjectOutputStream stream = new ObjectOutputStream(byteStream);

                    stream.writeObject(form);
                    stream.writeUTF(response.clickedButtonText());

                    stream.close();

                    Optional<ServerConnection> server = pl.getCurrentServer();

                    server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(MinecraftChannelIdentifier.from("geyserutils:response"), byteStream.toByteArray()));

                    byteStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return f.build();
    }
}
