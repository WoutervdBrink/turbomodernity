package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.MockLanguage;
import com.woutervdb.turbomodernity.normalization.NormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.MockVersion;

import java.util.List;

class VectorNormalizationTest extends BaseNormalizationStrategyTest{

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
                        0.0 / Math.sqrt(30),
                        1.0 / Math.sqrt(30),
                        2.0 / Math.sqrt(30),
                        3.0 / Math.sqrt(30),
                        4.0 / Math.sqrt(30)
                ),
                makeSignature(
                        1.0 / Math.sqrt(55),
                        2.0 / Math.sqrt(55),
                        3.0 / Math.sqrt(55),
                        4.0 / Math.sqrt(55),
                        5.0 / Math.sqrt(55)
                ),
                makeSignature(0.0, 0.0, 0.0, 0.0, 0.0),
                makeSignature(
                        1.0 / Math.sqrt(5),
                        1.0 / Math.sqrt(5),
                        1.0 / Math.sqrt(5),
                        1.0 / Math.sqrt(5),
                        1.0 / Math.sqrt(5)
                ),
                makeSignature(
                        5.0 / Math.sqrt(125),
                        5.0 / Math.sqrt(125),
                        5.0 / Math.sqrt(125),
                        5.0 / Math.sqrt(125),
                        5.0 / Math.sqrt(125)
                )
        );
    }

    @Override
    protected NormalizationStrategy<MockLanguage, MockVersion> getNormalizationStrategy() {
        return new VectorNormalization<>();
    }
}