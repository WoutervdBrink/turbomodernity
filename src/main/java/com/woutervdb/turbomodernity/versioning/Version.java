package com.woutervdb.turbomodernity.versioning;

public abstract class Version implements Comparable<Version> {
    public static Version newest(Version v1, Version v2) {
        if (v1.compareTo(v2) > 0) {
            return v1;
        }
        return v2;
    }

    public static Version oldest(Version v1, Version v2) {
        if (v1.compareTo(v2) < 0) {
            return v1;
        }
        return v2;
    }
}
