package com.woutervdb.turbomodernity.normalization.strategies;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.normalization.BaseNormalizationStrategy;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

public class LogarithmicNormalization<L extends Language<V>, V extends Version> extends BaseNormalizationStrategy<L, V> {
    private Double logOfProducts = 1.0;

    @Override
    protected void beforeNormalize(ModernitySignature<L, V> signature) {
        logOfProducts = Math.log(signature.stream().reduce(1.0, (a, b) -> a * b));
    }

    @Override
    protected Double normalize(Double value) {
        Double log = Math.log(value);

        double result = log / logOfProducts;

        if (Double.isNaN(result)) {
            return 0.0;
        }

        // Quirk: sometimes the calculations result in -0.0. Adding 0.0 prevents this.
        return result + 0.0;
    }
}
