package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;

public class VectorNormalization extends BaseNormalizationStrategy {
    private Double vectorLength = 1.0;

    @Override
    protected void beforeNormalize(ModernitySignature signature) {
        vectorLength = signature.vectorLength();
    }

    @Override
    protected Double normalize(Double value) {
        if (vectorLength.equals(0.0)) {
            return 0.0;
        }

        return value / vectorLength;
    }
}
