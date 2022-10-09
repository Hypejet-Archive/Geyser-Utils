package me.heroostech.floodgate.utils;

import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.floodgate.VelocityFloodgate;
import org.geysermc.cumulus.form.ModalForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModalFormConverter {
    public static ModalForm convert(me.heroostech.floodgate.forms.ModalForm form) {
        var f = ModalForm.builder();
        f.title(form.title());
        f.content(form.content());
        f.button1(form.button1());
        f.button2(form.button2());
        f.validResultHandler((aForm, response) -> {
            try {
                var player = VelocityFloodgate.INSTANCE.getServer().getPlayer(form.player());
                if (player.isPresent()) {
                    var pl = player.get();

                    var byteStream = new ByteArrayOutputStream();
                    var stream = new ObjectOutputStream(byteStream);

                    stream.writeObject(form);
                    stream.writeObject(response.clickedButtonText());

                    stream.close();

                    var server = pl.getCurrentServer();

                    server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(MinecraftChannelIdentifier.from("minestomfloodgate:formresponse"), byteStream.toByteArray()));

                    byteStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return f.build();
    }
}
