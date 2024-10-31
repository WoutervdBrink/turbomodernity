package com.woutervdb.turbomodernity;

import com.woutervdb.turbomodernity.grammars.semantic.SemanticVersionParserParser;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.atn.RuleStartState;
import org.antlr.v4.runtime.atn.Transition;

public class Main {
    public static void main(String[] args) {
        System.out.println("The main method does not do anything useful yet.");

        new Main().printGrammarInfo();
    }

    public void printGrammarInfo() {
        String[] ruleNames = SemanticVersionParserParser.ruleNames;
        ATN atn = SemanticVersionParserParser._ATN;

        for (int ruleIndex = 0; ruleIndex < ruleNames.length; ruleIndex++) {
            String ruleName = ruleNames[ruleIndex];
            RuleStartState ruleStart = atn.ruleToStartState[ruleIndex];

            System.out.println("Rule: " + ruleName);

            for (Transition transition : ruleStart.getTransitions()) {
                ATNState target = transition.target;
                System.out.println("  Alternative with target state: " + target);
            }
        }
    }
}