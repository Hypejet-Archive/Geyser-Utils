package me.heroostech.geyserutils.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.component.util.ComponentType;

@AllArgsConstructor
@RequiredArgsConstructor
public class SliderComponent implements Component {

    private final String text;
    @Getter private final float min;
    @Getter private final float max;
    @Getter private Float step;
    @Getter private Float defaultValue;

    public SliderComponent(String text, float min, float max, float step) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.step = step;
    }

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
        return ComponentType.SLIDER;
    }
}
