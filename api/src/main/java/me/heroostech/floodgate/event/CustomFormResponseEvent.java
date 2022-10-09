package me.heroostech.floodgate.event;

import me.heroostech.floodgate.component.Component;
import me.heroostech.floodgate.forms.CustomForm;
import me.heroostech.floodgate.player.FloodgatePlayer;
import net.minestom.server.event.Event;

import java.util.List;

public record CustomFormResponseEvent(FloodgatePlayer player, CustomForm form, List<Component> components) implements Event {}
