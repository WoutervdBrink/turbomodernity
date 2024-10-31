package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.versioning.Version;
import org.antlr.v4.runtime.RuleContext;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Language {
    private final List<Version> versions;
    private final VersionConstraintDeciderRepository deciderRepository;
    private boolean versionsAreSorted = true;

    public Language() {
        versions = new LinkedList<>();
        deciderRepository = new VersionConstraintDeciderRepository();

        for (Version version : getVersions()) {
            addVersion(version);
        }
    }

    protected <C extends RuleContext> void addDecider(Class<C> contextClass, VersionConstraintDecider<C> decider) {
        this.deciderRepository.addDecider(contextClass, decider);
    }

    protected Set<Version> getVersions() {
        return Set.of();
    }

    private void addVersion(@NotNull Version version) {
        versions.add(version);
        versionsAreSorted = false;
    }

    private void sortVersionsIfNeeded() {
        if (!versionsAreSorted) {
            versions.sort(Version::compareTo);
        }
    }

    public List<Version> versions() {
        sortVersionsIfNeeded();
        return new LinkedList<>(versions);
    }

    public Version getLowestVersion() {
        sortVersionsIfNeeded();
        return versions.getFirst();
    }

    public Version getHighestVersion() {
        sortVersionsIfNeeded();
        return versions.getLast();
    }
}
