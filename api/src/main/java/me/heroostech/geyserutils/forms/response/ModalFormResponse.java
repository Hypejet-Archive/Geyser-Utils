package me.heroostech.geyserutils.forms.response;

import me.heroostech.geyserutils.forms.ModalForm;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record ModalFormResponse(Player player, FloodgatePlayer floodgatePlayer, ModalForm form, String button) implements Event {}
