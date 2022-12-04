package me.heroostech.geyserutils.component.util;

import java.io.Serializable;
import java.util.Locale;

/**
 * An enum containing the valid component types. Valid component types are:
 * <ul>
 *     <li>{@link me.heroostech.geyserutils.component.DropdownComponent Dropdown Component}</li>
 *     <li>{@link me.heroostech.geyserutils.component.InputComponent Input Component}</li>
 *     <li>{@link me.heroostech.geyserutils.component.LabelComponent Label Component}</li>
 *     <li>{@link me.heroostech.geyserutils.component.SliderComponent Slider Component}</li>
 *     <li>{@link me.heroostech.geyserutils.component.StepSliderComponent Step Slider Component}</li>
 *     <li>{@link me.heroostech.geyserutils.component.ToggleComponent Toggle Component}</li>
 * </ul>
 */
public enum ComponentType implements Serializable {
    DROPDOWN, INPUT, LABEL, SLIDER, STEP_SLIDER, TOGGLE;

    private static final ComponentType[] VALUES = values();

    private final String name = name().toLowerCase(Locale.ROOT);

    public static ComponentType fromName(String name) {
        for (ComponentType type : VALUES) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String componentName() {
        return name;
    }
}
