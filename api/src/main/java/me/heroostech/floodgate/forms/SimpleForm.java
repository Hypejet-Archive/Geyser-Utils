package me.heroostech.floodgate.forms;

import me.heroostech.floodgate.component.ButtonComponent;

import java.util.List;
import java.util.UUID;

public record SimpleForm(String title, String content, List<ButtonComponent> buttons, UUID player) implements Form {
    /**
     * Returns a type of form
     */
    @Override
    public String type() {
        return "simple";
    }
}