package com.woutervdb.turbomodernity.versioning.semantic;

import com.woutervdb.turbomodernity.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PreReleaseIdentifierTest extends BaseTest {
    @Test
    public void it_rejects_empty_identifiers() {
        assertThrows(IllegalArgumentException.class, () -> PreReleaseIdentifier.of(""));
        assertThrows(IllegalArgumentException.class, () -> new PreReleaseIdentifier("", true));
        assertThrows(IllegalArgumentException.class, () -> new PreReleaseIdentifier("", false));
    }
}