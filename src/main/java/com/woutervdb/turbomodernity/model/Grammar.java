package com.woutervdb.turbomodernity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of an ANTLR grammar.
 * <p>
 * To determine:
 *
 * <ul>
 *     <li>Should we resolve imports? If so, how?</li>
 *     <li>Are actions relevant for the framework?</li>
 *     <li>Are attributes relevant for the framework?</li>
 *     <li>Are tokens relevant for the framework?</li>
 * </ul>
 */
public class Grammar {
    private final GRAMMAR_TYPE type;

    private final String name;
    private final Map<String, String> options = new HashMap<>();
    private final List<Rule> rules = new ArrayList<>();

    public Grammar(GRAMMAR_TYPE type, String name) {
        this.type = type;
        this.name = name;
    }

    public Map<String, String> getOptions() {
        return new HashMap<>(options);
    }

    public int getSize() {
        return rules.size();
    }

    public Rule getRule(int index) {
        return rules.get(index);
    }

    public void setOption(String key, String value) {
        options.put(key, value);
    }

    public Rule addRule(String name) {
        Rule rule = new Rule(this.rules.size(), name);
        this.rules.add(rule);
        return rule;
    }

    public GRAMMAR_TYPE getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public enum GRAMMAR_TYPE {LEXER, PARSER}
}
