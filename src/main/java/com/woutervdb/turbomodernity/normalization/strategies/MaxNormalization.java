package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;

public class MaxNormalization extends BaseNormalizationStrategy {
    private Double max;

    @Override
    protected void beforeNormalize(ModernitySignature signature) {
        max = signature.max();
    }

    @Override
    protected Double normalize(Double value) {
        if (max == 0) {
            return 0.0;
        }
        return value / max;
    }
}
