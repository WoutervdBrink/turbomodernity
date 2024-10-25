package com.woutervdb.turbomodernity.versioning.semantic;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import  org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SemanticVersionTest extends BaseTest {
    static SemanticVersion[] ORDERED_VERSIONS = new SemanticVersion[] {
            new SemanticVersion(1, 0, 0, "alpha"),
            new SemanticVersion(1, 0, 0, "alpha", "1"),
            new SemanticVersion(1, 0, 0, "alpha", "beta"),
            new SemanticVersion(1, 0, 0, "beta"),
            new SemanticVersion(1, 0, 0, "beta", "2"),
            new SemanticVersion(1, 0, 0, "beta", "11"),
            new SemanticVersion(1, 0, 0, "rc", "1"),
            new SemanticVersion(1, 0, 0),
            new SemanticVersion(1, 0, 1),
            new SemanticVersion(1, 0, 2),
            new SemanticVersion(1, 0, 25),
            new SemanticVersion(1, 1, 0),
            new SemanticVersion(2, 0, 0),
            new SemanticVersion(3, 0, 0, "#"),
            new SemanticVersion(3, 0, 0, "##"),
            new SemanticVersion(3, 0, 0, "01"),
            new SemanticVersion(3, 0, 0, "010"),
            new SemanticVersion(3, 0, 0, "1A"),
            new SemanticVersion(3, 0, 0, "2"),
            new SemanticVersion(3, 0, 0, "A"),
            new SemanticVersion(3, 0, 0, "AA"),
    };

    @ParameterizedTest(name = "[{index}] {0} is older than {1}")
    @MethodSource("orderedVersions")
    public void it_correctly_orders_versions(SemanticVersion older, SemanticVersion newer) {
        assertTrue(older.compareTo(newer) < 0);
        assertTrue(newer.compareTo(older) > 0);
    }

    @Test
    public void it_rejects_comparing_with_other_objects() {
        SemanticVersion semanticVersion = new SemanticVersion(1, 0, 0, "alpha");

        Version otherVersionType = new Version() {
            @Override
            public int compareTo(@NotNull Version version) {
                return 0;
            }
        };

        assertThrows(ClassCastException.class, () -> semanticVersion.compareTo(otherVersionType));
    }

    @Test
    public void it_defaults_unspecified_version_fields_to_zero() {
        SemanticVersion justMajor = new SemanticVersion(1);
        SemanticVersion majorAndMinor = new SemanticVersion(1, 2);

        assertEquals(0, justMajor.getCore().minor());
        assertEquals(0, justMajor.getCore().patch());

        assertEquals(0, majorAndMinor.getCore().patch());
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void it_tests_equality(SemanticVersion version) {
        VersionCore core = version.getCore();
        PreReleaseIdentifierCollection preReleaseIdentifiers = version.getPreReleaseIdentifiers();

        SemanticVersion copy = new SemanticVersion(core.major(), core.minor(), core.patch(), preReleaseIdentifiers.getPreReleaseIdentifiers());

        assertEquals(version, copy);
        assertEquals(copy, version);
    }

    @Test
    public void it_rejects_equality_with_something_that_is_not_a_semantic_version() {
        SemanticVersion version = new SemanticVersion(1, 0, 0, "alpha");
        Version otherVersionType = new Version() {
            @Override
            public int compareTo(@NotNull Version version) {
                return 0;
            }
        };

        assertNotEquals(version, otherVersionType);
    }

    public static Stream<SemanticVersion> versions() {
        return Stream.of(ORDERED_VERSIONS);
    }

    public static Stream<Arguments> orderedVersions() {
        return IntStream.range(1, ORDERED_VERSIONS.length)
                .mapToObj(i -> Arguments.arguments(ORDERED_VERSIONS[i - 1], ORDERED_VERSIONS[i]));
    }
}
