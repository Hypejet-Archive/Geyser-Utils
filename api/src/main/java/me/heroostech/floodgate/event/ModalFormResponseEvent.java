package me.heroostech.floodgate.event;

import me.heroostech.floodgate.forms.ModalForm;
import me.heroostech.floodgate.player.FloodgatePlayer;
import net.minestom.server.event.Event;

public record ModalFormResponseEvent(FloodgatePlayer player, ModalForm form, String button) implements Event {}
