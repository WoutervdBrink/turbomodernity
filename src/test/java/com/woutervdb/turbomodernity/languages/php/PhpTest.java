package com.woutervdb.turbomodernity.languages.php;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhpTest {
    @Test
    public void it_has_all_the_versions() {
        assertEquals(
                List.of(
                        Php.PHP_4_0,
                        Php.PHP_4_1,
                        Php.PHP_4_2,
                        Php.PHP_4_3,
                        Php.PHP_4_4,
                        Php.PHP_5_0,
                        Php.PHP_5_1,
                        Php.PHP_5_2,
                        Php.PHP_5_3,
                        Php.PHP_5_4,
                        Php.PHP_5_5,
                        Php.PHP_5_6,
                        Php.PHP_7_0,
                        Php.PHP_7_1,
                        Php.PHP_7_2,
                        Php.PHP_7_3,
                        Php.PHP_7_4,
                        Php.PHP_8_0,
                        Php.PHP_8_1,
                        Php.PHP_8_2,
                        Php.PHP_8_3,
                        Php.PHP_8_4
                ),
                Php.INSTANCE.versions()
        );
    }
}