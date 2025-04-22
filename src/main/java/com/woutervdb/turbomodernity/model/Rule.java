package com.woutervdb.turbomodernity.model;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private final List<Alternative> alternatives = new ArrayList<>();

    private final int index;
    private final String name;

    public Rule(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public List<Alternative> getAlternatives() {
        return new ArrayList<>(alternatives);
    }

    public void addAlternative(String identifier, String alternative) {
        this.alternatives.add(new Alternative(identifier, alternative));
    }

    public void addAlternative(String alternative) {
        this.addAlternative(null, alternative);
    }
}
