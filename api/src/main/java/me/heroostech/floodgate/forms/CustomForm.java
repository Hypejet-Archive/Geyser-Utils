package me.heroostech.floodgate.forms;

import me.heroostech.floodgate.component.Component;
import me.heroostech.floodgate.util.FormImage;

import java.util.List;
import java.util.UUID;

public record CustomForm(String title, FormImage icon, List<Component> content, UUID player) implements Form {
    /**
     * Returns a type of form
     */
    @Override
    public String type() {
        return "custom";
    }
}
