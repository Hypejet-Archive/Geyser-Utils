package me.heroostech.geyserutils.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.heroostech.geyserutils.util.FormImage;

import java.io.Serializable;

/**
 * Button component is a component that can only be used in SimpleForm. With this component you can
 * show a button with an optional image attached to it.
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class ButtonComponent implements Serializable {
    @Getter private final String text;
    @Getter private FormImage image;
}