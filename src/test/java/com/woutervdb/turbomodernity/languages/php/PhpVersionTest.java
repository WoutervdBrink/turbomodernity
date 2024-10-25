package com.woutervdb.turbomodernity.languages.php;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PhpVersionTest {
    @Test
    public void it_stores_version_components() {
        PhpVersion version = PhpVersion.of(1, 2, "2024-01-02", "2025-03-04");

        Calendar releasedAt = Calendar.getInstance();
        releasedAt.setTime(version.getReleasedAt());

        Calendar supportedUntil = Calendar.getInstance();
        supportedUntil.setTime(version.getSupportedUntil());

        assertEquals(1, version.getCore().major());
        assertEquals(2, version.getCore().minor());
        assertEquals(0, version.getCore().patch());

        assertEquals(2024, releasedAt.get(Calendar.YEAR));
        assertEquals(0, releasedAt.get(Calendar.MONTH));
        assertEquals(2, releasedAt.get(Calendar.DAY_OF_MONTH));

        assertEquals(2025, supportedUntil.get(Calendar.YEAR));
        assertEquals(2, supportedUntil.get(Calendar.MONTH));
        assertEquals(4, supportedUntil.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void it_checks_support_status() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, -1);
        Date oneYearAgo = c.getTime();
        c.add(Calendar.YEAR, 2);
        Date oneYearFromNow = c.getTime();

        PhpVersion unsupported = PhpVersion.of(1, 2, "2024-01-02", new SimpleDateFormat("yyyy-MM-dd").format(oneYearAgo));
        PhpVersion supported = PhpVersion.of(1, 2, "2024-01-02", new SimpleDateFormat("yyyy-MM-dd").format(oneYearFromNow));

        assertFalse(unsupported.isSupported());
        assertTrue(supported.isSupported());
    }

    @Test
    public void it_checks_released_status() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, -1);
        Date oneYearAgo = c.getTime();
        c.add(Calendar.YEAR, 2);
        Date oneYearFromNow = c.getTime();

        PhpVersion unreleased = PhpVersion.of(1, 2, new SimpleDateFormat("yyyy-MM-dd").format(oneYearFromNow), "2024-01-02");
        PhpVersion released = PhpVersion.of(1, 2,  new SimpleDateFormat("yyyy-MM-dd").format(oneYearAgo), "2024-01-02");

        assertFalse(unreleased.isReleased());
        assertTrue(released.isReleased());
    }

    @Test
    public void it_sets_unparseable_dates_to_null() {
        PhpVersion garbageReleaseDate = PhpVersion.of(1, 2, "bla", "2024-01-01");
        PhpVersion garbageSupportedDate = PhpVersion.of(1, 2, "2024-01-01", "bla");
        PhpVersion garbageAllDates = PhpVersion.of(1, 2, "bla", "bla");

        assertNull(garbageReleaseDate.getReleasedAt());
        assertNull(garbageSupportedDate.getSupportedUntil());
        assertNull(garbageAllDates.getReleasedAt());
        assertNull(garbageAllDates.getSupportedUntil());
    }
}