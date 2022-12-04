package me.heroostech.geyserutils.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.component.Component;
import me.heroostech.geyserutils.util.FormImage;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
public final class CustomForm implements Form {

    private final String title;
    @Getter private FormImage icon;
    @Getter private final List<Component> content;
    private final UUID player;

    @Override
    public String title() {
        return title;
    }

    /**
     * Returns a type of form
     */
    @Override
    public String type() {
        return "custom";
    }

    @Override
    public UUID player() {
        return player;
    }
}
