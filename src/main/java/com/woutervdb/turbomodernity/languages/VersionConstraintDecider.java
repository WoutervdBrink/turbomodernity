package com.woutervdb.turbomodernity.languages;

import org.antlr.v4.runtime.RuleContext;

public interface VersionConstraintDecider<C extends RuleContext> {
    VersionConstraint decide(C ctx);
}

