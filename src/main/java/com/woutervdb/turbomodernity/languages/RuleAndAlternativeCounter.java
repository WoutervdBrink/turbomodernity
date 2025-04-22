package com.woutervdb.turbomodernity.languages;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Parse tree listener which tracks how often certain rules and rule alternatives are chosen in the parse tree.
 *
 * <p>This counter only works if the grammar is generated with the {@code contextSuperClass} option set to
 * {@code org.antlr.v4.runtime.RuleContextWithAltNum} in the grammar file. Otherwise, ANTLR does not keep track of
 * alternative numbers and this counter cannot query them.</p>
 */
public class RuleAndAlternativeCounter implements ParseTreeListener {
    private final Map<RuleAndAlternativePair, Integer> counter = new HashMap<>();

    /**
     * Construct a new parse tree listener.
     */
    public RuleAndAlternativeCounter() {
        this.reset();
    }

    /**
     * Reset the counters.
     *
     * <p>This method is automatically called upon constructing the listener, and should only be called when re-using
     * existing listeners for different parse trees.</p>
     */
    public void reset() {
        this.counter.clear();
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        int ruleIndex = ctx.getRuleIndex();
        int alternativeNumber = ctx.getAltNumber();

        RuleAndAlternativePair pair = new RuleAndAlternativePair(ruleIndex, alternativeNumber);

        counter.put(pair, counter.getOrDefault(pair, 0) + 1);
    }

    private Integer sum() {
        return counter.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Get the ratio of the specified rule index.
     *
     * <p>The ratio is defined as the amount of times this rule was used, divided by the amount of times any rule was
     * used.</p>
     *
     * @param ruleIndex The index of the rule to query.
     * @return The ratio of the specified rule index.
     */
    public @NotNull Double getRatio(int ruleIndex) {
        return (double) getCount(ruleIndex) / (double) sum();
    }

    /**
     * Get the ratio of the specified pair of rule and alternative indices.
     *
     * <p>The ratio is defined as the amount of times this alternative was used, divided by the amount of times any
     * alternative of the specified rule was used.</p>
     *
     * @param ruleIndex        The index of the rule to query.
     * @param alternativeIndex The index of the alternative to query.
     * @return The ratio of the specified pair of rule and alternative indices.
     */
    public @NotNull Double getRatio(int ruleIndex, int alternativeIndex) {
        return (double) getCount(ruleIndex, alternativeIndex) / (double) getCount(ruleIndex);
    }

    /**
     * Get the count for a specified rule index.
     *
     * @param ruleIndex The index of the rule to query.
     * @return The amount of times this rule was used, or 0 if it was never used.
     */
    public @NotNull Integer getCount(int ruleIndex) {
        return counter.keySet().stream().filter((pair -> pair.ruleIndex == ruleIndex)).map(counter::get).reduce(0, Integer::sum);
    }

    /**
     * Get the count for a specified pair of rule and alternative indices.
     *
     * @param ruleIndex        The index of the rule to query.
     * @param alternativeIndex The index of the alternative to query.
     * @return The amount of times this combination of rule and alternative was used, or 0 if this combination was never
     * used.
     */
    public @NotNull Integer getCount(int ruleIndex, int alternativeIndex) {
        return counter.getOrDefault(new RuleAndAlternativePair(ruleIndex, alternativeIndex), 0);
    }

    private record RuleAndAlternativePair(int ruleIndex, int alternativeIndex) {
    }
}
