package me.heroostech.floodgate.utils;

import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.floodgate.VelocityFloodgate;
import org.geysermc.cumulus.component.*;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomFormConverter {
    public static CustomForm convert(me.heroostech.floodgate.forms.CustomForm form) {
        var f = CustomForm.builder();
        f.title(form.title());
        f.icon(FormImage.Type.valueOf(form.icon().type().getName()), form.icon().data());
        componentConvert(form.content()).forEach(f::component);
        f.validResultHandler((aForm, response) -> {
            try {
                var player = VelocityFloodgate.INSTANCE.getServer().getPlayer(form.player());
                if (player.isPresent()) {
                    var pl = player.get();

                    var byteStream = new ByteArrayOutputStream();
                    var stream = new ObjectOutputStream(byteStream);

                    stream.writeObject(form);
                    stream.writeObject(serializeResponse(response));

                    stream.close();

                    var server = pl.getCurrentServer();

                    server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(MinecraftChannelIdentifier.from("minestomfloodgate:formresponse"), byteStream.toByteArray()));

                    byteStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return f.build();
    }

    private static List<Component> componentConvert(Collection<me.heroostech.floodgate.component.Component> components) {
        return components.stream()
                .map(component -> {
                    switch (component.type()) {
                        case TOGGLE -> {
                            return toggleConvert((me.heroostech.floodgate.component.ToggleComponent) component);
                        }

                        case SLIDER -> {
                            return sliderConvert((me.heroostech.floodgate.component.SliderComponent) component);
                        }

                        case LABEL -> {
                            return labelConvert((me.heroostech.floodgate.component.LabelComponent) component);
                        }

                        case INPUT -> {
                            return inputConvert((me.heroostech.floodgate.component.InputComponent) component);
                        }

                        case DROPDOWN -> {
                            return dropdownConvert((me.heroostech.floodgate.component.DropdownComponent) component);
                        }

                        case STEP_SLIDER -> {
                            return stepSliderConvert((me.heroostech.floodgate.component.StepSliderComponent) component);
                        }
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    private static ToggleComponent toggleConvert(me.heroostech.floodgate.component.ToggleComponent component) {
        if(component.getDefaultValue() == null) {
            return ToggleComponent.of(component.text());
        }
        return ToggleComponent.of(component.text(), component.getDefaultValue());
    }

    private static SliderComponent sliderConvert(me.heroostech.floodgate.component.SliderComponent component) {
        if(component.getDefaultValue() != null) {
            return SliderComponent.of(component.text(), component.getMin(), component.getMax(), component.getStep(), component.getDefaultValue());
        }

        if(component.getStep() != null) {
            return SliderComponent.of(component.text(), component.getMin(), component.getMax(), component.getStep());
        }

        return SliderComponent.of(component.text(), component.getMin(), component.getMax());
    }

    private static LabelComponent labelConvert(me.heroostech.floodgate.component.LabelComponent component) {
        return LabelComponent.of(component.text());
    }

    private static InputComponent inputConvert(me.heroostech.floodgate.component.InputComponent component) {
        return InputComponent.of(component.text());
    }

    private static DropdownComponent dropdownConvert(me.heroostech.floodgate.component.DropdownComponent component) {
        return DropdownComponent.of(component.text(), component.options(), component.defaultOption());
    }

    private static StepSliderComponent stepSliderConvert(me.heroostech.floodgate.component.StepSliderComponent component) {
        return StepSliderComponent.of(component.text(), component.steps(), component.defaultStep());
    }

    private static List<me.heroostech.floodgate.component.Component> componentConvertResponse(Collection<Component> components) {
        return components.stream()
                .map(component -> {
                    switch (component.type()) {
                        case TOGGLE -> {
                            return toggleConvert((ToggleComponent) component);
                        }

                        case SLIDER -> {
                            return sliderConvert((SliderComponent) component);
                        }

                        case LABEL -> {
                            return labelConvert((LabelComponent) component);
                        }

                        case INPUT -> {
                            return inputConvert((InputComponent) component);
                        }

                        case DROPDOWN -> {
                            return dropdownConvert((DropdownComponent) component);
                        }

                        case STEP_SLIDER -> {
                            return stepSliderConvert((StepSliderComponent) component);
                        }
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    private static me.heroostech.floodgate.component.ToggleComponent toggleConvert(ToggleComponent component) {
        return new me.heroostech.floodgate.component.ToggleComponent(component.text(), component.defaultValue());
    }

    private static me.heroostech.floodgate.component.SliderComponent sliderConvert(SliderComponent component) {
        return new me.heroostech.floodgate.component.SliderComponent(component.text(), component.minValue(), component.maxValue(), component.step(), component.defaultValue());
    }

    private static me.heroostech.floodgate.component.LabelComponent labelConvert(LabelComponent component) {
        return new me.heroostech.floodgate.component.LabelComponent(component.text());
    }

    private static me.heroostech.floodgate.component.InputComponent inputConvert(InputComponent component) {
        return new me.heroostech.floodgate.component.InputComponent(component.text(), component.placeholder(), component.defaultText());
    }

    private static me.heroostech.floodgate.component.DropdownComponent dropdownConvert(DropdownComponent component) {
        return new me.heroostech.floodgate.component.DropdownComponent(component.text(), component.options(), component.defaultOption());
    }

    private static me.heroostech.floodgate.component.StepSliderComponent stepSliderConvert(StepSliderComponent component) {
        return new me.heroostech.floodgate.component.StepSliderComponent(component.text(), component.steps(), component.defaultStep());
    }

    private static List<me.heroostech.floodgate.component.Component> serializeResponse(CustomFormResponse response) {
        List<Component> components = new ArrayList<>();
        do {
            Component component = response.next();
            components.add(component);
        } while (response.hasNext());
        return componentConvertResponse(components);
    }
}
