package me.heroostech.floodgate.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.floodgate.VelocityFloodgate;
import me.heroostech.floodgate.forms.CustomForm;
import me.heroostech.floodgate.forms.Form;
import me.heroostech.floodgate.forms.ModalForm;
import me.heroostech.floodgate.forms.SimpleForm;
import me.heroostech.floodgate.utils.CustomFormConverter;
import me.heroostech.floodgate.utils.ModalFormConverter;
import me.heroostech.floodgate.utils.SimpleFormConverter;
import org.geysermc.floodgate.api.FloodgateApi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FormListener {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) throws IOException, ClassNotFoundException {
        var player = event.getTarget();
        var message = event.getData();
        var channel = event.getIdentifier();

        if (!channel.equals(MinecraftChannelIdentifier.from("minestomfloodgate:sendform"))) return;
        if (!(player instanceof Player)) return;

        var stream = new ObjectInputStream(new ByteArrayInputStream(message));

        var form = (Form) stream.readObject();
        var type = form.type();

        stream.close();

        var isFloodgatePlayer = FloodgateApi.getInstance().isFloodgatePlayer(((Player) player).getUniqueId());
        if (!isFloodgatePlayer) return;

        var floodgatePlayer = FloodgateApi.getInstance().getPlayer(((Player) player).getUniqueId());

        var finalForm = (org.geysermc.cumulus.form.Form) null;

        switch(type) {
            case "custom" -> {
                var custom = (CustomForm) form;
                finalForm = CustomFormConverter.convert(custom);
            }

            case "modal" -> {
                var modal = (ModalForm) form;
                finalForm = ModalFormConverter.convert(modal);
            }

            case "simple" -> {
                var simple = (SimpleForm) form;
                finalForm = SimpleFormConverter.convert(simple);
            }
        }

        floodgatePlayer.sendForm(finalForm);
    }
}
