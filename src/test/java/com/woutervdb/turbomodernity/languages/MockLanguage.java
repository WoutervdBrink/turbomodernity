package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.versioning.MockVersion;
import com.woutervdb.turbomodernity.versioning.Version;

import java.util.Set;

public final class MockLanguage extends Language {
    public static final MockLanguage INSTANCE = new MockLanguage();

    @Override
    protected Set<Version> getVersions() {
        return Set.of(MockVersion.V1, MockVersion.V2, MockVersion.V3, MockVersion.V4, MockVersion.V5);
    }
}
