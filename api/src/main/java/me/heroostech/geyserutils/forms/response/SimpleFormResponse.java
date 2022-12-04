package me.heroostech.geyserutils.forms.response;

import me.heroostech.geyserutils.component.ButtonComponent;
import me.heroostech.geyserutils.forms.SimpleForm;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record SimpleFormResponse(Player player, FloodgatePlayer floodgatePlayer, SimpleForm form, ButtonComponent button) implements Event {}
