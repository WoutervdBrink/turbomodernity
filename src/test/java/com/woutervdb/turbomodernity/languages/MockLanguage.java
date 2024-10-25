package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.versioning.MockVersion;

public final class MockLanguage extends Language<MockVersion> {
    public static final MockLanguage INSTANCE = new MockLanguage();

    private MockLanguage() {
        addVersion(MockVersion.V1);
        addVersion(MockVersion.V2);
        addVersion(MockVersion.V3);
        addVersion(MockVersion.V4);
        addVersion(MockVersion.V5);
    }
}
