package me.heroostech.geyserutils.component;

import me.heroostech.geyserutils.component.util.ComponentType;

import java.util.List;

public record DropdownComponent(String text, List<String> options, int defaultOption) implements Component {
    /**
     * Returns the type of component this component is.
     */
    @Override
    public ComponentType type() {
        return ComponentType.DROPDOWN;
    }
}
