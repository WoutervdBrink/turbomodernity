package com.woutervdb.turbomodernity.signature;

import com.woutervdb.turbomodernity.languages.Language;
import com.woutervdb.turbomodernity.versioning.Version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModernitySignature<L extends Language<V>, V extends Version> {
    private final Map<V, Double> values;
    private final L language;

    public ModernitySignature(L language) {
        this.language = language;

        values = new HashMap<>();

        language.versions().forEach((v) -> values.put(v, 0.0));
    }

    public List<V> versions() {
        return values.keySet().stream().sorted().collect(Collectors.toList());
    }

    public int size() {
        return values.size();
    }

    public Double getValue(V version) {
        Double d = values.get(version);

        if (d == null) {
            throw new IllegalArgumentException("Version " + version.toString() + " is not part of language " + language.toString());
        }

        return d;
    }

    public void setValue(V version, Double value) {
        values.put(version, value);
    }

    public ModernitySignature<L, V> copy() {
        ModernitySignature<L, V> clone = new ModernitySignature<>(language);
        clone.values.putAll(values);
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ModernitySignature<?, ?> other)) {
            return false;
        }

        return language.equals(other.language) && values.equals(other.values);
    }

    @Override
    public String toString() {
        return "<" + versions().parallelStream().map(v -> this.getValue(v).toString()).collect(Collectors.joining(", ")) + ">";
    }

    public Stream<Double> stream() {
        return this.language.versions().stream().map(values::get);
    }

    public Double max() {
        return stream().max(Double::compareTo).orElse(0.0);
    }

    public Double min() {
        return stream().min(Double::compareTo).orElse(0.0);
    }

    public Double vectorLength() {
        return Math.sqrt(stream().mapToDouble(v -> v * v).sum());
    }
}