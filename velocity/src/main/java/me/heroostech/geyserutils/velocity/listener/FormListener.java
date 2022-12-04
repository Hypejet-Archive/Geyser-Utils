package me.heroostech.geyserutils.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.geyserutils.forms.CustomForm;
import me.heroostech.geyserutils.forms.Form;
import me.heroostech.geyserutils.forms.ModalForm;
import me.heroostech.geyserutils.forms.SimpleForm;
import me.heroostech.geyserutils.velocity.utils.CustomFormConverter;
import me.heroostech.geyserutils.velocity.utils.ModalFormConverter;
import me.heroostech.geyserutils.velocity.utils.SimpleFormConverter;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FormListener {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) throws IOException, ClassNotFoundException {
        ChannelMessageSink player = event.getTarget();
        byte[] message = event.getData();
        ChannelIdentifier channel = event.getIdentifier();

        if (!channel.equals(MinecraftChannelIdentifier.from("geyserutils:form"))) return;
        if (!(player instanceof Player)) return;

        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(message));

        Form form = (Form) stream.readObject();
        String type = form.type();

        stream.close();

        boolean isFloodgatePlayer = FloodgateApi.getInstance().isFloodgatePlayer(((Player) player).getUniqueId());
        if (!isFloodgatePlayer) return;

        FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(((Player) player).getUniqueId());

        org.geysermc.cumulus.form.Form finalForm;

        switch(type) {
            case "custom" -> {
                CustomForm custom = (CustomForm) form;
                finalForm = CustomFormConverter.convert(custom);
            }

            case "modal" -> {
                ModalForm modal = (ModalForm) form;
                finalForm = ModalFormConverter.convert(modal);
            }

            case "simple" -> {
                SimpleForm simple = (SimpleForm) form;
                finalForm = SimpleFormConverter.convert(simple);
            }

            default -> throw new UnsupportedOperationException();
        }

        floodgatePlayer.sendForm(finalForm);
    }
}
