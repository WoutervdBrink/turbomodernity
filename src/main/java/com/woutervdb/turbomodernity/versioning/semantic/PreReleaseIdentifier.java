package com.woutervdb.turbomodernity.versioning.semantic;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PreReleaseIdentifier implements Comparable<PreReleaseIdentifier> {
    private final String identifier;
    private final boolean isNumeric;

    public PreReleaseIdentifier(String identifier) {
        this(identifier, null);
    }

    public PreReleaseIdentifier(String identifier, Boolean isNumeric) {
        if (identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be empty");
        }

        this.identifier = identifier;
        this.isNumeric = Optional.ofNullable(isNumeric).orElse(isNumeric(identifier));
    }

    private static boolean isNumeric(@NotNull String identifier) {
        char[] chars = identifier.toCharArray();

        if (chars.length == 1 && chars[0] >= '0' && chars[0] <= '9') {
            return true;
        }

        if (chars[0] < '1' || chars[0] > '9') {
            return false;
        }

        for (int i = 1; i < chars.length; i++) {
            // Identifiers can only contain /0-9a-zA-Z/, and 0 is already the lowest ASCII character.
            if (chars[i] > '9') {
                return false;
            }
        }

        return true;
    }

    public static PreReleaseIdentifier of(@NotNull String identifier) {
        return new PreReleaseIdentifier(identifier);
    }

    @Override
    public int compareTo(@NotNull PreReleaseIdentifier other) {
        if (isNumeric && other.isNumeric) {
            return Integer.compare(
                    Integer.parseInt(identifier),
                    Integer.parseInt(other.identifier)
            );
        }
        return identifier.compareTo(other.identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
