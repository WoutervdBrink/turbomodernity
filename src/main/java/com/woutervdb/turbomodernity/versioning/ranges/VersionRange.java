package com.woutervdb.turbomodernity.versioning.ranges;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A range for certain versions.
 *
 * @param minimum The minimum required version (inclusive).
 * @param maximum The maximum required version (inclusive).
 */
public record VersionRange(@Nullable Version minimum, @Nullable Version maximum) {
    /**
     * Construct a version range that encompasses all versions starting from a certain version (inclusive).
     *
     * @param minimum The minimum version (inclusive).
     * @return A version range going from the specified version (inclusive).
     */
    public static VersionRange from(@NotNull Version minimum) {
        return new VersionRange(minimum, null);
    }

    /**
     * Construct a version range that encompasses all versions up to a certain version (inclusive).
     *
     * @param maximum The maximum version (inclusive).
     * @return A version range going up to the specified version (inclusive).
     */
    public static VersionRange to(@NotNull Version maximum) {
        return new VersionRange(null, maximum);
    }

    /**
     * Construct a version range that encompasses all versions starting from a certain version, up to a certain
     * version (inclusive).
     *
     * @param minimum The minimum version (inclusive).
     * @param maximum The maximum version (inclusive).
     * @return A version range going from the first specified version up to the second specified version
     * (inclusive).
     */
    public static VersionRange between(@NotNull Version minimum, @NotNull Version maximum) {
        if (minimum.compareTo(maximum) > 0) {
            throw new IllegalArgumentException("minimum must be less than maximum");
        }

        return new VersionRange(minimum, maximum);
    }

    public static VersionRange empty() {
        return new VersionRange(null, null);
    }

    /**
     * Create a new, possibly stricter version range, based on another version range.
     *
     * <p>The new range limits versions to the newest lower bound and the oldest upper bound of the two versions.</p>
     *
     * <p>The version ranges must overlap. If not, an {@link IllegalArgumentException} is thrown.</p>
     *
     * <p>For example, merging <2, 5> and <3, 8> results in <3, 5>.</p>
     *
     * @param other The version range to merge with.
     * @return A new version range that is stricter than or as strict as this range and the given range.
     * @throws IllegalArgumentException if the version ranges do not overlap.
     */
    public VersionRange merge(VersionRange other) {
        if (maximum != null && other.minimum != null && maximum.compareTo(other.minimum) < 0) {
            throw new IllegalArgumentException("Version ranges must overlap");
        }

        if (minimum != null && other.maximum != null && minimum.compareTo(other.maximum) > 0) {
            throw new IllegalArgumentException("Version ranges must overlap");
        }

        Version oldest = minimum != null && other.minimum != null
                ? Version.oldest(minimum, other.minimum)
                : null;

        Version newest = maximum != null && other.maximum != null
                ? Version.newest(maximum, other.maximum)
                : null;

        return new VersionRange(oldest, newest);
    }

    /**
     * Check whether the range allows a specified version.
     *
     * @param version The version to check.
     * @return Whether the range allows this version.
     */
    public boolean allows(Version version) {
        if (minimum != null && minimum.compareTo(version) > 0) {
            return false;
        }

        return maximum == null || maximum.compareTo(version) >= 0;
    }

    /**
     * Convert this version range to a modernity signature.
     *
     * @param language The language for which to construct the modernity signature.
     * @return A modernity signature with value 1.0 for any version that is allowed by this version range, and 0.0 for
     * any version which is not allowed by this version range.
     */
    public ModernitySignature toModernitySignature(Language language) {
        ModernitySignature sig = new ModernitySignature(language);

        for (Version v : language.versions()) {
            sig.setValue(v, this.allows(v) ? 1.0 : 0.0);
        }

        return sig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionRange(Version otherMinimum, Version otherMaximum))) return false;
        return Objects.equals(minimum, otherMinimum) && Objects.equals(maximum, otherMaximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }
}
