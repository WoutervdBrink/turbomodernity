package com.woutervdb.turbomodernity.versioning.ranges;

import org.antlr.v4.runtime.RuleContext;
import org.jetbrains.annotations.NotNull;

/**
 * A decider which, given a certain rule context of a parse tree, decides which version(s) of the language would allow
 * this rule context.
 *
 * @param <C> The type of rule context analyzed by this decider.
 */
@FunctionalInterface
public interface VersionRangeDecider<C extends RuleContext> {
    /**
     * Decide the range of versions which would allow this rule context.
     *
     * <p>Implementations of this method should inspect the given rule context and determine the language version(s) in
     * which the given context is allowed, based on e.g. the absence/presence of certain tokens or the contents of
     * tokens. The {@link VersionRange} class offers various static helper methods to classify the version range.</p>
     *
     * <p>If the context does not allow deciding the version range for whatever reason, this method should return
     * <code>null</code>.</p>
     *
     * @param ctx The analyzed parse tree context.
     * @return A range of versions or <code>null</code> if the version range could not be decided.
     * @see VersionRange for more information on classifying version ranges.
     */
    VersionRange decide(@NotNull C ctx);
}
