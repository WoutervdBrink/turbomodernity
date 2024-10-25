package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.normalization.NormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;

import java.util.List;

class ZScoreNormalizationTest extends BaseNormalizationStrategyTest {

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
                makeSignature(
                        (0.0 - 2) / Math.sqrt(2),
                        (1.0 - 2) / Math.sqrt(2),
                        (2.0 - 2) / Math.sqrt(2),
                        (3.0 - 2) / Math.sqrt(2),
                        (4.0 - 2) / Math.sqrt(2)
                ),
                makeSignature(
                        (1.0 - 3) / Math.sqrt(2),
                        (2.0 - 3) / Math.sqrt(2),
                        (3.0 - 3) / Math.sqrt(2),
                        (4.0 - 3) / Math.sqrt(2),
                        (5.0 - 3) / Math.sqrt(2)
                ),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0)
        );
    }

    @Override
    protected NormalizationStrategy<MockLanguage, MockVersion> getNormalizationStrategy() {
        return new ZScoreNormalization<>();
    }
}