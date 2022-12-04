package me.heroostech.geyserutils.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.component.util.ComponentType;

@AllArgsConstructor
@RequiredArgsConstructor
public class InputComponent implements Component {

    @NonNull private final String text;
    @Getter private String placeholder;
    @Getter private String defaultText;

    public InputComponent(@NonNull String text, @NonNull String placeholder) {
        this.text = text;
        this.placeholder = placeholder;
    }

    /**
     * Returns the type of component this component is.
     */
    @Override
    public ComponentType type() {
        return ComponentType.INPUT;
    }

    /**
     * Returns the text that is shown in the component.
     */
    @Override
    public String text() {
        return text;
    }
}
