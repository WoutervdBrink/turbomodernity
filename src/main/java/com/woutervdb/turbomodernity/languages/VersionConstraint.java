package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.versioning.Version;

import java.util.Objects;

/**
 * A constraint for certain versions.
 *
 * @param minimum The minimum required version (inclusive).
 * @param maximum The maximum required version (inclusive).
 */
public record VersionConstraint(Version minimum, Version maximum) {
    public static VersionConstraint from(Version minimum) {
        return new VersionConstraint(minimum, null);
    }

    public static VersionConstraint to(Version maximum) {
        return new VersionConstraint(null, maximum);
    }

    public static VersionConstraint of(Version minimum, Version maximum) {
        return new VersionConstraint(minimum, maximum);
    }

    /**
     * Create a new, possibly stricter version constraint, based on another version constraint.
     *
     * <p>The new constraint limits versions to the newest lower bound and the oldest upper bound of the two versions.</p>
     *
     * <p>For example, merging <2, 5> and <3, 8> results in <3, 5>.</p>
     *
     * @param other The version constraint to merge with.
     * @return A new version constraint that is stricter than or as strict as this constraint and the given constraint.
     */
    public VersionConstraint merge(VersionConstraint other) {
        return new VersionConstraint(
                Version.newest(minimum, other.minimum),
                Version.oldest(maximum, other.maximum)
        );
    }

    /**
     * Check whether the constraint allows a specified version.
     *
     * @param version The version to check.
     * @return Whether the constraint allows this version.
     */
    public boolean allows(Version version) {
        return minimum.compareTo(version) <= 0 && maximum.compareTo(version) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionConstraint that)) return false;
        return Objects.equals(minimum, that.minimum) && Objects.equals(maximum, that.maximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }
}
