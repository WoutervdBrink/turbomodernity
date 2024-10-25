package com.woutervdb.turbomodernity.signature;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.versioning.simple.SimpleVersion;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModernitySignatureTest extends BaseTest {
    private static final SimpleVersion V1 = new SimpleVersion(1);
    private static final SimpleVersion V2 = new SimpleVersion(2);
    private static final SimpleVersion V3 = new SimpleVersion(3);

    /**
     * A language with three versions: V1, V2, and V3.
     */
    private static final Language<SimpleVersion> firstLanguageWithThreeVersions = new Language<>() {
        {
            addVersion(V1);
            addVersion(V2);
            addVersion(V3);
        }
    };

    /**
     * Another language with three versions. Remark how these versions are equal to those of the first language.
     */
    private static final Language<SimpleVersion> secondLanguageWithThreeVersions = new Language<>() {
        {
            addVersion(V1);
            addVersion(V2);
            addVersion(V3);
        }
    };

    /**
     * A language with two versions: V1 and V2.
     */
    private static final Language<SimpleVersion> languageWithTwoVersions = new Language<>() {
        {
            addVersion(V1);
            addVersion(V2);
        }
    };

    @Test
    public void it_registers_all_versions() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> signature2 = new ModernitySignature<>(languageWithTwoVersions);
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> signature3 = new ModernitySignature<>(firstLanguageWithThreeVersions);

        assertEquals(2, signature2.size());
        assertEquals(List.of(V1, V2), signature2.versions());

        assertEquals(3, signature3.size());
        assertEquals(List.of(V1, V2, V3), signature3.versions());
    }

    @Test
    public void it_initializes_values_to_zero() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> signature = new ModernitySignature<>(firstLanguageWithThreeVersions);

        assertEquals(0.0, signature.getValue(V1));
        assertEquals(0.0, signature.getValue(V2));
        assertEquals(0.0, signature.getValue(V3));
    }

    @Test
    public void it_is_not_allowed_to_get_or_set_values_for_a_version_not_associated_with_the_language() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(languageWithTwoVersions);

        assertThrows(IllegalArgumentException.class, () -> sig.getValue(V3));
    }

    @Test
    public void it_sets_values() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        assertEquals(1.0, sig.getValue(V1));
        assertEquals(3.0, sig.getValue(V2));
        assertEquals(5.0, sig.getValue(V3));
    }

    @Test
    public void it_tests_equality_for_equal_signatures() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig1a = new ModernitySignature<>(firstLanguageWithThreeVersions);
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig1b = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig1a.setValue(V1, 1.0);
        sig1a.setValue(V2, 3.0);
        sig1a.setValue(V3, 5.0);

        sig1b.setValue(V1, 1.0);
        sig1b.setValue(V2, 3.0);
        sig1b.setValue(V3, 5.0);

        assertEquals(sig1a, sig1b);
        assertEquals(sig1b, sig1a);
    }

    @Test
    public void it_tests_equality_for_signatures_with_different_values_and_same_language() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig1 = new ModernitySignature<>(firstLanguageWithThreeVersions);
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig2 = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig1.setValue(V1, 1.0);
        sig1.setValue(V2, 3.0);
        sig1.setValue(V3, 5.0);

        sig2.setValue(V1, 7.0);
        sig2.setValue(V2, 3.0);
        sig2.setValue(V1, 5.0);

        assertNotEquals(sig1, sig2);
        assertNotEquals(sig2, sig1);
    }

    @Test
    public void it_tests_equality_for_signatures_with_different_languages_and_same_versions() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> firstLanguageSig = new ModernitySignature<>(firstLanguageWithThreeVersions);
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> secondLanguageSig = new ModernitySignature<>(secondLanguageWithThreeVersions);

        firstLanguageSig.setValue(V1, 1.0);
        firstLanguageSig.setValue(V2, 3.0);
        firstLanguageSig.setValue(V3, 5.0);

        secondLanguageSig.setValue(V1, 1.0);
        secondLanguageSig.setValue(V2, 3.0);
        secondLanguageSig.setValue(V3, 5.0);

        assertNotEquals(firstLanguageSig, secondLanguageSig);
        assertNotEquals(secondLanguageSig, firstLanguageSig);
    }

    @Test
    public void it_tests_equality_for_things_that_are_not_signatures() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);
        Object notASig = new Object();

        assertNotEquals(sig, notASig);
        assertNotEquals(notASig, sig);
    }

    @Test
    public void it_makes_copies_of_itself() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig1 = new ModernitySignature<>(firstLanguageWithThreeVersions);
        sig1.setValue(V1, 1.0);
        sig1.setValue(V2, 3.0);
        sig1.setValue(V3, 5.0);

        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig2 = sig1.copy();

        assertEquals(sig1, sig2);
    }

    @Test
    public void copies_are_not_affected_by_the_original_or_vice_versa() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig1 = new ModernitySignature<>(firstLanguageWithThreeVersions);
        sig1.setValue(V1, 1.0);
        sig1.setValue(V2, 3.0);
        sig1.setValue(V3, 5.0);

        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig2 = sig1.copy();

        sig1.setValue(V1, 2.0);
        sig2.setValue(V2, 4.0);

        assertEquals(2.0, sig1.getValue(V1));
        assertEquals(1.0, sig2.getValue(V1));

        assertEquals(3.0, sig1.getValue(V2));
        assertEquals(4.0, sig2.getValue(V2));
    }

    @Test
    public void it_generates_a_stream_of_values() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);
        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        Stream<Double> expected = Stream.of(1.0, 3.0, 5.0);
        Stream<Double> actual = sig.stream();

        Double[] expectedArray = expected.toArray(Double[]::new);
        Double[] actualArray = actual.toArray(Double[]::new);

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void it_determines_a_maximum_value() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        assertEquals(5.0, sig.max());
    }

    @Test
    public void it_determines_a_minimum_value() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        assertEquals(1.0, sig.min());
    }

    @Test
    public void it_determines_the_vector_length() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        Double expected = Math.sqrt(1.0 + 3.0 * 3.0 + 5.0 * 5.0);

        assertEquals(expected, sig.vectorLength());
    }

    @Test
    public void it_casts_to_string() {
        ModernitySignature<Language<SimpleVersion>, SimpleVersion> sig = new ModernitySignature<>(firstLanguageWithThreeVersions);

        sig.setValue(V1, 1.0);
        sig.setValue(V2, 3.0);
        sig.setValue(V3, 5.0);

        assertEquals("<1.0, 3.0, 5.0>", sig.toString());
    }
}