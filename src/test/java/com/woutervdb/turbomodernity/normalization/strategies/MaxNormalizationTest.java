package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.normalization.NormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;

import java.util.List;

class MaxNormalizationTest extends BaseNormalizationStrategyTest {

    @Override
    protected List<ModernitySignature<MockLanguage, MockVersion>> getBeforeSignatures() {
        return List.of(
                makeSignature(0.0, 1.0, 2.0, 3.0, 4.0),
                makeSignature(1.0, 2.0, 3.0, 4.0, 5.0),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(1.0, 1.0, 1.0, 1.0, 1.0),
                makeSignature(5.0, 5.0, 5.0, 5.0, 5.0)
        );
    }

    @Override
    protected List<ModernitySignature<MockLanguage, MockVersion>> getAfterSignatures() {
        return List.of(
                makeSignature(0.0, 0.25, 0.50, 0.75, 1.0),
                makeSignature(0.20, 0.40, 0.60, 0.80, 1.0),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(1.0, 1.0, 1.0, 1.0, 1.0),
                makeSignature(1.0, 1.0, 1.0, 1.0, 1.0)
        );
    }

    @Override
    protected NormalizationStrategy<MockLanguage, MockVersion> getNormalizationStrategy() {
        return new MaxNormalization<>();
    }
}