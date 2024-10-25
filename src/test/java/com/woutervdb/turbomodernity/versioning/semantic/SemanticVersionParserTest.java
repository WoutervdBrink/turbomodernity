package com.woutervdb.turbomodernity.versioning.semantic;

import com.woutervdb.turbomodernity.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SemanticVersionParserTest extends BaseTest {
    public static Stream<Arguments> versions() {
        return Stream.of(
                Arguments.arguments("1.0.0", 1, 0, 0, new PreReleaseIdentifier[0]),
                Arguments.arguments("1.0.0-beta", 1, 0, 0, new PreReleaseIdentifier[]{new PreReleaseIdentifier("beta", false)}),
                Arguments.arguments("1.2.3-rc.4.5.6", 1, 2, 3, new PreReleaseIdentifier[]{
                        new PreReleaseIdentifier("rc", false),
                        new PreReleaseIdentifier("4", true),
                        new PreReleaseIdentifier("5", true),
                        new PreReleaseIdentifier("6", true),
                }),
                Arguments.arguments("4.5.6-rc1+build", 4, 5, 6, new PreReleaseIdentifier[]{
                        new PreReleaseIdentifier("rc1", false),
                }),
                Arguments.arguments("6.7.8+build", 6, 7, 8, new PreReleaseIdentifier[0])
        );
    }

    public static Stream<String> invalidVersions() {
        return Stream.of(
                "1.x",
                "",
                "v1.2.3",
                "01.2.3"
        );
    }

    @ParameterizedTest
    @MethodSource("versions")
    public void it_parses_semver_versions(String version, int major, int minor, int patch, PreReleaseIdentifier... identifiers) {
        SemanticVersion parsed = SemanticVersionParser.fromVersionString(version);
        SemanticVersion expected = new SemanticVersion(major, minor, patch, identifiers);
        assertEquals(expected, parsed);
    }

    @ParameterizedTest
    @MethodSource("invalidVersions")
    public void it_rejects_invalid_semver_versions(String invalid) {
        assertThrows(IllegalArgumentException.class, () -> SemanticVersionParser.fromVersionString(invalid));
    }
}
