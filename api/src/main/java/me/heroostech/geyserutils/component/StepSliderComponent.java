package me.heroostech.geyserutils.component;

import me.heroostech.geyserutils.component.util.ComponentType;

import java.util.List;

public record StepSliderComponent(String text, List<String> steps, Integer defaultStep) implements Component {
    /**
     * Returns the text that is shown in the component.
     */
    @Override
    public String text() {
        return text;
    }

    /**
     * Returns the type of component this component is.
     */
    @Override
    public ComponentType type() {
        return ComponentType.STEP_SLIDER;
    }
}
