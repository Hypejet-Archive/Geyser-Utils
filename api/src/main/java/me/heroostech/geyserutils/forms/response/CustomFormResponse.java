package me.heroostech.geyserutils.forms.response;

import me.heroostech.geyserutils.forms.CustomForm;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record CustomFormResponse(Player player, FloodgatePlayer floodgatePlayer, CustomForm form, Object[] objects, int pos) implements Event {
    public Object next() {
        return objects[pos];
    }

    public boolean hasNext() {
        return this.objects.length > pos + 1;
    }
}
