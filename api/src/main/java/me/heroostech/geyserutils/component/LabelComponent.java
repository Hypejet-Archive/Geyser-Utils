package me.heroostech.geyserutils.component;

import me.heroostech.geyserutils.component.util.ComponentType;

public record LabelComponent(String text) implements Component {
    /**
     * Returns the type of component this component is.
     */
    @Override
    public ComponentType type() {
        return ComponentType.LABEL;
    }
}
