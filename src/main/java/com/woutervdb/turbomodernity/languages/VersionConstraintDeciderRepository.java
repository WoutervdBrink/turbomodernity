package com.woutervdb.turbomodernity.languages;

import org.antlr.v4.runtime.RuleContext;

import java.util.*;

public class VersionConstraintDeciderRepository {
    private final Map<Class<? extends RuleContext>, List<? extends VersionConstraintDecider<? extends RuleContext>>> map = new HashMap<>();

    public <C extends RuleContext> void addDecider(Class<C> contextClass, VersionConstraintDecider<C> decider) {
        @SuppressWarnings("unchecked")
        List<VersionConstraintDecider<C>> deciders = (List<VersionConstraintDecider<C>>)
                map.computeIfAbsent(contextClass, k -> new LinkedList<>());
        deciders.add(decider);
    }

    @SuppressWarnings("unchecked")
    public <C extends RuleContext> List<VersionConstraintDecider<C>> getDeciders(Class<C> contextClass) {
        return (List<VersionConstraintDecider<C>>) map.getOrDefault(contextClass, new ArrayList<>());
    }
}
