package com.woutervdb.turbomodernity.normalization;

import com.woutervdb.turbomodernity.signature.ModernitySignature;

/**
 * This interface defines a strategy for normalizing a {@link ModernitySignature}.
 */
public interface NormalizationStrategy {
    /**
     * Normalize the signature. Implementations of this method are expected to create a copy of the signature, then
     * modify that copy. The behavior for modifying the given signature is undefined.
     *
     * @param signature The signature.
     * @return The normalized signature.
     */
    ModernitySignature normalize(final ModernitySignature signature);
}
