package com.woutervdb.turbomodernity.versioning.ranges;

import org.antlr.v4.runtime.RuleContext;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A collection of {@link VersionRangeDecider} instances.
 */
public class VersionRangeDeciderCollection {
    private final Map<Class<? extends RuleContext>, List<? extends VersionRangeDecider<? extends RuleContext>>> map = new HashMap<>();

    /**
     * Decide the range of versions which would allow this rule context, using the {@link VersionRangeDecider} instances
     * in this collection.
     *
     * @param ctx The rule context to analyze. Should not be {@code null}
     * @return The range of versions which would allow this rule context. Will never be {@code null}.
     * @param <C> The type of rule context analyzed.
     */
    public <C extends RuleContext> @NotNull VersionRange decide(@NotNull C ctx) {
        @SuppressWarnings("unchecked")
        List<VersionRangeDecider<C>> list = (List<VersionRangeDecider<C>>) map.getOrDefault(ctx.getClass(), new ArrayList<>());
        VersionRange range = VersionRange.empty();

        for (VersionRangeDecider<C> decider : list) {
            if (decider != null) {
                VersionRange newRange = decider.decide(ctx);

                if (newRange != null) {
                    range = range.merge(newRange);
                }
            }
        }

        return range;
    }

    /**
     * Add a {@link VersionRangeDecider} instance to this collection.
     *
     * @param contextClass The class of rule context that is analyzed by this decider.
     * @param decider The decider to add.
     * @param <C> The type of rule context that is analyzed by this decider.
     */
    public <C extends RuleContext> void addDecider(Class<C> contextClass, @NotNull VersionRangeDecider<C> decider) {
        @SuppressWarnings("unchecked")
        List<VersionRangeDecider<C>> deciders = (List<VersionRangeDecider<C>>)
                map.computeIfAbsent(contextClass, k -> new LinkedList<>());
        deciders.add(decider);
    }

}