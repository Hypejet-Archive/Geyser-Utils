package me.heroostech.geyserutils.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.component.util.ComponentType;

@AllArgsConstructor
@RequiredArgsConstructor
public class ToggleComponent implements Component {
    private final String text;
    @Getter private Boolean defaultValue;

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
        return ComponentType.TOGGLE;
    }
}
