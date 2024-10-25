package com.woutervdb.turbomodernity.versioning.semantic;

import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class SemanticVersion extends Version {
    private final VersionCore core;
    private final PreReleaseIdentifierCollection preReleaseIdentifiers;

    public SemanticVersion(int major) {
        this(major, 0, 0);
    }

    public SemanticVersion(int major, int minor) {
        this(major, minor, 0);
    }

    public SemanticVersion(int major, int minor, int patch, String... preReleaseIdentifiers) {
        this(major, minor, patch, Arrays.stream(preReleaseIdentifiers).map(PreReleaseIdentifier::of).toArray(PreReleaseIdentifier[]::new));
    }

    public SemanticVersion(int major, int minor, int patch, PreReleaseIdentifier[] preReleaseIdentifiers) {
        this.core = new VersionCore(major, minor, patch);
        this.preReleaseIdentifiers = new PreReleaseIdentifierCollection(preReleaseIdentifiers);
    }

    public int compareTo(@NotNull Version o) {
        if (!(o instanceof SemanticVersion other)) {
            throw new ClassCastException();
        }

        int result = 0;

        result = core.compareTo(other.core);
        if (result != 0) return result;

        return preReleaseIdentifiers.compareTo(other.preReleaseIdentifiers);
    }

    public VersionCore getCore() {
        return core;
    }

    public PreReleaseIdentifierCollection getPreReleaseIdentifiers() {
        return preReleaseIdentifiers;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SemanticVersion)) {
            return false;
        }

        return this.compareTo((SemanticVersion) obj) == 0;
    }

    @Override
    public String toString() {
        return core.toString() + preReleaseIdentifiers.toString();
    }
}
