package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.versioning.Version;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Language<V extends Version> {
    private final List<V> versions;
    private boolean versionsAreSorted = true;

    public Language() {
        versions = new LinkedList<>();
    }

    public void addVersion(@NotNull V version) {
        versions.add(version);
        versionsAreSorted = false;
    }

    private void sortVersionsIfNeeded() {
        if (!versionsAreSorted) {
            versions.sort(V::compareTo);
        }
    }

    public List<V> versions() {
        sortVersionsIfNeeded();
        return new LinkedList<>(versions);
    }
}
