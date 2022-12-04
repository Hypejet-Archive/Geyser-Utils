package me.heroostech.geyserutils.forms;

import java.util.UUID;

public record ModalForm(String title, String content, String button1, String button2, UUID player) implements Form {
    /**
     * Returns a type of form
     */
    @Override
    public String type() {
        return "modal";
    }
}