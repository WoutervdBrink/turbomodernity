package com.woutervdb.turbomodernity.normalization;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

/**
 * This interface defines a strategy for normalizing a {@link ModernitySignature}.
 *
 * @param <L> The language to which this strategy can be applied.
 * @param <V> The versioning schema for which this strategy can be applied.
 */
public interface NormalizationStrategy<L extends Language<V>, V extends Version> {
    /**
     * Normalize the signature. Implementations of this method are expected to create a copy of the signature, then
     * modify that copy. The behavior for modifying the given signature is undefined.
     *
     * @param signature The signature.
     * @return The normalized signature.
     */
    ModernitySignature<L, V> normalize(final ModernitySignature<L, V> signature);
}
