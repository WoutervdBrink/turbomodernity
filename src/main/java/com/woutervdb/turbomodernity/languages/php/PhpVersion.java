package com.woutervdb.turbomodernity.languages.php;

import com.woutervdb.turbomodernity.versioning.semantic.SemanticVersion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class PhpVersion extends SemanticVersion {
    private final Date releasedAt;
    private final Date supportedUntil;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static PhpVersion of(int major, int minor, String releasedAt, String supportedUntil) {
        Date releaseDate;
        Date supportedUntilDate;

        try {
            releaseDate = dateFormat.parse(releasedAt);
        } catch (ParseException e) {
            releaseDate = null;
        }

        try {
            supportedUntilDate = dateFormat.parse(supportedUntil);
        } catch (ParseException e) {
            supportedUntilDate = null;
        }

        return new PhpVersion(major, minor, releaseDate, supportedUntilDate);
    }

    private PhpVersion(int major, int minor, Date releasedAt, Date supportedUntil) {
        super(major, minor, 0);
        this.releasedAt = releasedAt;
        this.supportedUntil = supportedUntil;
    }

    public Date getReleasedAt() {
        return releasedAt;
    }

    public Date getSupportedUntil() {
        return supportedUntil;
    }

    public boolean isReleased() {
        return releasedAt != null && releasedAt.before(new Date());
    }

    public boolean isSupported() {
        return supportedUntil != null && supportedUntil.after(new Date());
    }
}
