package com.woutervdb.turbomodernity.languages.php;

import com.woutervdb.turbomodernity.languages.Language;

public class Php extends Language<PhpVersion> {
    public static final PhpVersion PHP_4_0 = PhpVersion.of(4, 0, "2000-05-22", "2000-10-10");
    public static final PhpVersion PHP_4_1 = PhpVersion.of(4, 1, "2001-12-10", "2002-03-12");
    public static final PhpVersion PHP_4_2 = PhpVersion.of(4, 2, "2002-04-22", "2002-09-06");
    public static final PhpVersion PHP_4_3 = PhpVersion.of(4, 3, "2002-12-27", "2005-03-31");
    public static final PhpVersion PHP_4_4 = PhpVersion.of(4, 4, "2005-07-11", "2008-08-07");
    public static final PhpVersion PHP_5_0 = PhpVersion.of(5, 0, "2004-07-13", "2005-09-05");
    public static final PhpVersion PHP_5_1 = PhpVersion.of(5, 1, "2005-11-24", "2006-08-24");
    public static final PhpVersion PHP_5_2 = PhpVersion.of(5, 2, "2006-11-02", "2011-01-06");
    public static final PhpVersion PHP_5_3 = PhpVersion.of(5, 3, "2009-06-30", "2014-08-14");
    public static final PhpVersion PHP_5_4 = PhpVersion.of(5, 4, "2012-03-01", "2015-09-03");
    public static final PhpVersion PHP_5_5 = PhpVersion.of(5, 5, "2013-06-20", "2016-07-10");
    public static final PhpVersion PHP_5_6 = PhpVersion.of(5, 6, "2013-06-20", "2018-12-31");
    public static final PhpVersion PHP_7_0 = PhpVersion.of(7, 0, "2015-12-03", "2019-01-10");
    public static final PhpVersion PHP_7_1 = PhpVersion.of(7, 1, "2016-12-01", "2019-12-01");
    public static final PhpVersion PHP_7_2 = PhpVersion.of(7, 2, "2017-11-30", "2020-11-30");
    public static final PhpVersion PHP_7_3 = PhpVersion.of(7, 3, "2018-12-06", "2021-12-06");
    public static final PhpVersion PHP_7_4 = PhpVersion.of(7, 4, "2019-11-28", "2022-11-28");
    public static final PhpVersion PHP_8_0 = PhpVersion.of(8, 0, "2020-11-26", "2023-11-26");
    public static final PhpVersion PHP_8_1 = PhpVersion.of(8, 1, "2021-11-25", "2025-12-31");
    public static final PhpVersion PHP_8_2 = PhpVersion.of(8, 2, "2022-12-08", "2026-12-31");
    public static final PhpVersion PHP_8_3 = PhpVersion.of(8, 3, "2023-11-23", "2027-12-31");
    public static final PhpVersion PHP_8_4 = PhpVersion.of(8, 4, "2024-11-21", "2028-12-31");

    public static final Php INSTANCE = new Php();

    private Php() {
        super();

        addVersion(PHP_4_0);
        addVersion(PHP_4_1);
        addVersion(PHP_4_2);
        addVersion(PHP_4_3);
        addVersion(PHP_4_4);
        addVersion(PHP_5_0);
        addVersion(PHP_5_1);
        addVersion(PHP_5_2);
        addVersion(PHP_5_3);
        addVersion(PHP_5_4);
        addVersion(PHP_5_5);
        addVersion(PHP_5_6);
        addVersion(PHP_7_0);
        addVersion(PHP_7_1);
        addVersion(PHP_7_2);
        addVersion(PHP_7_3);
        addVersion(PHP_7_4);
        addVersion(PHP_8_0);
        addVersion(PHP_8_1);
        addVersion(PHP_8_2);
        addVersion(PHP_8_3);
        addVersion(PHP_8_4);
    }
}
