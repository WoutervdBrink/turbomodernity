package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.versioning.simple.SimpleVersion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest extends BaseTest {
    @Test
    public void it_keeps_sorted_versions_sorted() {
        Language<SimpleVersion> language = new Language<>();

        SimpleVersion v1 = new SimpleVersion(1);
        SimpleVersion v2 = new SimpleVersion(2);
        SimpleVersion v3 = new SimpleVersion(3);

        language.addVersion(v1);
        language.addVersion(v2);
        language.addVersion(v3);

        assertEquals(List.of(v1, v2, v3), language.versions());
    }

    @Test
    public void it_sorts_unsorted_versions() {
        Language<SimpleVersion> language = new Language<>();

        SimpleVersion v1 = new SimpleVersion(1);
        SimpleVersion v2 = new SimpleVersion(2);
        SimpleVersion v3 = new SimpleVersion(3);

        language.addVersion(v2);
        language.addVersion(v1);
        language.addVersion(v3);

        assertEquals(List.of(v1, v2, v3), language.versions());
    }
}