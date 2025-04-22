package com.woutervdb.turbomodernity.languages;

import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;
import com.woutervdb.turbomodernity.versioning.ranges.VersionRangeDecider;
import com.woutervdb.turbomodernity.versioning.ranges.VersionRangeDeciderCollection;
import org.antlr.v4.runtime.RuleContext;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Model of a language.
 *
 * <p>Implementations of this class SHOULD keep their constructors private and expose a single instance through a static
 * field. The private constructor SHOULD use {@link Language#addVersion(Version)} to add the language's versions. The
 * versions MAY be exposed through static fields. Finally, {@link Language#addDecider(Class, VersionRangeDecider)}
 * SHOULD be called for every context class in the language's grammar, so that the version ranges can be decided.</p>
 */
public abstract class Language {
    private final List<Version> versions;
    private final VersionRangeDeciderCollection deciderRepository;
    private boolean versionsAreSorted = true;

    public Language() {
        versions = new LinkedList<>();
        deciderRepository = new VersionRangeDeciderCollection();

        for (Version version : getVersions()) {
            addVersion(version);
        }
    }

    public VersionRangeDeciderCollection getDeciderRepository() {
        return deciderRepository;
    }

    protected <C extends RuleContext> void addDecider(Class<C> contextClass, VersionRangeDecider<C> decider) {
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
}
