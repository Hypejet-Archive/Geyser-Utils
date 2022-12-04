package me.heroostech.geyserutils.minestom.listeners;

import me.heroostech.geyserutils.component.ButtonComponent;
import me.heroostech.geyserutils.forms.CustomForm;
import me.heroostech.geyserutils.forms.Form;
import me.heroostech.geyserutils.forms.ModalForm;
import me.heroostech.geyserutils.forms.SimpleForm;
import me.heroostech.geyserutils.forms.response.CustomFormResponse;
import me.heroostech.geyserutils.forms.response.ModalFormResponse;
import me.heroostech.geyserutils.forms.response.SimpleFormResponse;
import me.heroostech.geyserutils.minestom.GeyserUtils;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.player.PlayerPluginMessageEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class FormResponseListener {
    public static void listener(PlayerPluginMessageEvent event) {
        try {
            String channel = event.getIdentifier();
            Player player = event.getPlayer();
            byte[] message = event.getMessage();

            if (!channel.equals("geyserutils:response")) return;

            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(message));

            Form form = (Form) stream.readObject();

            Event eventToCall;

            switch (form.type()) {
                case "custom" -> eventToCall = new CustomFormResponse(player, GeyserUtils.getInstance().getPlayers().get(player), (CustomForm) form, (Object[]) stream.readObject());
                case "modal" -> eventToCall = new ModalFormResponse(player, GeyserUtils.getInstance().getPlayers().get(player), (ModalForm) form, stream.readUTF());
                case "simple" -> eventToCall = new SimpleFormResponse(player, GeyserUtils.getInstance().getPlayers().get(player), (SimpleForm) form, (ButtonComponent) stream.readObject());
                default -> throw new UnsupportedOperationException();
            }

            GeyserUtils.getInstance().getEventNode().call(eventToCall);

            stream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
