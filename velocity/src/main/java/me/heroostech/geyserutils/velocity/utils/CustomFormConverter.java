package me.heroostech.geyserutils.velocity.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.heroostech.geyserutils.velocity.VelocityFloodgate;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomFormConverter {
    public static CustomForm convert(me.heroostech.geyserutils.forms.CustomForm form) {
        CustomForm.Builder f = CustomForm.builder();
        f.title(form.title());
        f.icon(FormImage.Type.valueOf(form.icon().type().getName()), form.icon().data());
        componentConvert(form.content()).forEach(f::component);
        f.validResultHandler((aForm, response) -> {
            try {
                Optional<Player> player = VelocityFloodgate.INSTANCE.server().getPlayer(form.player());
                if (player.isPresent()) {
                    Player pl = player.get();

                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    ObjectOutputStream stream = new ObjectOutputStream(byteStream);

                    stream.writeObject(form);
                    stream.writeObject(serializeResponse(response));

                    stream.close();

                    Optional<ServerConnection> server = pl.getCurrentServer();

                    server.ifPresent(serverConnection -> serverConnection.sendPluginMessage(MinecraftChannelIdentifier.from("geyserutils:response"), byteStream.toByteArray()));

                    byteStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return f.build();
    }

    private static List<Component> componentConvert(Collection<me.heroostech.geyserutils.component.Component> components) {
        return components.stream()
                .map(component -> {
                    switch (component.type()) {
                        case TOGGLE -> {
                            return toggleConvert((me.heroostech.geyserutils.component.ToggleComponent) component);
                        }

                        case SLIDER -> {
                            return sliderConvert((me.heroostech.geyserutils.component.SliderComponent) component);
                        }

                        case LABEL -> {
                            return labelConvert((me.heroostech.geyserutils.component.LabelComponent) component);
                        }

                        case INPUT -> {
                            return inputConvert((me.heroostech.geyserutils.component.InputComponent) component);
                        }

                        case DROPDOWN -> {
                            return dropdownConvert((me.heroostech.geyserutils.component.DropdownComponent) component);
                        }

                        case STEP_SLIDER -> {
                            return stepSliderConvert((me.heroostech.geyserutils.component.StepSliderComponent) component);
                        }
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    private static ToggleComponent toggleConvert(me.heroostech.geyserutils.component.ToggleComponent component) {
        if(component.getDefaultValue() == null) {
            return ToggleComponent.of(component.text());
        }
        return ToggleComponent.of(component.text(), component.getDefaultValue());
    }

    private static SliderComponent sliderConvert(me.heroostech.geyserutils.component.SliderComponent component) {
        if(component.getDefaultValue() != null) {
            return SliderComponent.of(component.text(), component.getMin(), component.getMax(), component.getStep(), component.getDefaultValue());
        }

        if(component.getStep() != null) {
            return SliderComponent.of(component.text(), component.getMin(), component.getMax(), component.getStep());
        }

        return SliderComponent.of(component.text(), component.getMin(), component.getMax());
    }

    private static LabelComponent labelConvert(me.heroostech.geyserutils.component.LabelComponent component) {
        return LabelComponent.of(component.text());
    }

    private static InputComponent inputConvert(me.heroostech.geyserutils.component.InputComponent component) {
        return InputComponent.of(component.text());
    }

    private static DropdownComponent dropdownConvert(me.heroostech.geyserutils.component.DropdownComponent component) {
        return DropdownComponent.of(component.text(), component.options(), component.defaultOption());
    }

    private static StepSliderComponent stepSliderConvert(me.heroostech.geyserutils.component.StepSliderComponent component) {
        return StepSliderComponent.of(component.text(), component.steps(), component.defaultStep());
    }

    private static Object[] serializeResponse(CustomFormResponse response) {
        List<Object> objects = new ArrayList<>();
        do {
            Object obj = response.next();
            objects.add(obj);
        } while (response.hasNext());
        return objects.toArray(new Object[0]);
    }
}
