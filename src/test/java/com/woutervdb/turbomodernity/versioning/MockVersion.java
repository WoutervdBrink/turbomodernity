package com.woutervdb.turbomodernity.versioning;

import org.jetbrains.annotations.NotNull;

public final class MockVersion extends Version {
    public static final MockVersion V1 = new MockVersion(1);
    public static final MockVersion V2 = new MockVersion(2);
    public static final MockVersion V3 = new MockVersion(3);
    public static final MockVersion V4 = new MockVersion(4);
    public static final MockVersion V5 = new MockVersion(5);
    private final int version;

    private MockVersion(int version) {
        this.version = version;
    }

    @Override
    public int compareTo(@NotNull Version o) {
        if (!(o instanceof MockVersion other)) {
            throw new ClassCastException();
        }

        return Integer.compare(version, other.version);
    }
}
