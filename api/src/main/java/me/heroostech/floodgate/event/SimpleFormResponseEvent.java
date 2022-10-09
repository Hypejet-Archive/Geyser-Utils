package me.heroostech.floodgate.event;

import me.heroostech.floodgate.component.ButtonComponent;
import me.heroostech.floodgate.forms.SimpleForm;
import me.heroostech.floodgate.player.FloodgatePlayer;
import net.minestom.server.event.Event;

public record SimpleFormResponseEvent(FloodgatePlayer player, SimpleForm form, ButtonComponent component) implements Event {}
