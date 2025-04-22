package com.woutervdb.turbomodernity.versioning.ranges;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;
import com.woutervdb.turbomodernity.versioning.Version;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VersionRangeTest extends BaseTest {
    public static Stream<Arguments> rangesWithAllowedVersions() {
        return Stream.of(
                Arguments.of(VersionRange.from(MockVersion.V2), false, true, true, true, true),
                Arguments.of(VersionRange.to(MockVersion.V4), true, true, true, true, false),
                Arguments.of(VersionRange.empty(), true, true, true, true, true),
                Arguments.of(VersionRange.between(MockVersion.V2, MockVersion.V4), false, true, true, true, false)
        );
    }

    public static Stream<Arguments> rangesAndTheirMergedRanges() {
        return Stream.of(
                Arguments.of(
                        VersionRange.between(MockVersion.V1, MockVersion.V2),
                        VersionRange.between(MockVersion.V2, MockVersion.V4),
                        VersionRange.between(MockVersion.V1, MockVersion.V4)
                ),
                Arguments.of(
                        VersionRange.between(MockVersion.V1, MockVersion.V2),
                        VersionRange.between(MockVersion.V2, MockVersion.V3),
                        VersionRange.between(MockVersion.V1, MockVersion.V3)
                ),
                Arguments.of(
                        VersionRange.from(MockVersion.V3),
                        VersionRange.between(MockVersion.V1, MockVersion.V3),
                        VersionRange.from(MockVersion.V1)
                ),
                Arguments.of(
                        VersionRange.to(MockVersion.V2),
                        VersionRange.from(MockVersion.V2),
                        VersionRange.empty()
                ),
                Arguments.of(
                        VersionRange.from(MockVersion.V2),
                        VersionRange.to(MockVersion.V2),
                        VersionRange.empty()
                ),
                Arguments.of(
                        VersionRange.from(MockVersion.V2),
                        VersionRange.from(MockVersion.V1),
                        VersionRange.from(MockVersion.V1)
                ),
                Arguments.of(
                        VersionRange.to(MockVersion.V3),
                        VersionRange.to(MockVersion.V4),
                        VersionRange.to(MockVersion.V4)
                )
        );
    }

    public static Stream<Arguments> nonOverlappingRanges() {
        return Stream.of(
                Arguments.of(
                        VersionRange.to(MockVersion.V2),
                        VersionRange.from(MockVersion.V3)
                ),
                Arguments.of(
                        VersionRange.between(MockVersion.V1, MockVersion.V2),
                        VersionRange.between(MockVersion.V3, MockVersion.V4)
                )
        );
    }

    @Test
    public void it_allows_constructing_without_specified_versions() {
        VersionRange v = VersionRange.empty();

        assertNull(v.minimum());
        assertNull(v.maximum());
    }

    @Test
    public void it_allows_constructing_with_minimum_versions() {
        VersionRange v1 = VersionRange.from(MockVersion.V1);

        assertEquals(MockVersion.V1, v1.minimum());
        assertNull(v1.maximum());
    }

    @Test
    public void it_allows_constructing_with_maximum_versions() {
        VersionRange v1 = VersionRange.to(MockVersion.V1);

        assertEquals(MockVersion.V1, v1.maximum());
        assertNull(v1.minimum());
    }

    @Test
    public void it_allows_constructing_with_minimum_and_maximum_versions() {
        VersionRange v1_v2 = VersionRange.between(MockVersion.V1, MockVersion.V2);

        assertEquals(MockVersion.V1, v1_v2.minimum());
        assertEquals(MockVersion.V2, v1_v2.maximum());
    }

    @Test
    public void it_rejects_constructing_with_minimum_that_is_higher_than_maximum() {
        assertThrows(IllegalArgumentException.class, () -> VersionRange.between(MockVersion.V2, MockVersion.V1));
    }

    @DisplayName("it checks allows versions")
    @ParameterizedTest(name = "[{index}] {0} allows versions v1 through v5: {1}, {2}, {3}, {4}, {5}")
    @MethodSource("rangesWithAllowedVersions")
    public void it_checks_allowed_versions(VersionRange range, boolean allows1, boolean allows2, boolean allows3, boolean allows4, boolean allows5) {
        assertEquals(allows1, range.allows(MockVersion.V1));
        assertEquals(allows2, range.allows(MockVersion.V2));
        assertEquals(allows3, range.allows(MockVersion.V3));
        assertEquals(allows4, range.allows(MockVersion.V4));
        assertEquals(allows5, range.allows(MockVersion.V5));
    }

    @DisplayName("it generates modernity signatures")
    @ParameterizedTest(name = "[{index}] {0} generates a signature with value 1.0 for versions v1 through v5: {1}, {2}, {3}, {4}, {5}")
    @MethodSource("rangesWithAllowedVersions")
    public void it_generated_modernity_signatures(VersionRange range, boolean allows1, boolean allows2, boolean allows3, boolean allows4, boolean allows5) {
        ModernitySignature sig = range.toModernitySignature(MockLanguage.INSTANCE);

        assertEquals(allows1 ? 1.0 : 0.0, sig.getValue(MockVersion.V1));
        assertEquals(allows2 ? 1.0 : 0.0, sig.getValue(MockVersion.V2));
        assertEquals(allows3 ? 1.0 : 0.0, sig.getValue(MockVersion.V3));
        assertEquals(allows4 ? 1.0 : 0.0, sig.getValue(MockVersion.V4));
        assertEquals(allows5 ? 1.0 : 0.0, sig.getValue(MockVersion.V5));
    }

    @DisplayName("it merges ranges")
    @ParameterizedTest(name = "[{index}] Merging {0} and {1} results in {2}")
    @MethodSource("rangesAndTheirMergedRanges")
    public void it_merges_ranges(VersionRange first, VersionRange second, VersionRange result) {
        assertEquals(result, first.merge(second));
    }

    @DisplayName("it rejects merging non-overlapping ranges")
    @ParameterizedTest(name = "[{index}] Merging {0} and {1} is not allowed")
    @MethodSource("nonOverlappingRanges")
    public void it_rejects_merging_non_overlapping_ranges(VersionRange first, VersionRange second) {
        assertThrows(IllegalArgumentException.class, () -> first.merge(second));
        assertThrows(IllegalArgumentException.class, () -> second.merge(first));
    }

    @Test
    public void it_tests_equality() {
        VersionRange from1a = VersionRange.from(MockVersion.V1);
        VersionRange from1b = VersionRange.from(MockVersion.V1);
        VersionRange from2 = VersionRange.from(MockVersion.V2);

        VersionRange to1a = VersionRange.to(MockVersion.V1);
        VersionRange to1b = VersionRange.to(MockVersion.V1);
        VersionRange to2 = VersionRange.to(MockVersion.V2);

        VersionRange between23a = VersionRange.between(MockVersion.V2, MockVersion.V3);
        VersionRange between23b = VersionRange.between(MockVersion.V2, MockVersion.V3);
        VersionRange between34 = VersionRange.between(MockVersion.V3, MockVersion.V4);

        VersionRange empty1 = VersionRange.empty();
        VersionRange empty2 = VersionRange.empty();

        assertEquals(from1a, from1b);
        assertEquals(from1b, from1a);
        assertNotEquals(from1a, from2);
        assertNotEquals(from2, from1a);

        assertEquals(to1a, to1b);
        assertEquals(to1b, to1a);
        assertNotEquals(to1a, to2);
        assertNotEquals(to2, to1a);

        assertEquals(between23a, between23b);
        assertEquals(between23b, between23a);
        assertNotEquals(between23a, between34);
        assertNotEquals(between34, between23a);

        assertEquals(empty1, empty2);
        assertEquals(empty2, empty1);

        assertEquals(from1a, from1a);
    }

}