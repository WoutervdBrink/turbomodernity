package com.woutervdb.turbomodernity.versioning;

import org.jetbrains.annotations.NotNull;

public abstract class Version implements Comparable<Version> {
    public static @NotNull Version newest(@NotNull Version v1, @NotNull Version v2) {
        if (v1.compareTo(v2) > 0) {
            return v1;
        }

        return v2;
    }

    public static @NotNull Version oldest(@NotNull Version v1, @NotNull Version v2) {
        if (v1.compareTo(v2) < 0) {
            return v1;
        }

        return v2;
    }
}
