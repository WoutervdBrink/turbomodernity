package com.woutervdb.turbomodernity.normalization;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseNormalizationStrategyTest extends BaseTest {
    private ModernitySignature<MockLanguage, MockVersion> sig;

    @BeforeEach
    public void setUp() {
        sig = new ModernitySignature<>(MockLanguage.INSTANCE);

        sig.setValue(MockVersion.V1, 1.0);
        sig.setValue(MockVersion.V2, 2.0);
        sig.setValue(MockVersion.V3, 3.0);
        sig.setValue(MockVersion.V4, 4.0);
        sig.setValue(MockVersion.V5, 5.0);
    }

    @Test
    public void it_does_nothing_to_values_by_default() {
        NormalizationStrategy<MockLanguage, MockVersion> strategy = new BaseNormalizationStrategy<>() {
        };

        ModernitySignature<MockLanguage, MockVersion> result = strategy.normalize(sig);

        assertEquals(1, sig.getValue(MockVersion.V1));
        assertEquals(1, result.getValue(MockVersion.V1));

        assertEquals(2, sig.getValue(MockVersion.V2));
        assertEquals(2, result.getValue(MockVersion.V2));

        assertEquals(3, sig.getValue(MockVersion.V3));
        assertEquals(3, result.getValue(MockVersion.V3));

        assertEquals(4, sig.getValue(MockVersion.V4));
        assertEquals(4, result.getValue(MockVersion.V4));

        assertEquals(5, sig.getValue(MockVersion.V5));
        assertEquals(5, result.getValue(MockVersion.V5));
    }

    @Test
    public void it_calls_beforeNormalize_before_normalizing() {
        final boolean[] calledBeforeNormalize = {false};
        final boolean[] didCallNormalize = {false};

        NormalizationStrategy<MockLanguage, MockVersion> strategy = new BaseNormalizationStrategy<>() {
            @Override
            protected void beforeNormalize(ModernitySignature<MockLanguage, MockVersion> signature) {
                if (!didCallNormalize[0]) {
                    calledBeforeNormalize[0] = true;
                }
            }

            @Override
            protected Double normalize(Double value) {
                didCallNormalize[0] = true;
                return 0.0;
            }
        };

        strategy.normalize(sig);

        assertTrue(calledBeforeNormalize[0]);
        assertTrue(didCallNormalize[0]);
    }

    @Test
    public void it_calls_afterNormalize_after_normalizing() {
        final boolean[] calledAfterNormalize = {false};
        final boolean[] didCallNormalize = {false};

        NormalizationStrategy<MockLanguage, MockVersion> strategy = new BaseNormalizationStrategy<>() {
            @Override
            protected void afterNormalize(ModernitySignature<MockLanguage, MockVersion> signature) {
                if (didCallNormalize[0]) {
                    calledAfterNormalize[0] = true;
                }
            }

            @Override
            protected Double normalize(Double value) {
                didCallNormalize[0] = true;
                calledAfterNormalize[0] = false;
                return 0.0;
            }
        };

        strategy.normalize(sig);

        assertTrue(calledAfterNormalize[0]);
        assertTrue(didCallNormalize[0]);
    }

    @Test
    public void it_does_not_alter_the_original_signature() {
        NormalizationStrategy<MockLanguage, MockVersion> strategy = new BaseNormalizationStrategy<>() {
            @Override
            protected Double normalize(Double value) {
                return 0.0;
            }
        };

        strategy.normalize(sig);

        assertNotEquals(0, sig.getValue(MockVersion.V1));
        assertNotEquals(0, sig.getValue(MockVersion.V2));
        assertNotEquals(0, sig.getValue(MockVersion.V3));
        assertNotEquals(0, sig.getValue(MockVersion.V4));
        assertNotEquals(0, sig.getValue(MockVersion.V5));
    }

    @Test
    public void it_allows_changing_the_normalization_method() {
        NormalizationStrategy<MockLanguage, MockVersion> strategy = new BaseNormalizationStrategy<>() {
            @Override
            protected Double normalize(Double value) {
                return value * 2;
            }
        };

        ModernitySignature<MockLanguage, MockVersion> result = strategy.normalize(sig);

        assertEquals(2.0, result.getValue(MockVersion.V1));
        assertEquals(4.0, result.getValue(MockVersion.V2));
        assertEquals(6.0, result.getValue(MockVersion.V3));
        assertEquals(8.0, result.getValue(MockVersion.V4));
        assertEquals(10.0, result.getValue(MockVersion.V5));
    }
}