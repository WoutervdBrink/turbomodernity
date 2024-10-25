package com.woutervdb.turbomodernity.versioning.simple;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleVersionTest extends BaseTest {
    static SimpleVersion[] ORDERED_VERSIONS = new SimpleVersion[]{
            new SimpleVersion(1),
            new SimpleVersion(2),
            new SimpleVersion(3),
            new SimpleVersion(4),
            new SimpleVersion(5),
    };

    @Test
    public void it_sets_the_version() {
        SimpleVersion v1 = new SimpleVersion(1);
        SimpleVersion v2 = new SimpleVersion(2);

        assertEquals(1, v1.getVersion());
        assertEquals(2, v2.getVersion());
    }

    @ParameterizedTest
    @MethodSource("orderedVersions")
    public void it_correctly_orders_versions(SimpleVersion older, SimpleVersion newer) {
        assertTrue(older.compareTo(newer) < 0);
        assertTrue(newer.compareTo(older) > 0);
    }

    @Test
    public void it_rejects_comparing_with_other_objects() {
        SimpleVersion version = new SimpleVersion(1);

        Version otherVersionType = new Version() {
            @Override
            public int compareTo(@NotNull Version version) {
                return 0;
            }
        };

        assertThrows(ClassCastException.class, () -> version.compareTo(otherVersionType));
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void it_tests_equality(SimpleVersion version) {
        SimpleVersion copy = new SimpleVersion(version.getVersion());

        assertEquals(version, copy);
        assertEquals(copy, version);
    }

    @Test
    public void it_rejects_equality_with_something_that_is_not_a_semantic_version() {
        SimpleVersion version = new SimpleVersion(1);
        Version otherVersionType = new Version() {
            @Override
            public int compareTo(@NotNull Version version) {
                return 0;
            }
        };

        assertNotEquals(version, otherVersionType);
    }

    public static Stream<SimpleVersion> versions() {
        return Stream.of(ORDERED_VERSIONS);
    }

    public static Stream<Arguments> orderedVersions() {
        return IntStream.range(1, ORDERED_VERSIONS.length)
                .mapToObj(i -> Arguments.arguments(ORDERED_VERSIONS[i - 1], ORDERED_VERSIONS[i]));
    }
}