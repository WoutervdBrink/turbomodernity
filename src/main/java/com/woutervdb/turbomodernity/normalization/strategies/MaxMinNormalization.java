package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

public class MaxMinNormalization<L extends Language<V>, V extends Version> extends BaseNormalizationStrategy<L, V> {
    private Double min = 0.0;
    private Double max = 1.0;

    @Override
    protected void beforeNormalize(ModernitySignature<L, V> signature) {
        min = signature.min();
        max = signature.max();
    }

    @Override
    protected Double normalize(Double value) {
        if (max.equals(min)) {
            // Interesting choice to be made here: it is not really defined what the value should be when the maximum is
            // equal to the minimum, i.e. all values are the same. For now, we have chosen to define this as 0.0.
            return 0.0;
        }
        return (value - min) / (max - min);
    }
}
