package me.heroostech.floodgate.component;

import me.heroostech.floodgate.component.util.ComponentType;

public record LabelComponent(String text) implements Component {
    /**
     * Returns the type of component this component is.
     */
    @Override
    public ComponentType type() {
        return ComponentType.LABEL;
    }
}
