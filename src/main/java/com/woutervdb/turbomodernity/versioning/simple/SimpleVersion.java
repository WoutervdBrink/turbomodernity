package com.woutervdb.turbomodernity.versioning.simple;

import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;

public class SimpleVersion extends Version {
    private final int version;

    public SimpleVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleVersion other)) {
            return false;
        }

        return version == other.getVersion();
    }

    @Override
    public int compareTo(@NotNull Version o) {
        if (!(o instanceof SimpleVersion simpleVersion)) {
            throw new ClassCastException();
        }

        return Integer.compare(version, simpleVersion.version);
    }
}
