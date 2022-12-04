package me.heroostech.geyserutils.util;

import lombok.Getter;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public record FormImage(Type type, String data) implements Serializable {
    public enum Type implements Serializable {
        PATH, URL;

        private static final Type[] VALUES = values();

        @Getter private final String name = name().toLowerCase(Locale.ROOT);

        public static Type fromName(String name) {
            Objects.requireNonNull(name, "name");
            for (Type value : VALUES) {
                if (value.getName().equals(name)) {
                    return value;
                }
            }
            return null;
        }
    }
}
