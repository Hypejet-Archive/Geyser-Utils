package me.heroostech.geyserutils.component;

import me.heroostech.geyserutils.component.util.ComponentType;

import java.io.Serializable;

/**
 * The base class of all components.
 */
public interface Component extends Serializable {
    /**
     * Returns the type of component this component is.
     */
    ComponentType type();

    /**
     * Returns the text that is shown in the component.
     */
    String text();
}
