package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

public class ZScoreNormalization<L extends Language<V>, V extends Version> extends BaseNormalizationStrategy<L, V> {
    private Double mean = 1.0;
    private Double stdDev = 0.0;

    @Override
    protected void beforeNormalize(ModernitySignature<L, V> signature) {
        mean = signature.stream().reduce(0.0, Double::sum) / signature.size();
        double variance = signature.stream().map(x -> Math.pow(x - mean, 2)).reduce(0.0, Double::sum) / (signature.size());
        stdDev = Math.sqrt(variance);
    }

    @Override
    protected Double normalize(Double value) {
        if (stdDev.equals(0.0)) {
            // Edge case: all values equal, no std. deviation.
            return 0.0;
        }
        return (value - mean) / stdDev;
    }
}
