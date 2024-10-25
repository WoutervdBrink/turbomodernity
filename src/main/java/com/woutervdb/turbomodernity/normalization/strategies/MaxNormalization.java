package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

public class MaxNormalization<L extends Language<V>, V extends Version> extends BaseNormalizationStrategy<L, V> {
    private Double max;

    @Override
    protected void beforeNormalize(ModernitySignature<L, V> signature) {
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
