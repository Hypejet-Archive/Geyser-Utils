package me.heroostech.geyserutils.forms.response;

import lombok.Getter;
import me.heroostech.geyserutils.forms.CustomForm;
import me.heroostech.geyserutils.player.FloodgatePlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public class CustomFormResponse implements Event {

    @Getter private final Player player;
    @Getter private final FloodgatePlayer floodgatePlayer;
    @Getter private final CustomForm form;
    private final Object[] objects;
    private int pos;

    public CustomFormResponse(Player player, FloodgatePlayer floodgatePlayer, CustomForm form, Object[] objects) {
        this.player = player;
        this.floodgatePlayer = floodgatePlayer;
        this.form = form;
        this.objects = objects;
        this.pos = -1;
    }

    public Object next() {
        pos++;
        if(hasNext())
            return objects[pos];
        return null;
    }

    public boolean hasNext() {
        return this.objects.length > pos + 1;
    }
}
