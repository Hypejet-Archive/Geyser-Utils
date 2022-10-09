package me.heroostech.floodgate.component.util;

import java.io.Serializable;
import java.util.Locale;

/**
 * An enum containing the valid component types. Valid component types are:
 * <ul>
 *     <li>{@link me.heroostech.floodgate.component.DropdownComponent Dropdown Component}</li>
 *     <li>{@link me.heroostech.floodgate.component.InputComponent Input Component}</li>
 *     <li>{@link me.heroostech.floodgate.component.LabelComponent Label Component}</li>
 *     <li>{@link me.heroostech.floodgate.component.SliderComponent Slider Component}</li>
 *     <li>{@link me.heroostech.floodgate.component.StepSliderComponent Step Slider Component}</li>
 *     <li>{@link me.heroostech.floodgate.component.ToggleComponent Toggle Component}</li>
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
