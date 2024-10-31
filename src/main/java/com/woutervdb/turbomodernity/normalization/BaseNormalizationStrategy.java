package com.woutervdb.turbomodernity.normalization;

import com.woutervdb.turbomodernity.signature.ModernitySignature;
import com.woutervdb.turbomodernity.versioning.Version;

/**
 * This class provides a default implementation of {@link NormalizationStrategy}.
 *
 * <p>Its default behavior is as follows:</p>
 *
 * <ol>
 *     <li>Create a copy of the signature.</li>
 *     <li>Call {@link #beforeNormalize(ModernitySignature)} on the <b>copy</b> of the signature.</li>
 *     <li>Go through the language versions (oldest first) and call {@link #normalize(Double)}
 *     to determine the new value in the signature, given the old value.</li>
 *     <li>Call {@link #afterNormalize(ModernitySignature)} on the <b>processed</b> signature.</li>
 *     <li>Return the processed signature.</li>
 * </ol>
 *
 * <p>There are several ways to influence the behavior of this strategy. They are listed below.</p>
 *
 * <ul>
 *     <li>Override the {@link #normalize(Double)} method to determine how values should be normalized.</li>
 *     <li>Override the {@link #beforeNormalize(ModernitySignature)} method to do something with the <b>copy</b> of the
 *     signature <b>before</b> processing.</li>
 *     <li>Override the {@link #afterNormalize(ModernitySignature)} method to do something with the <b>processed</b>
 *     signature <b>after</b> processing.</li>
 * </ul>
 *
 * <p>Overriding the {@link #normalize(ModernitySignature)} method is not allowed. If you wish to override this method,
 * it is better to write your own implementation of {@link ModernitySignature}.</p>
 */
public abstract class BaseNormalizationStrategy implements NormalizationStrategy {
    public final ModernitySignature normalize(final ModernitySignature signature) {
        ModernitySignature result = signature.copy();

        beforeNormalize(result);

        for (Version version : signature.versions()) {
            result.setValue(version, normalize(signature.getValue(version)));
        }

        afterNormalize(result);

        return result;
    }

    /**
     * Do something with the signature <b>before</b> normalization.
     *
     * <p>The default implementation is to do nothing.</p>
     *
     * @param signature The <b>copy</b> of the signature that is to be normalized.
     */
    protected void beforeNormalize(ModernitySignature signature) {
        // Do nothing.
    }

    /**
     * Do something with the signature <b>after</b> normalization.
     *
     * <p>The default implementation is to do nothing.</p>
     *
     * @param signature The <b>processed</b> normalized signature.
     */
    protected void afterNormalize(ModernitySignature signature) {
        // Do nothing.
    }

    /**
     * Determine the normalized value.
     *
     * @param value The old value.
     * @return The new value.
     */
    protected Double normalize(Double value) {
        return value;
    }
}
