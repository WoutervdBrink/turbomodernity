package com.woutervdb.turbomodernity.versioning.semantic;

import org.jetbrains.annotations.NotNull;

public record VersionCore(int major, int minor, int patch) implements Comparable<VersionCore> {
    @Override
    public int compareTo(@NotNull VersionCore other) {
        if (this.major != other.major) {
            return Integer.compare(this.major, other.major);
        }

        if (this.minor != other.minor) {
            return Integer.compare(this.minor, other.minor);
        }

        if (this.patch != other.patch) {
            return Integer.compare(this.patch, other.patch);
        }

        return 0;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
