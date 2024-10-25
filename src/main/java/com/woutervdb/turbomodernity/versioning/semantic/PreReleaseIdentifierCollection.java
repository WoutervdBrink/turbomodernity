package com.woutervdb.turbomodernity.versioning.semantic;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PreReleaseIdentifierCollection implements Comparable<PreReleaseIdentifierCollection> {
    private final PreReleaseIdentifier[] preReleaseIdentifiers;

    public PreReleaseIdentifierCollection(@NotNull PreReleaseIdentifier[] preReleaseIdentifiers) {
        this.preReleaseIdentifiers = preReleaseIdentifiers;
    }

    public PreReleaseIdentifier[] getPreReleaseIdentifiers() {
        return preReleaseIdentifiers.clone();
    }

    @Override
    public int compareTo(@NotNull PreReleaseIdentifierCollection other) {
        int myLength = preReleaseIdentifiers.length;
        int otherLength = other.preReleaseIdentifiers.length;

        if (myLength == 0 && otherLength == 0) {
            return 0;
        }

        if (myLength == 0 || otherLength == 0) {
            return myLength == 0 ? 1 : -1;
        }

        for (int i = 0; i < myLength && i < otherLength; i++) {
            int result = preReleaseIdentifiers[i].compareTo(other.preReleaseIdentifiers[i]);
            if (result != 0) {
                return result;
            }
        }

        return Integer.compare(myLength, otherLength);
    }

    @Override
    public String toString() {
        return (preReleaseIdentifiers.length > 0 ? "-" : "") +
                Arrays.stream(preReleaseIdentifiers).map(PreReleaseIdentifier::toString).collect(Collectors.joining("-"));
    }
}
