package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.BaseTest;
import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.normalization.NormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseNormalizationStrategyTest extends BaseTest {
    protected abstract List<ModernitySignature<MockLanguage, MockVersion>> getBeforeSignatures();

    protected abstract List<ModernitySignature<MockLanguage, MockVersion>> getAfterSignatures();

    protected abstract NormalizationStrategy<MockLanguage, MockVersion> getNormalizationStrategy();

    protected ModernitySignature<MockLanguage, MockVersion> makeSignature(Double v1, Double v2, Double v3, Double v4, Double v5) {
        ModernitySignature<MockLanguage, MockVersion> sig = new ModernitySignature<>(MockLanguage.INSTANCE);

        sig.setValue(MockVersion.V1, v1);
        sig.setValue(MockVersion.V2, v2);
        sig.setValue(MockVersion.V3, v3);
        sig.setValue(MockVersion.V4, v4);
        sig.setValue(MockVersion.V5, v5);

        return sig;
    }


    @ParameterizedTest(name = "[{index}] {0} normalizes to {1}")
    @MethodSource("testCases")
    public void it_normalizes(ModernitySignature<MockLanguage, MockVersion> before, ModernitySignature<MockLanguage, MockVersion> after) {
        ModernitySignature<MockLanguage, MockVersion> beforeCopy = before.copy();

        ModernitySignature<MockLanguage, MockVersion> normalized = getNormalizationStrategy().normalize(before);

        assertEquals(after, normalized, "Error in test: after modernity signature should be normalized!");
        assertEquals(beforeCopy, before, "Error in test: before modernity signature should not be altered!");
    }

    public Iterator<Arguments> testCases() {
        List<ModernitySignature<MockLanguage, MockVersion>> beforeSignatures = getBeforeSignatures();
        List<ModernitySignature<MockLanguage, MockVersion>> afterSignatures = getAfterSignatures();

        assertEquals(beforeSignatures.size(), afterSignatures.size(), "Error in test: amount of before and after signatures should be equal!");

        Iterator<ModernitySignature<MockLanguage, MockVersion>> beforeIterator = beforeSignatures.iterator();
        Iterator<ModernitySignature<MockLanguage, MockVersion>> afterIterator = afterSignatures.iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return beforeIterator.hasNext() && afterIterator.hasNext();
            }

            @Override
            public Arguments next() {
                return Arguments.of(beforeIterator.next(), afterIterator.next());
            }
        };
    }
}
