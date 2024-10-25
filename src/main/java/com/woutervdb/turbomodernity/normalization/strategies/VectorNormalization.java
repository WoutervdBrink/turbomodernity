package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

public class VectorNormalization<L extends Language<V>, V extends Version> extends BaseNormalizationStrategy<L, V> {
    private Double vectorLength = 1.0;

    @Override
    protected void beforeNormalize(ModernitySignature<L, V> signature) {
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
