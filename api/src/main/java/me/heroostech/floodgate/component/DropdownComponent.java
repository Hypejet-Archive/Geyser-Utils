package me.heroostech.floodgate.component;

import me.heroostech.floodgate.component.util.ComponentType;

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
