package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.versioning.Version;
import com.woutervdb.turbomodernity.versioning.simple.SimpleVersion;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageTest extends BaseTest {
    @Test
    public void it_keeps_sorted_versions_sorted() {
        SimpleVersion v1 = new SimpleVersion(1);
        SimpleVersion v2 = new SimpleVersion(2);
        SimpleVersion v3 = new SimpleVersion(3);

        Language language = new Language() {
            @Override
            protected Set<Version> getVersions() {
                return Set.of(v1, v2, v3);
            }
        };

        assertEquals(List.of(v1, v2, v3), language.versions());
    }

    @Test
    public void it_sorts_unsorted_versions() {
        SimpleVersion v1 = new SimpleVersion(1);
        SimpleVersion v2 = new SimpleVersion(2);
        SimpleVersion v3 = new SimpleVersion(3);

        Language language = new Language() {
            protected Set<Version> getVersions() {
                return Set.of(v3, v2, v1);
            }
        };

        assertEquals(List.of(v1, v2, v3), language.versions());
    }
}