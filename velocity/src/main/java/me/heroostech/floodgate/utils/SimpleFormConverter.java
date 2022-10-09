package me.heroostech.floodgate.utils;


import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.floodgate.VelocityFloodgate;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleFormConverter {
    public static SimpleForm convert(me.heroostech.floodgate.forms.SimpleForm form) {
        var f = SimpleForm.builder();
        f.title(form.title());
        f.content(form.content());
        convertButtons(form.buttons()).forEach(buttonComponent -> f.button(buttonComponent.text(), buttonComponent.image()));
        f.validResultHandler((aForm, response) -> {
            try {
                var player = VelocityFloodgate.INSTANCE.getServer().getPlayer(form.player());
                if (player.isPresent()) {
                    var pl = player.get();

                    var byteStream = new ByteArrayOutputStream();
                    var stream = new ObjectOutputStream(byteStream);

                    stream.writeObject(form);
                    if(response.clickedButton().image() != null) {
                        stream.writeObject(new me.heroostech.floodgate.component.ButtonComponent(response.clickedButton().text(), convertImage(Objects.requireNonNull(response.clickedButton().image()))));
                    } else {
                        stream.writeObject(new me.heroostech.floodgate.component.ButtonComponent(response.clickedButton().text()));
                    }
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

    private static List<ButtonComponent> convertButtons(List<me.heroostech.floodgate.component.ButtonComponent> components) {
        return components.stream()
                .map(component -> {
                    if (component.getImage() != null) {
                        return ButtonComponent.of(component.getText(), convertImage(component.getImage()));
                    }

                    return ButtonComponent.of(component.getText());
                })
                .collect(Collectors.toList());
    }

    private static FormImage convertImage(me.heroostech.floodgate.util.FormImage image) {
        return FormImage.of(FormImage.Type.valueOf(image.type().getName()), image.data());
    }

    private static me.heroostech.floodgate.util.FormImage convertImage(FormImage image) {
        return new me.heroostech.floodgate.util.FormImage(me.heroostech.floodgate.util.FormImage.Type.valueOf(image.type().name()), image.data());
    }
}
