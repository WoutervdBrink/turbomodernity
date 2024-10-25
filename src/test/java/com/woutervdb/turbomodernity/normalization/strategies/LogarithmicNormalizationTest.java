package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.normalization.NormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;

import java.util.List;

class LogarithmicNormalizationTest extends BaseNormalizationStrategyTest {

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
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(
                        Math.log(1) / Math.log(120),
                        Math.log(2) / Math.log(120),
                        Math.log(3) / Math.log(120),
                        Math.log(4) / Math.log(120),
                        Math.log(5) / Math.log(120)
                ),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(
                        Math.log(5) / Math.log(3125),
                        Math.log(5) / Math.log(3125),
                        Math.log(5) / Math.log(3125),
                        Math.log(5) / Math.log(3125),
                        Math.log(5) / Math.log(3125)
                )
        );
    }

    @Override
    protected NormalizationStrategy<MockLanguage, MockVersion> getNormalizationStrategy() {
        return new LogarithmicNormalization<>();
    }
}